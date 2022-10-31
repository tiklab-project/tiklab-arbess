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
     * 创建流水线认证
     * @param pipelineAuth 流水线认证
     * @return 流水线认证id
     */
    String createAuth(PipelineAuth pipelineAuth);

    /**
     * 删除流水线认证
     * @param AuthId 流水线认证id
     */
    void deleteAuth(String AuthId);

    /**
     * 更新认证信息
     * @param pipelineAuth 信息
     */
    void updateAuth(PipelineAuth pipelineAuth);

    /**
     * 查询认证信息
     * @param authId id
     * @return 信息
     */
    @FindOne
    PipelineAuth findOneAuth(String authId);

    /**
     * 根据类型查询认证信息
     * @param type 类型
     * @return 认证
     */
    PipelineAuth findOneAuth(int type);

    /**
     * 查询所有流水线认证
     * @return 流水线认证列表
     */
    @FindAll
    List<PipelineAuth> findAllAuth();


    @FindList
    List<PipelineAuth> findAllAuthList(List<String> idList);



}
