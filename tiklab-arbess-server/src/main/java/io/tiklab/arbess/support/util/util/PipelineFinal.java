package io.tiklab.arbess.support.util.util;

public class PipelineFinal {

    /**
     * 项目名称
     */
    public static final String appName = "arbess";

    /**
     * DEFAULT
      */
    public static final String DEFAULT = "default";

    // 流水线运行权限 Key
    public static final String PIPELINE_RUN_KEY = "pipeline_task_run";

    /**
     * 流水线文件系统
     */
    public static final String MATFLOW_WORKSPACE = "/source";

    public static final String MATFLOW_LOGS = "/artifact";

    public static final String MATFLOW_INSTABCE = "/instance";

    public static final String ARBESS_SCM = "/attachment";

    public static final String ARBESS_OTHER = "/other";


    /**
     * 流水线运行状态
     */
    public static final String RUN_SUCCESS = "success";

    public static final String RUN_ERROR = "error";

    public static final String RUN_WAIT = "wait";

    public static final String RUN_HALT = "halt";

    public static final String RUN_RUN = "run";

    public static final String RUN_SUSPEND = "suspend";

    public static final String RUN_TIMEOUT = "timeout";

    /**
     * 审批状态
     */

    public static final String APPROVE = "approve";

    public static final String APPROVE_CANCEL = "cancel";

    public static final String APPROVE_WAIT = "wait";

    public static final String APPROVE_PENDING = "pending";

    public static final String APPROVE_PASS = "pass";

    public static final String APPROVE_REJECT = "reject";

    public static final String APPROVE_WAIT_RUN = "wail-run";

    public static final String APPROVE_RUN = "run";

    public static final String APPROVE_COMPLETE = "complete";

    public static final String APPROVE_ERROR = "error";

    public static final String APPROVE_DRAFT = "draft";


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

    //消息发送方式
    public static final String MES_SEND_SITE = "site";
    public static final String MES_SEND_EMAIL = "email";
    public static final String MES_SEND_DINGDING = "dingding";
    public static final String MES_SEND_WECHAT = "qywechat";
    public static final String MES_SEND_SMS = "sms";

    //消息通知方案
    public static final String MES_UPDATE = "MF_MES_TYPE_UPDATE";
    public static final String MES_DELETE = "MF_MES_TYPE_DELETE";
    public static final String MES_CREATE = "MF_MES_TYPE_CREATE";
    public static final String MES_RUN = "MF_MES_TYPE_RUN";


    // 日志类型
    public static final String LOG_TYPE_CREATE = "MF_LOG_TYPE_CREATE";

    public static final String LOG_TYPE_DELETE = "MF_LOG_TYPE_DELETE";

    public static final String LOG_TYPE_UPDATE = "MF_LOG_TYPE_UPDATE";

    public static final String LOG_TYPE_RUN = "MF_LOG_TYPE_RUN";


    public static final String CREATE_LINK = "/pipeline/${pipelineId}/config";

    public static final String DELETE_LINK = "/pipeline/${pipelineId}/delete";

    public static final String UPDATE_LINK = "/pipeline/${pipelineId}/set/info";

    public static final String RUN_LINK = "/pipeline/${pipelineId}/history/${instanceId}";


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
     * 默认命令
     */
    public static final String TEST_DEFAULT_ORDER = "mvn test";
    public static final String MAVEN_DEFAULT_ORDER = "mvn clean package";
    public static final String GO_DEFAULT_ORDER = "go build -o bin/myapp";
    public static final String PYTHON_DEFAULT_ORDER = "python install .";
    public static final String PHP_DEFAULT_ORDER = "composer install";
    public static final String NET_CORE_DEFAULT_ORDER = "dotnet publish -c Release -o out";
    public static final String NODE_DEFAULT_ORDER = "npm install";
    public static final String DOCKER_DEFAULT_ORDER = "docker image build -t default .";

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
    public static final String TASK_TOOL_TYPE_JDK = "jdk";
    public static final String TASK_TOOL_TYPE_MAVEN = "maven";
    public static final String TASK_TOOL_TYPE_GIT = "git";
    public static final String TASK_TOOL_TYPE_SVN = "svn";
    public static final String TASK_TOOL_TYPE_NODEJS = "nodejs";
    public static final String TASK_TOOL_TYPE_NPM = "npm";


    // 源码应用类型
    public static final String TASK_TYPE_CODE = "code";
    public static final String TASK_CODE_GIT = "git";
    public static final String TASK_CODE_GITLAB = "gitlab";
    public static final String TASK_CODE_PRI_GITLAB = "pri_gitlab";
    public static final String TASK_CODE_GITHUB = "github";
    public static final String TASK_CODE_GITEE = "gitee";
    public static final String TASK_CODE_SVN = "svn";
    public static final String TASK_CODE_XCODE = "gitpuk";
    public static final String TASK_CODE_DEFAULT_BRANCH = "master";


    // 构建应用类型
    public static final String TASK_TYPE_BUILD = "build";
    public static final String TASK_BUILD_MAVEN = "maven";
    public static final String TASK_BUILD_PYTHON = "build_python";
    public static final String TASK_BUILD_NET_CORE = "build_net_core";
    public static final String TASK_BUILD_PHP = "build_php";
    public static final String TASK_BUILD_NODEJS = "nodejs";

    public static final String TASK_BUILD_DOCKER = "build_docker";
    public static final String TASK_BUILD_GO = "build_go";


    // 测试应用类型
    public static final String TASK_TYPE_TEST = "test";
    public static final String TASK_TEST_MAVENTEST = "maventest";
    public static final String TASK_TEST_TESTON = "testhubo";


    // 部署应用类型
    public static final String TASK_TYPE_DEPLOY = "deploy";
    public static final String TASK_DEPLOY_LINUX = "liunx";
    public static final String TASK_DEPLOY_DOCKER = "docker";
    public static final String TASK_DEPLOY_K8S = "k8s";
    public static final String TASK_DEPLOY_K8S_TYPE_FILE = "file";
    public static final String TASK_DEPLOY_K8S_TYPE_TXT = "txt";
    public static final String TASK_DEPLOY_STRATEGY_ONE = "one";
    public static final String TASK_DEPLOY_STRATEGY_ALL = "all";


    // 上传类型
    public static final String TASK_TYPE_UPLOAD = "upload";
    public static final String TASK_UPLOAD_HADESS = "upload_hadess";
    public static final String TASK_UPLOAD_SSH = "upload_ssh";
    public static final String TASK_UPLOAD_NEXUS = "upload_nexus";
    public static final String TASK_UPLOAD_DOCKER = "upload_docker";


    // 下载类型
    public static final String TASK_TYPE_DOWNLOAD = "download";
    public static final String TASK_DOWNLOAD_HADESS = "download_hadess";
    public static final String TASK_DOWNLOAD_SSH = "download_ssh";
    public static final String TASK_DOWNLOAD_NEXUS = "download_nexus";
    public static final String TASK_DOWNLOAD_DOCKER = "download_docker";


    public static final String TASK_CODESCAN_SONAR_JAVA = "java";
    public static final String TASK_CODESCAN_SONAR_OTHER = "other";
    public static final String TASK_CODESCAN_SONAR_JAVASCRIPT = "javascript";
    public static final String TASK_CODESCAN_SONAR_GO = "go";


    // 代码扫描应用类型
    public static final String TASK_TYPE_CODESCAN = "codescan";
    public static final String TASK_CODESCAN_SONAR = "sonar";
    public static final String TASK_CODESCAN_SPOTBUGS = "spotbugs";
    public static final String TASK_CODESCAN_SOURCEFARE = "sourcefare";

    // 消息应用类型
    public static final String TASK_TYPE_MESSAGE = "message";
    public static final String TASK_MESSAGE_MSG = "message";

    // 脚本应用类型
    public static final String TASK_TYPE_SCRIPT = "script";
    public static final String TASK_SCRIPT_SHELL = "shell";
    public static final String TASK_SCRIPT_SH = "sh";
    public static final String TASK_SCRIPT_BASH = "bash";
    public static final String TASK_SCRIPT_CMD = "cmd";
    public static final String TASK_SCRIPT_BAT = "bat";

    // 人工卡点
    public static final String TASK_TYPE_TOOL = "tool";
    public static final String TASK_CHECK_POINT = "checkpoint";


    //触发器
    public static final String TRIGGER_SCHEDULED = "scheduled";


    public static final String SIZE_TYPE_MB = "MB";

    public static final int DEFAULT_SIZE = 2;


    public static final String SIZE_TYPE_GB = "GB";


    public static final  Integer DEFAULT_CLEAN_CACHE_DAY = 7;


    public static final String AUTH_NONE = "none";
    public static final String AUTH_USER_PASS = "userPass";
    public static final String AUTH_PRI_KEY = "prikey";








}
