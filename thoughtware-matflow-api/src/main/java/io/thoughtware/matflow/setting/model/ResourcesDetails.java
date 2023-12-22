package io.thoughtware.matflow.setting.model;


import java.util.List;
import java.util.Map;

public class ResourcesDetails {


    private String type;

    private String sourceCache;

    private String artifactCache;

    private List<String> list;


    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceCache() {
        return sourceCache;
    }

    public void setSourceCache(String sourceCache) {
        this.sourceCache = sourceCache;
    }

    public String getArtifactCache() {
        return artifactCache;
    }

    public void setArtifactCache(String artifactCache) {
        this.artifactCache = artifactCache;
    }
}













