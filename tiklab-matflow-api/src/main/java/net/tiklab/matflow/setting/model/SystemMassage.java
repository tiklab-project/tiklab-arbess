package net.tiklab.matflow.setting.model;



import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import java.net.InetAddress;
import java.net.UnknownHostException;

@ApiModel
public class SystemMassage {

    @ApiProperty(name="workspace",desc="系统版本")
    private String workspace;

    @ApiProperty(name="JavaVersion",desc="java版本")
    private String JavaVersion = System.getProperty("java.version");

    @ApiProperty(name="javaHome",desc="java安装路径")
    private String javaHome = System.getProperty("java.home");

    @ApiProperty(name="userName",desc="用户名")
    private String userName = System.getProperty("user.name");

    @ApiProperty(name="userName",desc="应用地址")
    private String osName = System.getProperty("os.name");

    @ApiProperty(name="ip",desc="ip地址")
    private String ip ;

    @ApiProperty(name="userDir",desc="系统版本")
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

    public String getJavaHome() {
        return javaHome;
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    public String getIp() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return  null;
        }
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }
}
