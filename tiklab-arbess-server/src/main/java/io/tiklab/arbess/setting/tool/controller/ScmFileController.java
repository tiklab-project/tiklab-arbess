package io.tiklab.arbess.setting.tool.controller;

import io.tiklab.arbess.setting.tool.service.ScmService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.Result;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线环境配置控制器
 */
@RestController
@RequestMapping("/scm/file")
public class ScmFileController {

    @Autowired
    ScmService scmService;

    @Value("${DATA_HOME}")
    private String DATA_HOME;

    private final Logger logger = LoggerFactory.getLogger(ScmFileController.class);

    @RequestMapping(path="/upload",method = RequestMethod.POST)
    public Result<String> fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile){
        if(uploadFile == null){
            throw new SystemException("uploadFile must not be null.");
        }

        String fileName = uploadFile.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)){
            throw new ApplicationException("fileName must not be empty.");
        }

        boolean contains = fileName.contains(".");
        if (contains){
            if (!fileName.endsWith(".tar.gz") && !fileName.endsWith(".zip")) {
                throw new ApplicationException("fileName must not end with .tar.gz. or .zip");
            }
        }

        File destDir = new File(DATA_HOME + PipelineFinal.ARBESS_SCM);

        String filePath = destDir.getAbsolutePath() + "/" + fileName;


        PipelineFileUtil.createDirectory(destDir.getAbsolutePath());

        // 确保目录存在
        File file = new File(filePath);

        // 写入文件
        writeToFile(uploadFile,file);

        try {
            if (!contains){
                return Result.ok(k8s(file,destDir));
            }
            if (fileName.endsWith(".tar.gz") ) {
                return Result.ok(tarGz(file,destDir));
            }
            if (fileName.endsWith(".zip")) {
                return Result.ok(zip(file,destDir));
            }

        } catch (Exception e) {
            throw new ApplicationException("解压失败:" + e.getMessage());
        }
        throw new ApplicationException("该类型文件无法解析！");
    }

    private void writeToFile(MultipartFile uploadFile, File file){
        try (InputStream stream = uploadFile.getInputStream()) {
            Files.copy(stream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e){
            throw new SystemException("文件写入失败："+ e.getMessage());
        }
    }

    // 添加权限
    private void addChmod(File destDir,String s){

        String absolutePath = destDir.getAbsolutePath();
        String order;
        if (StringUtils.isEmpty(s)){
            order =" chmod -R 755 "+ absolutePath;
        }else {
            order =" chmod -R 755 "+ absolutePath + "/" + s;
        }
        try {
            logger.info("scm exec order : {}",order);
            PipelineUtil.process(absolutePath, order);
        }catch (Exception e){
            logger.error("添加权限失败：{}",e.getMessage());
        }

    }

    public String tarGz(File file,File destDir ){

        String s;
        try {
            s = extractTarGz(file, destDir);
        } catch (IOException e) {
            throw new SystemException("文件解压失败："+e.getMessage());
        }

        // 删除压缩包
        file.delete();

        addChmod(destDir,s);
        return destDir.getAbsolutePath() + "/" + s +"/bin";
    }

    public String zip(File file,File destDir ){
        String s;
        try {
            s = extractZip(file, destDir);
        } catch (IOException e) {
            throw new SystemException("文件解压失败："+e.getMessage());
        }

        // 删除压缩包
        file.delete();

        addChmod(destDir,s);
        return destDir.getAbsolutePath() + "/" + s +"/bin";
    }

    public String k8s(File file,File destDir ){
        addChmod(destDir,null);
        return destDir.getAbsolutePath();
    }

    /**
     * 解压 tar.gz 文件到目标目录，并返回解压出的根目录名称
     * @param tarGzFile 输入的 tar.gz 文件
     * @param destDir 目标解压目录
     * @return 解压出的根目录名称（如 package-1.0.0)
     * @throws IOException IO 异常
     */
    private String extractTarGz(File tarGzFile, File destDir) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        String rootDirName = null;

        try (FileInputStream fis = new FileInputStream(tarGzFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             GzipCompressorInputStream gis = new GzipCompressorInputStream(bis);
             TarArchiveInputStream tis = new TarArchiveInputStream(gis)) {

            TarArchiveEntry entry;
            while ((entry = tis.getNextTarEntry()) != null) {
                String entryName = entry.getName();

                // 获取 tar 包内的根目录（第一次出现的前缀路径）
                if (rootDirName == null && entryName.contains("/")) {
                    rootDirName = entryName.substring(0, entryName.indexOf("/"));
                }

                File outputFile = new File(destDir, entryName);
                if (entry.isDirectory()) {
                    outputFile.mkdirs();
                } else {
                    outputFile.getParentFile().mkdirs();
                    try (OutputStream out = Files.newOutputStream(outputFile.toPath())) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = tis.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                    }
                }
            }
        }

        return rootDirName; // 可能为 null，如果 tar 包是平铺的
    }

    /**
     * 解压 zip 文件到目标目录，并返回解压出的根目录名称
     * @param zipFile 输入的 zip 文件
     * @param destDir 目标解压目录
     * @return 解压出的根目录名称（如 package-1.0.0)
     * @throws IOException IO 异常
     */
    private String extractZip(File zipFile, File destDir) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        String rootDirName = null;

        try (FileInputStream fis = new FileInputStream(zipFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();

                // 获取 zip 包内的根目录（第一次出现的前缀路径）
                if (rootDirName == null && entryName.contains("/")) {
                    rootDirName = entryName.substring(0, entryName.indexOf("/"));
                }

                File outputFile = new File(destDir, entryName);
                if (entry.isDirectory()) {
                    outputFile.mkdirs();
                } else {
                    outputFile.getParentFile().mkdirs();
                    try (OutputStream out = Files.newOutputStream(outputFile.toPath())) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = zis.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                    }
                }
            }
        }

        return rootDirName; // 可能为 null，如果 zip 包是平铺的
    }

}














