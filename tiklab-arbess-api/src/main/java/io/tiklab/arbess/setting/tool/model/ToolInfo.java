package io.tiklab.arbess.setting.tool.model;

public class ToolInfo {

    private boolean installed;

    private String type;

    private String version;

    private String path;

    public ToolInfo() {
    }

    public ToolInfo(boolean installed, String version, String path) {
        this.installed = installed;
        this.version = version;
        this.path = path;
    }

    public ToolInfo(boolean installed, String type, String version, String path) {
        this.installed = installed;
        this.type = type;
        this.version = version;
        this.path = path;
    }

    public ToolInfo setInstalled(boolean installed) {
        this.installed = installed;
        return this;
    }

    public ToolInfo setType(String type) {
        this.type = type;
        return this;
    }

    public ToolInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public ToolInfo setPath(String path) {
        this.path = path;
        return this;
    }

    public boolean isInstalled() { return installed; }

    public String getType() { return type; }

    public String getVersion() { return version; }

    public String getPath() { return path; }


}
