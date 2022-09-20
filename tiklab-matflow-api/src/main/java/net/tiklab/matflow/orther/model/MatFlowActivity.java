package net.tiklab.matflow.orther.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.user.user.model.User;

@ApiModel
@Join
@Mapper(targetAlias = "MatFlowActivityEntity")
public class MatFlowActivity {

    @ApiProperty(name = "id")
    private String id;

    @ApiProperty(name = "createTime")
    private String createTime;

    @ApiProperty(name = "massage")
    private String massage ;

    @ApiProperty(name = "news")
    private String news ;

    @ApiProperty(name="user",desc="认证配置",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    //流水线
    @ApiProperty(name="matFlow",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matFlow.matflowId",target = "matflowId")
    })
    @JoinQuery(key = "matflowId")
    private MatFlow matFlow;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }


    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MatFlow getMatFlow() {
        return matFlow;
    }

    public void setMatFlow(MatFlow matFlow) {
        this.matFlow = matFlow;
    }
}
