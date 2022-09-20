package net.tiklab.matflow.orther.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="matflow_open")
public class MatFlowOpenEntity {

    @Id
    @GeneratorValue
    @Column(name = "open_id")
    private String id;

    @Column(name = "matflow_id")
    private String matflowId;

    @Column(name = "number")
    private int number ;

    @Column(name = "user_id")
    private String userId ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatflowId() {
        return matflowId;
    }

    public void setMatflowId(String matflowId) {
        this.matflowId = matflowId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
