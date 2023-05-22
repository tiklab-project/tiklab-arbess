package io.tiklab.matflow.task.test.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.teston.repository.model.Repository;
import io.tiklab.teston.support.environment.model.ApiEnv;
import io.tiklab.teston.support.environment.model.AppEnv;
import io.tiklab.teston.support.environment.model.WebEnv;
import io.tiklab.teston.testplan.cases.model.TestPlan;

/**
 * 任务测试模型
 */
@ApiModel
@Join
@Mapper(targetAlias = "TaskTestEntity")
public class TaskTest {

    @ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //测试内容
    @ApiProperty(name="testOrder",desc="测试内容",required = true)
    private String testOrder;

    @ApiProperty(name="address",desc="测试地址",required = true)
    private String address;

    @ApiProperty(name = "testSpace",desc="测试空间")
    @Mappings({
            @Mapping(source = "testSpace.id",target = "testSpace")
    })
    @JoinQuery(key = "testSpace")
    private Repository testSpace;

    @ApiProperty(name = "testPlan",desc="测试计划")
    @Mappings({
            @Mapping(source = "testPlan.id",target = "testPlan")
    })
    @JoinQuery(key = "testPlan")
    private TestPlan testPlan;

    @ApiProperty(name = "apiEnv",desc="api环境")
    @Mappings({
            @Mapping(source = "apiEnv.id",target = "apiEnv")
    })
    @JoinQuery(key = "id")
    private ApiEnv apiEnv;

    @ApiProperty(name = "appEnv",desc="app环境")
    @Mappings({
            @Mapping(source = "appEnv.id",target = "appEnv")
    })
    @JoinQuery(key = "appEnv")
    private AppEnv appEnv;

    @ApiProperty(name = "webEnv",desc="web环境")
    @Mappings({
            @Mapping(source = "webEnv.id",target = "webEnv")
    })
    @JoinQuery(key = "webEnv")
    private WebEnv webEnv;

    @ApiProperty(name = "authId",desc="认证id")
    private String authId;

    private Object auth;

    //测试类型
    private String type;

    //顺序
    private int sort;

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

    public ApiEnv getApiEnv() {
        return apiEnv;
    }

    public void setApiEnv(ApiEnv apiEnv) {
        this.apiEnv = apiEnv;
    }

    public AppEnv getAppEnv() {
        return appEnv;
    }

    public void setAppEnv(AppEnv appEnv) {
        this.appEnv = appEnv;
    }

    public WebEnv getWebEnv() {
        return webEnv;
    }

    public void setWebEnv(WebEnv webEnv) {
        this.webEnv = webEnv;
    }

    public Repository getTestSpace() {
        return testSpace;
    }

    public void setTestSpace(Repository testSpace) {
        this.testSpace = testSpace;
    }

    public TestPlan getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestPlan testPlan) {
        this.testPlan = testPlan;
    }

    public String getTaskId() {
        return taskId;
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
}
