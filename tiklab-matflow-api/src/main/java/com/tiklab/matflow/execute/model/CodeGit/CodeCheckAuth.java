package com.tiklab.matflow.execute.model.CodeGit;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;

@ApiModel
public class CodeCheckAuth {


    @ApiProperty(name="url",desc="地址")
    private String url;

    @ApiProperty(name="port",desc="端口")
    private int port;

    @ApiProperty(name="proofId",desc="凭证id")
    private String proofId;

    @ApiProperty(name="type",desc="类型")
    private int type;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
