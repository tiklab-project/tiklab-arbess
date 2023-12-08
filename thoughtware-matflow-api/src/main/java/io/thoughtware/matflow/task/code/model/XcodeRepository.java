package io.thoughtware.matflow.task.code.model;

public class XcodeRepository {

    private String rpyId;

    private String name;

    private String fullPath;


    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getRpyId() {
        return rpyId;
    }

    public void setRpyId(String rpyId) {
        this.rpyId = rpyId;
    }

    public String getName() {
        return name;
    }

    public XcodeRepository setName(String name) {
        this.name = name;
        return this;
    }
}
