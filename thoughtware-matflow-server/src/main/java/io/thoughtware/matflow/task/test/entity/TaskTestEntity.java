package io.thoughtware.matflow.task.test.entity;


import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_test")
public class TaskTestEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    //地址
    @Column(name = "test_order",notNull = true)
    private String testOrder;

    @Column(name = "address")
    private String address;

    @Column(name = "test_space")
    private String testSpace;

    @Column(name = "test_plan")
    private String testPlan;

    @Column(name = "api_env")
    private String apiEnv;

    @Column(name = "app_env")
    private String appEnv;

    @Column(name = "web_env")
    private String webEnv;

    @Column(name = "auth_id")
    private String authId;


    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getApiEnv() {
        return apiEnv;
    }

    public void setApiEnv(String apiEnv) {
        this.apiEnv = apiEnv;
    }

    public String getAppEnv() {
        return appEnv;
    }

    public void setAppEnv(String appEnv) {
        this.appEnv = appEnv;
    }

    public String getWebEnv() {
        return webEnv;
    }

    public void setWebEnv(String webEnv) {
        this.webEnv = webEnv;
    }

    public String getTestSpace() {
        return testSpace;
    }

    public void setTestSpace(String testSpace) {
        this.testSpace = testSpace;
    }

    public String getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(String testPlan) {
        this.testPlan = testPlan;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
