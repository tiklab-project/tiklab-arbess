package io.tiklab.arbess.task.codescan.model;

import io.tiklab.postin.annotation.ApiProperty;

import java.io.Serializable;

public class SourceFareProject implements Serializable {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="name",desc="项目名称")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}