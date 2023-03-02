package net.tiklab.matflow.task.test.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_test")
public class TaskTestEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "task_name" ,notNull = true)
    private String taskName;

    //地址
    @Column(name = "test_order",notNull = true)
    private String testOrder;

    @Column(name = "address")
    private String address;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
