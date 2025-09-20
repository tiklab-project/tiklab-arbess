package io.tiklab.arbess.setting.tool.service;

import io.tiklab.arbess.setting.tool.model.ScmRemoteFile;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ScmFileServiceImpl implements ScmFileService {

    @Value("${DATA_HOME}")
    String DATA_HOME;

    @Value("${arbess.download.url:https://install.tiklab.net}")
    String remoteDownloadUrl;

    private final Logger logger = LoggerFactory.getLogger(ScmFileServiceImpl.class);

    private Map<String, ScmRemoteFile> fileMap = new HashMap<>();

    @Override
    public String fileFileBinPath(String filePath){

        File file = new File(filePath);
        File destFile = file.getParentFile();
        String fileName = file.getName();

        boolean contains = fileName.contains(".");
        String binPath = null;
        try {
            if (!contains){
                binPath = k8s(file, destFile);
            }
            if (fileName.endsWith(".tar.gz") || fileName.endsWith(".tgz") || fileName.endsWith(".gz") ) {
                binPath = tarGz(file,destFile);
            }

            if (fileName.endsWith(".xz") ) {
                binPath = tarXz(file,destFile);
            }
            if (fileName.endsWith(".zip") || fileName.endsWith(".7z")) {
                binPath = zip(file,destFile);
            }
            if (StringUtils.isEmpty(binPath)){
                throw new ApplicationException("该类型文件无法解析！");
            }
            if (binPath.endsWith("/bin/bin")){
                binPath = binPath.replace("/bin/bin", "/bin");
            }
            if (binPath.contains("kubectl")){
                return new File(binPath).getParent();
            }
            return binPath;
        } catch (Exception e) {
            throw new ApplicationException("解压失败:" + e.getMessage());
        }
    }

    @Override
    public String downloadFile(ScmRemoteFile remoteFile)  {
        String id = remoteFile.getId();
        fileMap.remove(id);

        File destDir = new File(DATA_HOME + PipelineFinal.ARBESS_SCM);
        PipelineFileUtil.createDirectory(destDir.getAbsolutePath());

        String destPath = destDir.getAbsolutePath() + "/" + remoteFile.getFileName();
        String replace = remoteFile.getDownloadUrl().replace("tools", "/tools/download");

        String fileUrl = remoteDownloadUrl + replace;

        remoteFile.setDownloadSize("0kb");
        remoteFile.setStatus("0");
        remoteFile.setLocalPath(destPath);
        fileMap.put(id,remoteFile);

        try {
            logger.info("scm download file from {} to {}",fileUrl,destPath);
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000); // 10秒超时
            connection.setReadTimeout(30000);    // 30秒读取超时
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("下载失败，HTTP 响应码: " + responseCode);
            }

            // 确保父目录存在
            Path dest = Path.of(destPath);
            Files.createDirectories(dest.getParent());

            new Thread(() -> {
                try (InputStream in = connection.getInputStream();
                     OutputStream out = new FileOutputStream(dest.toFile())) {

                    fileMap.put(id,fileMap.get(id).setStatus("1"));

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }catch (Exception e) {
                    fileMap.put(id,fileMap.get(id).setStatus("3"));
                } finally {
                    connection.disconnect();
                }
                fileMap.put(id,fileMap.get(id).setStatus("2"));
            }).start();
        } catch (Exception e) {
            fileMap.put(id,fileMap.get(id).setStatus("3"));
            throw new SystemException(e.getMessage());
        }

        return destPath;
    }

    @Override
    public void downloadAndInstall(ScmRemoteFile remoteFile)  {
        String id = remoteFile.getId();
        fileMap.remove(id);
        new Thread(() -> {
            String destPath;
            try {
                destPath = downloadFile(remoteFile);
            } catch (Exception e) {
                throw new SystemException("文件下载失败："+e.getMessage());
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // String id = remoteFile.getId();
            ScmRemoteFile scmRemoteFile = fileMap.get(id);
            if (Objects.isNull(scmRemoteFile)){
                throw new SystemException("获取状态失败！");
            }
            String status = scmRemoteFile.getStatus();
            while (status.equals("0") || status.equals("1")){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                scmRemoteFile = fileMap.get(id);
                status = scmRemoteFile.getStatus();
            }

            if (!scmRemoteFile.getStatus().equals("2")){
                return ;
            }

            fileMap.put(id,fileMap.get(id).setStatus("4"));
            String binPath;
            try {
                binPath = fileFileBinPath(destPath);
            }catch (Exception e){
                fileMap.put(id,fileMap.get(id).setStatus("6"));
                throw new SystemException("文件解压失败："+e.getMessage());
            }

            fileMap.put(id,fileMap.get(id).setStatus("5").setBinPath(binPath));

        }).start();
    }


    @Override
    public ScmRemoteFile findScmRemoteFile(String id){

        ScmRemoteFile scmRemoteFile = fileMap.get(id);

        if (Objects.isNull(scmRemoteFile)){
            return null;
        }

        String localPath = scmRemoteFile.getLocalPath();

        File file = new File(localPath);
        if (file.exists()){
            String size = formatSize(file.length());
            scmRemoteFile.setDownloadSize(size);
        }

        return scmRemoteFile;
    }

    // 添加权限
    private void addChmod(File destDir, String s){

        String absolutePath = destDir.getAbsolutePath();
        String order;
        logger.info("添加权限: : {}",absolutePath);
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

    public String tarXz(File file,File destDir ){
        String s;
        try {
            s = extractTarXz(file, destDir);
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

    /**
     * 解压 tar.xz 文件到目标目录，并返回解压出的根目录名称
     * @param tarXzFile 输入的 tar.xz 文件
     * @param destDir   目标解压目录
     * @return 解压出的根目录名称（如 package-1.0.0)
     * @throws IOException IO 异常
     */
    public static String extractTarXz(File tarXzFile, File destDir) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        String rootDirName = null;

        try (FileInputStream fis = new FileInputStream(tarXzFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             XZCompressorInputStream xis = new XZCompressorInputStream(bis);
             TarArchiveInputStream tis = new TarArchiveInputStream(xis)) {

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
     * 格式化文件大小
     * @param size 文件大小
     * @return 格式化后的文件大小
     */
    private static String formatSize(long size) {
        double kb = 1024.0;
        double mb = kb * 1024;
        double gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.2f GB", size / gb);
        } else if (size >= mb) {
            return String.format("%.2f MB", size / mb);
        } else if (size >= kb) {
            return String.format("%.2f KB", size / kb);
        } else {
            return size + " B";
        }
    }

}
