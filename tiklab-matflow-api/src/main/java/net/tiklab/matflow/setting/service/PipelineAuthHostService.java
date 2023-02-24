package net.tiklab.matflow.setting.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.setting.model.PipelineAuthHost;
import java.util.List;

@JoinProvider(model = PipelineAuthHost.class)
public interface PipelineAuthHostService {

    /**
     * 创建流水线主机授权
     * @param pipelineAuthHost 流水线主机授权
     * @return 流水线主机授权id
     */
    String createAuthHost(PipelineAuthHost pipelineAuthHost);

    /**
     * 删除流水线主机授权
     * @param authHostId 流水线主机授权id
     */
    void deleteAuthHost(String authHostId);

    /**
     * 更新主机授权信息
     * @param pipelineAuthHost 信息
     */
    @FindOne
    void updateAuthHost(PipelineAuthHost pipelineAuthHost);

    /**
     * 查询主机授权信息
     * @param authHostId id
     * @return 信息
     */
    PipelineAuthHost findOneAuthHost(String authHostId);

    /**
     * 查询单个类型所有配置
     * @param type 类型
     * @return 配置
     */
    List<PipelineAuthHost> findAllAuthHostList(int type);

    /**
     * 查询所有流水线主机授权
     * @return 流水线主机授权列表
     */
    @FindAll
    List<PipelineAuthHost> findAllAuthHost();


    @FindList
    List<PipelineAuthHost> findAllAuthHostList(List<String> idList);
    
}
