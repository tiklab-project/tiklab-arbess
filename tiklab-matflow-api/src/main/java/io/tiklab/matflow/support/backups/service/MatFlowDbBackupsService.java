package io.tiklab.matflow.support.backups.service;

import io.tiklab.matflow.support.backups.model.MatFlowBackups;

public interface MatFlowDbBackupsService {


    /**
     * 备份数据
     */
    void execBackups();


    /**
     * 查询备份信息
     * @return 备份信息
     */
    MatFlowBackups findBackupsResult();


    /**
     * 更新备份是否定时开启
     * @param state true：开启 false：关闭
     */
    void updateBackups(Boolean state);




}
