package net.tiklab.matflow.setting.service;

import net.tiklab.matflow.setting.model.SystemMassage;

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
