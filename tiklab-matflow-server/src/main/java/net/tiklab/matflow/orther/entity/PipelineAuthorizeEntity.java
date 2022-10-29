package net.tiklab.matflow.orther.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_authorize")
public class PipelineAuthorizeEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    @Column(name = "type")
    private Integer type;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "callback_url")
    private String callbackUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
