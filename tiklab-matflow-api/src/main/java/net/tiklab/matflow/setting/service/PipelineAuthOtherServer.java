package net.tiklab.matflow.setting.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.setting.model.PipelineAuthOther;

import java.util.List;
@JoinProvider(model = PipelineAuthOther.class)
public interface PipelineAuthOtherServer {

    /**
     * 创建流水线服务授权
     * @param pipelineAuthOther 流水线服务授权
     * @return 流水线服务授权id
     */
    String createAuthOther(PipelineAuthOther pipelineAuthOther);

    /**
     * 删除流水线服务授权
     * @param authOtherId 流水线服务授权id
     */
    void deleteAuthOther(String authOtherId);

    /**
     * 更新服务授权信息
     * @param pipelineAuthOther 信息
     */
    void updateAuthOther(PipelineAuthOther pipelineAuthOther);

    /**
     * 查询服务授权信息
     * @param authOtherId id
     * @return 信息
     */
    @FindOne
    PipelineAuthOther findOneAuthOther(String authOtherId);

    /**
     * 查询所有流水线服务授权
     * @return 流水线服务授权列表
     */
    @FindAll
    List<PipelineAuthOther> findAllAuthOther();


    @FindList
    List<PipelineAuthOther> findAllAuthOtherList(List<String> idList);
    
}
