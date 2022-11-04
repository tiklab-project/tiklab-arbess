package net.tiklab.matflow.setting.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.setting.model.PipelineAuthCode;

import java.util.List;

@JoinProvider(model = PipelineAuthCode.class)
public interface PipelineAuthCodeServer {

    /**
     * 创建流水线第三方授权
     * @param pipelineAuthCode 流水线第三方授权
     * @return 流水线第三方授权id
     */
    String createAuthCode(PipelineAuthCode pipelineAuthCode);

    /**
     * 删除流水线第三方授权
     * @param authCodeId 流水线第三方授权id
     */
    void deleteAuthCode(String authCodeId);

    /**
     * 更新第三方授权信息
     * @param pipelineAuthCode 信息
     */
    void updateAuthCode(PipelineAuthCode pipelineAuthCode);

    /**
     * 查询第三方授权信息
     * @param authCodeId id
     * @return 信息
     */
    @FindOne
    PipelineAuthCode findOneAuthCode(String authCodeId);

    /**
     * 查询说有需要更新的授权信息
     * @param type 类型
     * @return 授权信息
     */
    List<PipelineAuthCode> findAllAuthCode(int type);

    /**
     * 获取不同授权类型的源码认证
     * @param type 类型 1.自定义 ，2. 授权获取
     * @return 认证信息
     */
    List<PipelineAuthCode> findAllAuthCodeList(int type);

    /**
     * 查询所有流水线第三方授权
     * @return 流水线第三方授权列表
     */
    @FindAll
    List<PipelineAuthCode> findAllAuthCode();


    @FindList
    List<PipelineAuthCode> findAllAuthCodeList(List<String> idList);
    
    
}
