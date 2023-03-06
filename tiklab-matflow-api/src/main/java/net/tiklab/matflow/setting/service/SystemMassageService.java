package net.tiklab.matflow.setting.service;

import net.tiklab.matflow.setting.model.SystemMassage;

/**
 * 流水线系统信息服务接口
 */
public interface SystemMassageService {

    /**
     * 系统信息
     * @return 系统信息
     */
    SystemMassage getSystemMassage();

}
