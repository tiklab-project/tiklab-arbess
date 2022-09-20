package net.tiklab.matflow.definition.model;



import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.user.user.model.User;

@ApiModel
@Join
@Mapper(targetAlias = "MatFlowEntity")
public class MatFlow {

    //流水线id
    @ApiProperty(name="matflowId",desc="流水线id")
    private String matflowId;

    //流水线名称
    @ApiProperty(name="matflowName",desc="流水线名称",required = true)
    private String matflowName;

    //流水线创建人
    @ApiProperty(name="user",desc="认证配置",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    //流水线创建时间
    @ApiProperty(name="matflowCreateTime",desc="流水线创建时间",required = true)
    private String matflowCreateTime;

    //流水线类型
    @ApiProperty(name="matflowType",desc="流水线类型",required = true)
    private int matflowType;

    //收藏状态
    @ApiProperty(name="matflowCollect",desc="收藏状态",required = true)
    private int matflowCollect;

    //运行状态
    @ApiProperty(name="matflowCollect",desc="运行状态",required = true)
    private int matflowState;

    @ApiProperty(name="matflowPower",desc="项目作用域",required = true)
    private int matflowPower;

    public String getMatflowId() {
        return matflowId;
    }

    public void setMatflowId(String matflowId) {
        this.matflowId = matflowId;
    }

    public String getMatflowName() {
        return matflowName;
    }

    public void setMatflowName(String matflowName) {
        this.matflowName = matflowName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMatflowCreateTime() {
        return matflowCreateTime;
    }

    public void setMatflowCreateTime(String matflowCreateTime) {
        this.matflowCreateTime = matflowCreateTime;
    }

    public int getMatflowType() {
        return matflowType;
    }

    public void setMatflowType(int matflowType) {
        this.matflowType = matflowType;
    }

    public int getMatflowCollect() {
        return matflowCollect;
    }

    public void setMatflowCollect(int matflowCollect) {
        this.matflowCollect = matflowCollect;
    }

    public int getMatflowState() {
        return matflowState;
    }

    public void setMatflowState(int matflowState) {
        this.matflowState = matflowState;
    }

    public int getMatflowPower() {
        return matflowPower;
    }

    public void setMatflowPower(int matflowPower) {
        this.matflowPower = matflowPower;
    }
}
