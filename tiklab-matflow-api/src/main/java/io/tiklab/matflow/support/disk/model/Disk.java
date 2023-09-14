package io.tiklab.matflow.support.disk.model;

import java.util.List;

public class Disk {

    private String name;

    private String dirSize;


    private String diskSize;

    private String userSize;

    private String pipelineId;

    private List<String> pipelineList;


    private List<Disk> diskList;

    public String getPipelineId() {
        return pipelineId;
    }

    public Disk setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public List<Disk> getDiskList() {
        return diskList;
    }

    public Disk setDiskList(List<Disk> diskList) {
        this.diskList = diskList;
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

    public List<String> getPipelineList() {
        return pipelineList;
    }

    public Disk setPipelineList(List<String> pipelineList) {
        this.pipelineList = pipelineList;
        return this;
    }
}
