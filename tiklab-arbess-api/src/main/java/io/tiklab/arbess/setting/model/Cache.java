package io.tiklab.arbess.setting.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Mapper
@Join
public class Cache {


    private String id;


    private int logCache;


    private int artifactCache;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLogCache() {
        return logCache;
    }

    public void setLogCache(int logCache) {
        this.logCache = logCache;
    }

    public int getArtifactCache() {
        return artifactCache;
    }

    public void setArtifactCache(int artifactCache) {
        this.artifactCache = artifactCache;
    }
}
