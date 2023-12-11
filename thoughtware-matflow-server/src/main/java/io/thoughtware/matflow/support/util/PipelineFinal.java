package io.thoughtware.matflow.support.util;

public class PipelineFinal {

    /**
     * 项目名称
     */
    public static final String appName = "matflow";

    /**
     * DEFAULT
      */
    public static final String DEFAULT = "default";

    /**
     * 流水线文件系统
     */
    public static final String MATFLOW_WORKSPACE = "/source";

    public static final String MATFLOW_LOGS = "/artifact";


    /**
     * 流水线运行状态
     */
    //流水线运行状态
    public static final String RUN_SUCCESS = "success";

    public static final String RUN_ERROR = "error";

    public static final String RUN_WAIT = "wait";

    public static final String RUN_HALT = "halt";

    public static final String RUN_RUN = "run";


    /**
     * 系统编码
     */
    //字节编码
    public static final String UTF_8 = "UTF-8";

    public static final String GBK = "GBK";


    /**
     * 消息
     */
    //消息发送类型
    public static final String MES_PIPELINE_RUN = "PIPELINE_RUN";
    public static final String MES_PIPELINE_TASK_RUN = "INE_TASK_RUN";

    //消息发送方式
    public static final String MES_SEND_SITE = "site";
    public static final String MES_SEND_EMAIL = "email";
    public static final String MES_SEND_DINGDING = "dingding";
    public static final String MES_SEND_WECHAT = "qywechat";
    public static final String MES_SEND_SMS = "sms";

    //消息通知方案
    public static final String MES_UPDATE = "MES_UPDATE";
    public static final String MES_DELETE = "MES_DELETE";
    public static final String MES_CREATE = "MES_CREATE";
    public static final String MES_RUN = "MES_RUN";


    /**
     * 日志信息
     */
    //日志类型
    public static final String LOG_PIPELINE = "LOG_PIPELINE"; //流水线

    public static final String LOG_RUN = "LOG_RUN"; //运行

    //日志模板
    public static final String LOG_TEM_CREATE = "G_TEM_CREATE";
    public static final String LOG_TEM_DELETE = "G_TEM_DELETE";
    public static final String LOG_TEM_UPDATE = "G_TEM_UPDATE";
    public static final String LOG_TEM_RUN = "LOG_TEM_RUN";


    /**
     * 构建产物信息
     */
    // 默认制品地址
    public static final String PROJECT_DEFAULT_ADDRESS = "${PROJECT_DEFAULT_ADDRESS}";

    public static final String DEFAULT_ARTIFACT_ADDRESS = "DEFAULT_ARTIFACT_ADDRESS";

    // 默认制品
    public static final String DEFAULT_ARTIFACT_NAME = "DEFAULT_ARTIFACT_NAME";

    // Docker制品
    public static final String DEFAULT_ARTIFACT_DOCKER = "DEFAULT_ARTIFACT_DOCKER";

    // Docker名称
    public static final String DEFAULT_ARTIFACT_DOCKER_NAME = "DEFAULT_ARTIFACT_DOCKER_NAME";



    // 默认源码位置
    public static final String DEFAULT_CODE_ADDRESS = "${DEFAULT_CODE_ADDRESS}";

    public static final String DEFAULT_TYPE = "string";


    /**
     * 文件信息
     */
    public static final String FILE_TEMP_PREFIX = "temp";
    public static final String FILE_TYPE_TXT = ".txt";
    public static final String FILE_TYPE_SH = ".sh";

    public static final String FILE_TYPE_BAT = ".bat";


    /**
     * 系统任务类型
     */

    // 源码应用类型
    public static final String TASK_TYPE_CODE = "code";
    public static final String TASK_CODE_GIT = "git";
    public static final String TASK_CODE_GITLAB = "gitlab";
    public static final String TASK_CODE_GITHUB = "github";
    public static final String TASK_CODE_GITEE = "gitee";
    public static final String TASK_CODE_SVN = "svn";
    public static final String TASK_CODE_XCODE = "gittork";
    public static final String TASK_CODE_DEFAULT_BRANCH = "master";


    // 构建应用类型
    public static final String TASK_TYPE_BUILD = "build";
    public static final String TASK_BUILD_MAVEN = "maven";
    public static final String TASK_BUILD_NODEJS = "nodejs";

    public static final String TASK_BUILD_DOCKER = "build_docker";


    // 测试应用类型
    public static final String TASK_TYPE_TEST = "test";
    public static final String TASK_TEST_MAVENTEST = "maventest";
    public static final String TASK_TEST_TESTON = "teston";


    // 部署应用类型
    public static final String TASK_TYPE_DEPLOY = "deploy";
    public static final String TASK_DEPLOY_LINUX = "liunx";
    public static final String TASK_DEPLOY_DOCKER = "docker";


    // 推送制品应用类型
    public static final String TASK_TYPE_ARTIFACT = "artifact";

    public static final String TASK_ARTIFACT_MAVEN = "artifact_maven";

    public static final String TASK_ARTIFACT_NODEJS = "artifact_nodejs";

    public static final String TASK_ARTIFACT_DOCKER = "artifact_docker";


    public static final String TASK_TYPE_PULL = "pull";

    public static final String TASK_PULL_MAVEN = "pull_maven";

    public static final String TASK_PULL_NODEJS = "pull_nodejs";

    public static final String TASK_PULL_DOCKER = "pull_docker";


    public static final String TASK_ARTIFACT_XPACK = "hadess";

    public static final String TASK_ARTIFACT_SSH = "ssh";
    public static final String TASK_ARTIFACT_NEXUS = "nexus";

    // 代码扫描应用类型
    public static final String TASK_TYPE_CODESCAN = "codescan";
    public static final String TASK_CODESCAN_SONAR = "sonar";
    public static final String TASK_CODESCAN_SPOTBUGS = "spotbugs";

    // 消息应用类型
    public static final String TASK_TYPE_MESSAGE = "message";
    public static final String TASK_MESSAGE_MSG = "message";

    // 脚本应用类型
    public static final String TASK_TYPE_SCRIPT = "script";
    public static final String TASK_SCRIPT_SHELL = "shell";
    public static final String TASK_SCRIPT_BAT = "bat";


    //触发器
    public static final String TRIGGER_SCHEDULED = "scheduled";


    public static final String SIZE_TYPE_MB = "MB";

    public static final int DEFAULT_SIZE = 2;


    public static final String SIZE_TYPE_GB = "GB";


    public static final  Integer DEFAULT_CLEAN_CACHE_DAY = 7;











}
