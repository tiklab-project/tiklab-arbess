package net.tiklab.matflow.setting.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.setting.model.PipelineAuthThird;

import java.util.List;

@JoinProvider(model = PipelineAuthThird.class)
public interface PipelineAuthThirdServer {

    /**
     * 创建流水线第三方授权
     * @param pipelineAuthThird 流水线第三方授权
     * @return 流水线第三方授权id
     */
    String createAuthServer(PipelineAuthThird pipelineAuthThird);

    /**
     * 删除流水线第三方授权
     * @param authServerId 流水线第三方授权id
     */
    void deleteAuthServer(String authServerId);

    /**
     * 更新第三方授权信息
     * @param pipelineAuthThird 信息
     */
    void updateAuthServer(PipelineAuthThird pipelineAuthThird);

    /**
     * 查询第三方授权信息
     * @param authServerId id
     * @return 信息
     */
    @FindOne
    PipelineAuthThird findOneAuthServer(String authServerId);

    /**
     * 查询说有需要更新的授权信息
     * @param type 类型
     * @return 授权信息
     */
    List<PipelineAuthThird> findAllAuthServer(int type);

    /**
     * 获取不同授权类型的源码认证
     * @param type 类型 1. gitee 2. github 3.sonar 4.nexus
     * @return 认证信息
     */
    List<PipelineAuthThird> findAllAuthServerList(int type);

    /**
     * 查询所有流水线第三方授权
     * @return 流水线第三方授权列表
     */
    @FindAll
    List<PipelineAuthThird> findAllAuthServer();


    @FindList
    List<PipelineAuthThird> findAllAuthServerList(List<String> idList);
    
    
}
