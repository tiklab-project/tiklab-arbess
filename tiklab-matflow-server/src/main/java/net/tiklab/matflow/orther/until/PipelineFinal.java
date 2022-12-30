package net.tiklab.matflow.orther.until;

public class PipelineFinal {

    public static final String appName = "matflow";

    public static final int PIPELINE_RUN_SUCCESS=10;

    public static final int PIPELINE_RUN_ERROR=1;

    public static final int PIPELINE_RUN_HALT=20;

    //消息类型
    public static final String MES_PIPELINE_RUN = "MES_PIPELINE_RUN"; //流水线运行消息

    public static final String MES_TEM_SITE = "site"; //流水线创建信息模板


    //消息发送方式
    public static final String MES_SENT_SITE = "site"; //流水线创建信息模板


    public static final String MES_SENT_EMAIL = "email"; //流水线创建信息模板
    public static final String MES_SENT_DINGDING = "dingding"; //流水线创建信息模板
    public static final String MES_SENT_WECHAT = "qywechat"; //流水线创建信息模板

    //消息方案
    public static final String MES_UPDATE = "MES_UPDATE"; //消息方案
    public static final String MES_DELETE = "MES_DELETE"; //消息方案
    public static final String MES_CREATE = "MES_CREATE"; //消息方案


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
