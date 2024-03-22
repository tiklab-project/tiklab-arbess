package io.thoughtware.matflow.task.code.model;

import io.thoughtware.core.BaseModel;

public class ThirdBranch extends BaseModel {

    private String id;
    private String name;

    private Boolean isProtected = false ;

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
