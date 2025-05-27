package io.tiklab.arbess.setting.k8s.model;

public class KubectlVersion {

    // 服务端diz
    private String serverAddress;

    // 服务端版本
    private String serverVersion;

    // 客户端版本
    private String clientVersion;


    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}
