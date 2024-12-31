package io.tiklab.arbess.support.disk.model;

/**
 * 磁盘模型
 */
public class Disk {

    /**
     * 磁盘名称
     */
    private String name;

    /**
     * 目录大小
     */
    private String dirSize;

    /**
     * 磁盘大小
     */
    private String diskSize;

    /**
     * 用户大小
     */
    private String userSize;

    /**
     * 路径
     */
    private String path;

    /**
     * 文件路径
     */
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public Disk setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Disk setPath(String path) {
        this.path = path;
        return this;
    }

    public String getName() {
        return name;
    }

    public Disk setName(String name) {
        this.name = name;
        return this;
    }

    public String getDirSize() {
        return dirSize;
    }

    public Disk setDirSize(String dirSize) {
        this.dirSize = dirSize;
        return this;
    }

    public String getDiskSize() {
        return diskSize;
    }

    public Disk setDiskSize(String diskSize) {
        this.diskSize = diskSize;
        return this;
    }

    public String getUserSize() {
        return userSize;
    }

    public Disk setUserSize(String userSize) {
        this.userSize = userSize;
        return this;
    }
}
