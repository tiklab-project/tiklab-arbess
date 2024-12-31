package io.tiklab.arbess.task.code.model;

import io.tiklab.core.BaseModel;

/**
 * 第三方仓库模型
 */
public class ThirdHouse extends BaseModel {

    /**
     * ID
     */
    private String id;

    /**
     * 路径
     */
    private String path;

    /**
     * 名称
     */     
    private String name;

    /**
     * 路径
     */
    private String pathWithSpace;

    /**
     * 名称
     */
    private String nameWithSpace;

    /**
     * 仓库web地址
     */
    private String houseWebUrl;

    /**
     * 仓库ssh地址
     */
    private String houseSshUrl;

    /**
     * 默认分支
     */
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
