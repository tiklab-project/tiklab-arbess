package net.tiklab.matflow.orther.until;

public class PipelineFinal {

    public static final String appName = "matflow";

    public static final int PIPELINE_RUN_SUCCESS=10;

    public static final int PIPELINE_RUN_ERROR=1;

    public static final int PIPELINE_RUN_HALT=20;

    //消息类型
    public static final String MES_PIPELINE_CREATE = "MES_PIPELINE_CREATE"; //流水线创建消息
    public static final String MES_PIPELINE_DELETE = "MES_PIPELINE_DELETE"; //流水线删除消息
    public static final String MES_PIPELINE_RUN = "MES_PIPELINE_RUN"; //流水线运行消息
    public static final String MES_PIPELINE_UPDATE = "MES_PIPELINE_UPDATE"; //流水线更新消息

    //消息发送方式
    public static final String MES_SENT_SITE = "site"; //流水线创建信息模板
    public static final String MES_SENT_EMAIL = "email"; //流水线创建信息模板
    public static final String MES_SENT_DINGDING = "dingding"; //流水线创建信息模板
    public static final String MES_SENT_WECHAT = "qywechat"; //流水线创建信息模板

    public static final String MES_PLAN = "MES_PLAN"; //消息方案


    //日志类型
    public static final String LOG_PIPELINE = "LOG_PIPELINE"; //流水线
    public static final String LOG_PIPELINE_CONFIG = "LOG_PIPELINE_CONFIG"; //配置
    public static final String LOG_PIPELINE_RUN = "LOG_PIPELINE_RUN"; //运行

    public static final String LOG_PIPELINE_USER = "LOG_PIPELINE_USER"; //成员邀请
    public static final String LOG_PIPELINE_AUTH = "LOG_PIPELINE_AUTH"; //权限变更

    //日志模板
    public static final String LOG_TEM_PIPELINE_CREATE = "LOG_TEM_PIPELINE_CREATE";
    public static final String LOG_TEM_PIPELINE_DELETE = "LOG_TEM_PIPELINE_DELETE";
    public static final String LOG_TEM_PIPELINE_UPDATE = "LOG_TEM_PIPELINE_UPDATE";
    public static final String LOG_TEM_PIPELINE_EXEC = "LOG_TEM_PIPELINE_EXEC";
    public static final String LOG_TEM_PIPELINE_RUN = "LOG_TEM_PIPELINE_RUN";
    public static final String LOG_TEM_PIPELINE_CONFIG_CREATE = "LOG_TEM_PIPELINE_CONFIG_CREATE";
    public static final String LOG_TEM_PIPELINE_CONFIG_DELETE = "LOG_TEM_PIPELINE_CONFIG_DELETE";
    public static final String LOG_TEM_PIPELINE_CONFIG_UPDATE = "LOG_TEM_PIPELINE_CONFIG_UPDATE";

    //日志module
    public static final String LOG_MD_PIPELINE_CREATE = "LOG_MD_PIPELINE_CREATE";
    public static final String LOG_MD_PIPELINE_DELETE = "LOG_MD_PIPELINE_DELETE";
    public static final String LOG_MD_PIPELINE_UPDATE = "LOG_MD_PIPELINE_UPDATE";
    public static final String LOG_MD_PIPELINE_RUN = "LOG_MD_PIPELINE_RUN";

}
