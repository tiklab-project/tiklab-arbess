package io.tiklab.arbess.task.code.model;

import io.tiklab.core.BaseModel;

/**
 * 第三方用户模型
 */
public class ThirdUser extends BaseModel {


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
     * 头像
     */
    private String head;

    public ThirdUser() {
    }

    public ThirdUser(String id, String path, String name, String head) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.head = head;
    }

    public String getId() {
        return id;
    }

    public ThirdUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ThirdUser setPath(String path) {
        this.path = path;
        return this;
    }

    public String getName() {
        return name;
    }

    public ThirdUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getHead() {
        return head;
    }

    public ThirdUser setHead(String head) {
        this.head = head;
        return this;
    }
}
