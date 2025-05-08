package io.tiklab.arbess.setting.controller;

import io.tiklab.arbess.setting.model.Scm;
import io.tiklab.arbess.setting.model.ScmQuery;
import io.tiklab.arbess.setting.service.ScmService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.Result;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import io.tiklab.core.page.Pagination;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

    private Logger logger = LoggerFactory.getLogger(ScmFileController.class);

    @RequestMapping(path="/upload",method = RequestMethod.POST)
    public Result<String> fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile){
        if(uploadFile == null){
            throw new SystemException("uploadFile must not be null.");
        }

        try {
            String fileName = uploadFile.getOriginalFilename();
            if (StringUtils.isEmpty(fileName)){
                throw new ApplicationException("fileName must not be empty.");
            }

            if (!fileName.endsWith(".tar.gz")) {
                throw new ApplicationException("fileName must not end with .tar.gz.");
            }

            String filePath = DATA_HOME + PipelineFinal.ARBESS_SCM + "/" + fileName;

            // 确保目录存在
            File destFile = new File(filePath);
            File parentDir = destFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            // 保存文件
            try (InputStream stream = uploadFile.getInputStream()) {
                Files.copy(stream, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            File destDir = new File(DATA_HOME + PipelineFinal.ARBESS_SCM);

            try {
                // 解压文件
                String s = extractTarGz(destFile, destDir);

                String path = destDir.getAbsolutePath() + "/" + s+"/bin";
                // 删除压缩包
                destFile.delete();

                String order =" chmod -R 755 "+ destDir.getAbsolutePath() + "/" + s;
                try {
                    logger.info("scm exec order : {}",order);
                    PipelineUtil.process(DATA_HOME, order);
                }catch (Exception e){
                    logger.error("添加权限失败：{}",e.getMessage());
                }

                // String tarGzBaseName = getTarGzBaseName(destDir.getAbsolutePath()+"/"+s);
                return Result.ok(path);
            } catch (Exception e) {
                throw new ApplicationException("解压失败:" + e.getMessage());
            }
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 解压 tar.gz 文件到目标目录，并返回解压出的根目录名称
     * @param tarGzFile 输入的 tar.gz 文件
     * @param destDir 目标解压目录
     * @return 解压出的根目录名称（如 package-1.0.0），若是平铺结构则返回 null
     * @throws IOException IO 异常
     */
    public static String extractTarGz(File tarGzFile, File destDir) throws IOException {
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

}














