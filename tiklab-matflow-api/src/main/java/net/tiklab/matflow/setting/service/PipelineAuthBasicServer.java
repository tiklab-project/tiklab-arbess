package net.tiklab.matflow.setting.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.setting.model.PipelineAuthBasic;

import java.util.List;

@JoinProvider(model = PipelineAuthBasic.class)
public interface PipelineAuthBasicServer {

    /**
     * 创建流水线基本授权
     * @param pipelineAuthBasic 流水线基本授权
     * @return 流水线基本授权id
     */
    String createAuthBasic(PipelineAuthBasic pipelineAuthBasic);

    /**
     * 删除流水线基本授权
     * @param authBasicId 流水线基本授权id
     */
    void deleteAuthBasic(String authBasicId);

    /**
     * 更新基本授权信息
     * @param pipelineAuthBasic 信息
     */
    void updateAuthBasic(PipelineAuthBasic pipelineAuthBasic);

    /**
     * 查询基本授权信息
     * @param authBasicId id
     * @return 信息
     */
    @FindOne
    PipelineAuthBasic findOneAuthBasic(String authBasicId);


    /**
     * 查询所有流水线基本授权
     * @return 流水线基本授权列表
     */
    @FindAll
    List<PipelineAuthBasic> findAllAuthBasic();


    @FindList
    List<PipelineAuthBasic> findAllAuthBasicList(List<String> idList);
    
}
