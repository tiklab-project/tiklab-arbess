package io.tiklab.arbess.setting.tool.model;

public class ScmRemoteFile {

    // ID
    private String id;

    // 文件名称
    private String fileName;

    // 下载路径
    private String downloadUrl;

    // 已下载
    private String downloadSize = "0kb";

    // 文件路径
    private String localPath;

    // 0: 准备下载 1：下载中 2：下载完成 3.下载失败 4：解压中 5：完成 6：解压失败
    private String status;

    // bin路径
    private String binPath;

    public String getBinPath() {
        return binPath;
    }

    public ScmRemoteFile setBinPath(String binPath) {
        this.binPath = binPath;
        return this;
    }

    public String getId() {
        return id;
    }

    public ScmRemoteFile setId(String id) {
        this.id = id;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ScmRemoteFile setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public ScmRemoteFile setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    public String getDownloadSize() {
        return downloadSize;
    }

    public ScmRemoteFile setDownloadSize(String downloadSize) {
        this.downloadSize = downloadSize;
        return this;
    }

    public String getLocalPath() {
        return localPath;
    }

    public ScmRemoteFile setLocalPath(String localPath) {
        this.localPath = localPath;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ScmRemoteFile setStatus(String status) {
        this.status = status;
        return this;
    }
}
