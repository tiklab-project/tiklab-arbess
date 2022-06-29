package com.doublekit.pipeline.setting.other.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;

@ApiModel
public class SystemMassage {

    @ApiProperty(name="JavaVersion",desc="java版本")
    private String JavaVersion = System.getProperty("java.version");


    @ApiProperty(name="userName",desc="用户名")
    private String userName = System.getProperty("user.name");


    @ApiProperty(name="userName",desc="系统名称")
    private String osName = System.getProperty("os.name");


    @ApiProperty(name="userDir",desc="应用地址")
    private String userDir = System.getProperty("user.dir");


    public String getJavaVersion() {
        return JavaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        JavaVersion = javaVersion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }
}
