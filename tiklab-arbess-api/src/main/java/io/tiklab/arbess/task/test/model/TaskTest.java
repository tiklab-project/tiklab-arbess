package io.tiklab.arbess.task.test.model;


import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinField;


/**
 * 任务测试模型
 */
//@ApiModel
@Join
@Mapper
public class TaskTest {

    //@ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //测试内容
    //@ApiProperty(name="testOrder",desc="测试内容",required = true)
    private String testOrder;

    //@ApiProperty(name="address",desc="测试地址",required = true)
    private String address;

    @Mappings({
            @Mapping(source = "testSpace.id",target = "testSpace")
    })
    @JoinField(key = "testSpace")
    private TestHuboRpy testSpace;

    @Mappings({
            @Mapping(source = "testPlan.id",target = "testPlan")
    })
    @JoinField(key = "testPlan")
    private TestHuboTestPlan testPlan;

    @Mappings({
            @Mapping(source = "testEnv.id",target = "testEnv")
    })
    @JoinField(key = "testEnv")
    private TestHuboEnv testEnv;

    //@ApiProperty(name = "authId",desc="认证id")
    private String authId;

    private Object auth;

    //测试类型
    private String type;

    //顺序
    private int sort;

    private String instanceId;

    // jdk版本
    @Mappings({
            @Mapping(source = "toolJdk.scmId",target = "toolJdk")
    })
    @JoinField(key = "scmId")
    private Scm toolJdk;

    // maven版本
    @Mappings({
            @Mapping(source = "toolMaven.scmId",target = "toolMaven")
    })
    @JoinField(key = "scmId")
    private Scm toolMaven;


    public Scm getToolJdk() {
        return toolJdk;
    }

    public void setToolJdk(Scm toolJdk) {
        this.toolJdk = toolJdk;
    }

    public Scm getToolMaven() {
        return toolMaven;
    }

    public void setToolMaven(Scm toolMaven) {
        this.toolMaven = toolMaven;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getTaskId() {
        return taskId;
    }


    public TestHuboRpy getTestSpace() {
        return testSpace;
    }

    public void setTestSpace(TestHuboRpy testSpace) {
        this.testSpace = testSpace;
    }


    public TestHuboTestPlan getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestHuboTestPlan testPlan) {
        this.testPlan = testPlan;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TestHuboEnv getTestEnv() {
        return testEnv;
    }

    public void setTestEnv(TestHuboEnv testEnv) {
        this.testEnv = testEnv;
    }
}
