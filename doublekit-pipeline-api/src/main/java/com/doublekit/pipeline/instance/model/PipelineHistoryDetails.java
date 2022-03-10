package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
public class PipelineHistoryDetails {

    //id
    @ApiProperty(name="historyId",desc="历史id")
    private String historyId;

    //状态
    @ApiProperty(name="status",desc="状态")
    private int status;

    //构建次数
    @ApiProperty(name = "historyNumber",desc="构建次数")
    private int historyNumber;

    //创建构建时间
    @ApiProperty(name="createStructureTime",desc="创建构建时间")
    private String createStructureTime;

    //构建方式
    @ApiProperty(name="structureWay",desc="构建方式")
    private int structureWay;

    //执行人
    @ApiProperty(name="implementor",desc="执行人")
    private String implementor;

    //执行时长
    @ApiProperty(name="implementTime",desc="执行时长")
    private int implementTime;

    //代码源
    @ApiProperty(name="codeSource",desc="代码源")
    private int codeSource;

    //凭证
    @ApiProperty(name="proof",desc="凭证")
    private String proof;

    //分支
    @ApiProperty(name="branch",desc="分支")
    private String branch;

    public String getHistoryId() {
        return historyId;
    }

    public int getHistoryNumber() {
        return historyNumber;
    }

    public void setHistoryNumber(int historyNumber) {
        this.historyNumber = historyNumber;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateStructureTime() {
        return createStructureTime;
    }

    public void setCreateStructureTime(String createStructureTime) {
        this.createStructureTime = createStructureTime;
    }

    public int getStructureWay() {
        return structureWay;
    }

    public void setStructureWay(int structureWay) {
        this.structureWay = structureWay;
    }

    public String getImplementor() {
        return implementor;
    }

    public void setImplementor(String implementor) {
        this.implementor = implementor;
    }

    public int getImplementTime() {
        return implementTime;
    }

    public void setImplementTime(int implementTime) {
        this.implementTime = implementTime;
    }

    public int getCodeSource() {
        return codeSource;
    }

    public void setCodeSource(int codeSource) {
        this.codeSource = codeSource;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
