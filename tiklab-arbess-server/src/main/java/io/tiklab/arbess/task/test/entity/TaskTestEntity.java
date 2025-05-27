package io.tiklab.arbess.task.test.entity;


import io.tiklab.dal.jpa.annotation.*;

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

    @Column(name = "test_env")
    private String testEnv;

    @Column(name = "auth_id")
    private String authId;

    // jdk版本
    @Column(name = "tool_jdk")
    private String toolJdk;

    // maven版本
    @Column(name = "tool_maven")
    private String toolMaven;

    public String getToolJdk() {
        return toolJdk;
    }

    public void setToolJdk(String toolJdk) {
        this.toolJdk = toolJdk;
    }

    public String getToolMaven() {
        return toolMaven;
    }

    public void setToolMaven(String toolMaven) {
        this.toolMaven = toolMaven;
    }

    public String getTestEnv() {
        return testEnv;
    }

    public void setTestEnv(String testEnv) {
        this.testEnv = testEnv;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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
