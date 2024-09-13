package io.thoughtware.arbess.support.disk.model;

public class Disk {

    private String name;

    private String dirSize;

    private String diskSize;

    private String userSize;

    private String path;

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
