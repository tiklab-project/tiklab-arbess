package io.tiklab.arbess.task.code.model;

import io.tiklab.core.BaseModel;

/**
 * 第三方分支模型
 */
public class ThirdBranch extends BaseModel {

    /**
     * ID
     */ 
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否受保护
     */
    private Boolean isProtected = false ;

    /**
     * 是否默认
     */
    private Boolean isDefault;

    public ThirdBranch() {
    }

    public ThirdBranch(String id, String name, Boolean isProtected, Boolean isDefault) {
        this.id = id;
        this.name = name;
        this.isProtected = isProtected;
        this.isDefault = isDefault;
    }

    public String getId() {
        return id;
    }

    public ThirdBranch setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ThirdBranch setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getProtected() {
        return isProtected;
    }

    public ThirdBranch setProtected(Boolean aProtected) {
        isProtected = aProtected;
        return this;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public ThirdBranch setDefault(Boolean aDefault) {
        isDefault = aDefault;
        return this;
    }
}
