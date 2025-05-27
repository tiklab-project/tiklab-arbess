package io.tiklab.arbess.setting.other.service;

import io.tiklab.arbess.setting.other.model.SystemMassage;

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
