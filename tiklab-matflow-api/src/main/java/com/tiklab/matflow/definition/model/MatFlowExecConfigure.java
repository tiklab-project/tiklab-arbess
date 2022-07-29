package com.tiklab.matflow.definition.model;


import com.tiklab.beans.annotation.Mapping;
import com.tiklab.beans.annotation.Mappings;
import com.tiklab.join.annotation.Join;
import com.tiklab.join.annotation.JoinQuery;
import com.tiklab.matflow.execute.model.MatFlowCode;
import com.tiklab.matflow.execute.model.MatFlowDeploy;
import com.tiklab.matflow.execute.model.MatFlowStructure;
import com.tiklab.matflow.execute.model.MatFlowTest;
import com.tiklab.postlink.annotation.ApiModel;
import com.tiklab.postlink.annotation.ApiProperty;
import com.tiklab.user.user.model.User;

/**
 * 需要保存流水线配置信息
 */

@ApiModel
@Join
public class MatFlowExecConfigure {

    //流水线
    @ApiProperty(name="matFlow",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matflow.matFlowId",target = "matflowId")
    })
    @JoinQuery(key = "matflowId")
    private MatFlow matFlow;

    @ApiProperty(name="user",desc="用户",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name="configureCreateTime",desc="id")
    private String configureCreateTime;


    @ApiProperty(name="matFlowCode",desc="源码管理",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matFlowCode.codeId",target = "matFlowId")
    })
    @JoinQuery(key = "codeId")
    private MatFlowCode matFlowCode;

    @ApiProperty(name="matFlowTest",desc="测试",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matFlowTest.testId",target = "testId")
    })
    @JoinQuery(key = "testId")
    private MatFlowTest matFlowTest;

    @ApiProperty(name="matFlowStructure",desc="构建",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matFlowStructure.structureId",target = "structureId")
    })
    @JoinQuery(key = "structureId")
    private MatFlowStructure matFlowStructure;

    @ApiProperty(name="matFlowDeploy",desc="部署",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matFlowDeploy.deployId",target = "deployId")
    })
    @JoinQuery(key = "deployId")
    private MatFlowDeploy matFlowDeploy;

    @ApiProperty(name="view",desc="视图")
    private int view;


    public MatFlow getMatFlow() {
        return matFlow;
    }

    public void setMatFlow(MatFlow matFlow) {
        this.matFlow = matFlow;
    }

    public String getConfigureCreateTime() {
        return configureCreateTime;
    }

    public void setConfigureCreateTime(String configureCreateTime) {
        this.configureCreateTime = configureCreateTime;
    }

    public MatFlowCode getMatFlowCode() {
        return matFlowCode;
    }

    public void setMatFlowCode(MatFlowCode matFlowCode) {
        this.matFlowCode = matFlowCode;
    }

    public MatFlowTest getMatFlowTest() {
        return matFlowTest;
    }

    public void setMatFlowTest(MatFlowTest matFlowTest) {
        this.matFlowTest = matFlowTest;
    }

    public MatFlowStructure getMatFlowStructure() {
        return matFlowStructure;
    }

    public void setMatFlowStructure(MatFlowStructure matFlowStructure) {
        this.matFlowStructure = matFlowStructure;
    }

    public MatFlowDeploy getMatFlowDeploy() {
        return matFlowDeploy;
    }

    public void setMatFlowDeploy(MatFlowDeploy matFlowDeploy) {
        this.matFlowDeploy = matFlowDeploy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
