package io.tiklab.matflow.support.util;

public class PipelineFinal {


    public static final String MATFLOW_ROOT_PATH = "/.tiklab";

    public static final String MATFLOW_WORKSPACE = MATFLOW_ROOT_PATH + "/matflow/workspace";

    public static final String MATFLOW_LOGS = MATFLOW_ROOT_PATH+  "/matflow/logs";

    /**
     * 项目名称
     */
    public static final String appName = "matflow";

    //流水线运行状态
    public static final String RUN_SUCCESS = "success";

    public static final String RUN_ERROR = "error";

    public static final String RUN_WAIT = "wait";

    public static final String RUN_HALT = "halt";

    public static final String RUN_RUN = "run";

    //字节编码
    public static final String UTF_8 = "UTF-8";

    public static final String GBK = "GBK";


    //消息发送类型
    public static final String MES_PIPELINE_RUN = "MES_PIPELINE_RUN";
    public static final String MES_PIPELINE_TASK_RUN = "MES_PIPELINE_TASK_RUN";
    public static final String MES_PIPELINE_CREATE = "MES_PIPELINE_CREATE";
    public static final String MES_PIPELINE_DELETE = "MES_PIPELINE_DELETE";
    public static final String MES_PIPELINE_UPDATE = "MES_PIPELINE_UPDATE";


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



    //日志类型
    public static final String LOG_PIPELINE = "LOG_PIPELINE"; //流水线
    public static final String LOG_CONFIG = "LOG_CONFIG"; //配置
    public static final String LOG_RUN = "LOG_RUN"; //运行

    //日志模板
    public static final String LOG_TEM_CREATE = "LOG_TEM_CREATE";
    public static final String LOG_TEM_DELETE = "LOG_TEM_DELETE";
    public static final String LOG_TEM_UPDATE = "LOG_TEM_UPDATE";
    public static final String LOG_TEM_RUN = "LOG_TEM_RUN";


    public static final String LOG_TEM_CONFIG_CREATE = "LOG_TEM_PIPELINE_CONFIG_CREATE";
    public static final String LOG_TEM_CONFIG_DELETE = "LOG_TEM_PIPELINE_CONFIG_DELETE";
    public static final String LOG_TEM_CONFIG_UPDATE = "LOG_TEM_PIPELINE_CONFIG_UPDATE";










}
