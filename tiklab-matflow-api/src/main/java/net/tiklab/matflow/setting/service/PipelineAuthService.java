package net.tiklab.matflow.setting.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.setting.model.PipelineAuth;

import java.util.List;

@JoinProvider(model = PipelineAuth.class)
public interface PipelineAuthService {

    /**
     * 创建流水线基本授权
     * @param pipelineAuth 流水线基本授权
     * @return 流水线基本授权id
     */
    String createAuth(PipelineAuth pipelineAuth);

    /**
     * 删除流水线基本授权
     * @param authId 流水线基本授权id
     */
    void deleteAuth(String authId);

    /**
     * 更新基本授权信息
     * @param pipelineAuth 信息
     */
    void updateAuth(PipelineAuth pipelineAuth);

    /**
     * 查询基本授权信息
     * @param authId id
     * @return 信息
     */
    @FindOne
    PipelineAuth findOneAuth(String authId);


    /**
     * 查询所有流水线基本授权
     * @return 流水线基本授权列表
     */
    @FindAll
    List<PipelineAuth> findAllAuth();


    @FindList
    List<PipelineAuth> findAllAuthList(List<String> idList);
    
}
