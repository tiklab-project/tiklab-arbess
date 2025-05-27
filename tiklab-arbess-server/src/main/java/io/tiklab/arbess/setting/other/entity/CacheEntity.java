package io.tiklab.arbess.setting.other.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_setting_cache")
public class CacheEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    @Column(name = "log_cache" )
    private int logCache;

    @Column(name = "artifact_cache" )
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
