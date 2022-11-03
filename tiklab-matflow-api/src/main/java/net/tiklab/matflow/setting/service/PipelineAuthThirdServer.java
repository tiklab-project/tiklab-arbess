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
    String createAuthThird(PipelineAuthThird pipelineAuthThird);

    /**
     * 删除流水线第三方授权
     * @param authThirdId 流水线第三方授权id
     */
    void deleteAuthThird(String authThirdId);

    /**
     * 更新第三方授权信息
     * @param pipelineAuthThird 信息
     */
    void updateAuthThird(PipelineAuthThird pipelineAuthThird);

    /**
     * 查询第三方授权信息
     * @param authThirdId id
     * @return 信息
     */
    @FindOne
    PipelineAuthThird findOneAuthThird(String authThirdId);


    /**
     * 查询需要更新的授权信息
     * @param type 授权类型
     * @return 授权信息
     */
    List<PipelineAuthThird> findAllAuthThird(int type);


    /**
     * 查询所有流水线第三方授权
     * @return 流水线第三方授权列表
     */
    @FindAll
    List<PipelineAuthThird> findAllAuthThird();


    @FindList
    List<PipelineAuthThird> findAllAuthThirdList(List<String> idList);
    
    
}
