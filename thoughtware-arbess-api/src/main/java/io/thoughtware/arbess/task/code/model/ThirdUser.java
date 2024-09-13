package io.thoughtware.arbess.task.code.model;

import io.thoughtware.core.BaseModel;

public class ThirdUser extends BaseModel {

    private String id;

    private String path;

    private String name;

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
