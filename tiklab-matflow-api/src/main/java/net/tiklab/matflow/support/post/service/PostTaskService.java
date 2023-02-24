package net.tiklab.matflow.support.post.service;

import net.tiklab.matflow.support.post.model.Post;

public interface PostTaskService {


    /**
     * 配置信息
     * @param config 配置
     */
     void updateConfig(Post config);


    /**
     * 初始化任务名称
     * @param taskType 任务类型
     * @return 名称
     */
    String findConfigName(int taskType);

    /**
     * 查询配置信息
     * @param config 配置
     * @return 配置
     */
    Object findOneConfig(Post config);


    /**
     * 删除配置
     * @param config 配置
     */
    void deleteConfig(Post config);


}
