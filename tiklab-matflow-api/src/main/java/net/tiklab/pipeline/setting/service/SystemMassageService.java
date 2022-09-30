package net.tiklab.pipeline.setting.service;

import net.tiklab.pipeline.setting.model.SystemMassage;

public interface SystemMassageService {

    /**
     * 系统信息
     * @return 系统信息
     */
    SystemMassage getSystemMassage();

    ///**
    // * 读取日志信息
    // * @return 系统日志
    // */
    //List<String> getSystemLog();

}
