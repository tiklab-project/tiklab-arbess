package io.thoughtware.arbess.task.code.model;

import io.thoughtware.core.BaseModel;

public class ThirdHouse extends BaseModel {

    private String id;

    private String path;

    private String name;

    private String pathWithSpace;

    private String nameWithSpace;

    private String houseWebUrl;

    private String houseSshUrl;

    private String defaultBranch;

    public String getId() {
        return id;
    }

    public ThirdHouse setId(String id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ThirdHouse setPath(String path) {
        this.path = path;
        return this;
    }

    public String getName() {
        return name;
    }

    public ThirdHouse setName(String name) {
        this.name = name;
        return this;
    }

    public String getPathWithSpace() {
        return pathWithSpace;
    }

    public ThirdHouse setPathWithSpace(String pathWithSpace) {
        this.pathWithSpace = pathWithSpace;
        return this;
    }

    public String getNameWithSpace() {
        return nameWithSpace;
    }

    public ThirdHouse setNameWithSpace(String nameWithSpace) {
        this.nameWithSpace = nameWithSpace;
        return this;
    }

    public String getHouseWebUrl() {
        return houseWebUrl;
    }

    public ThirdHouse setHouseWebUrl(String houseWebUrl) {
        this.houseWebUrl = houseWebUrl;
        return this;
    }

    public String getHouseSshUrl() {
        return houseSshUrl;
    }

    public ThirdHouse setHouseSshUrl(String houseSshUrl) {
        this.houseSshUrl = houseSshUrl;
        return this;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public ThirdHouse setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
        return this;
    }
}
