package com.tiklab.matflow.setting.path.entity;


import com.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="matflow_path")
public class MatFlowPathEntity {

    //凭证id
    @Id
    @GeneratorValue
    @Column(name = "path_id")
    private String pathId;

    //1.git 2.svn 11.node 12.maven
    @Column(name = "path_type")
    private int pathType;

    //名称
    @Column(name = "path_name")
    private String pathName;

    //地址
    @Column(name = "path_address")
    private String pathAddress;

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public int getPathType() {
        return pathType;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathAddress() {
        return pathAddress;
    }

    public void setPathAddress(String pathAddress) {
        this.pathAddress = pathAddress;
    }
}
