package net.tiklab.matflow.setting.service;

import net.tiklab.matflow.setting.model.PipelineAuth;

import java.util.List;

public interface PipelineAuthServer {

    /**
     * 创建认证
     * @param pipelineAuth 认证内容
     * @return 认证id
     */
    String createAuth(PipelineAuth pipelineAuth);

    /**
     * 删除认证
     * @param type 类型
     * @param authId 认证is
     */
    void deleteAuth(int type,String authId);

    /**
     * 更新认证
     * @param pipelineAuth 更新内容
     */
    void updateAuth(PipelineAuth pipelineAuth);


    /**
     * 根据类型查询认证信息
     * @param type 类型
     * @param authId 认证id
     * @return 认证信息
     */
    Object findOneAuth(int type,String authId);

    /**
     * 根据不同配置类型查询凭证
     * @param type 类型
     * @return 凭证列表
     */
    List<Object> findAllConfigAuth(int type);

    /**
     * 查询认证
     * @param type 类型
     * @return 认证集合
     */
    List<Object> findAllAuth(int type);

}
