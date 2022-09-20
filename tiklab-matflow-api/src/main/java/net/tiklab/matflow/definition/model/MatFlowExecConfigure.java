package net.tiklab.matflow.definition.model;


import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.user.user.model.User;

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

    @ApiProperty(name="matFlowBuild",desc="构建",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matFlowBuild.buildId",target = "buildId")
    })
    @JoinQuery(key = "buildId")
    private MatFlowBuild matFlowBuild;

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

    public MatFlowBuild getMatFlowBuild() {
        return matFlowBuild;
    }

    public void setMatFlowBuild(MatFlowBuild matFlowBuild) {
        this.matFlowBuild = matFlowBuild;
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
