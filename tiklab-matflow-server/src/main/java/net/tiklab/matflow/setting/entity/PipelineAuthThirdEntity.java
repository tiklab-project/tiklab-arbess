package net.tiklab.matflow.setting.entity;

import net.tiklab.dal.jpa.annotation.*;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.utils.context.LoginContext;
@Entity
@Table(name="pipeline_auth_Third")
public class PipelineAuthThirdEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String thirdId;

    @Column(name = "type")
    private int authType;

    @Column(name = "name")
    private String names;

    @Column(name = "username")
    private String username;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "create_time")
    private String createTime = PipelineUntil.date;

    //创建人
    @Column(name = "user_id")
    private String userId = LoginContext.getLoginId();

    //是否公开 true：公开， false：不公开
    @Column(name = "auth_public")
    private int authPublic;


    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAuthPublic() {
        return authPublic;
    }

    public void setAuthPublic(int authPublic) {
        this.authPublic = authPublic;
    }
}
