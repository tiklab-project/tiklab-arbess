package net.tiklab.matflow.orther.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.matflow.orther.model.PipelineAuthorize;

import java.util.List;

public interface PipelineAuthorizeService {

    /**
     * 创建流水线授权
     * @param pipelineAuthorize 流水线授权
     * @return 流水线授权id
     */
    String createAuthorize(PipelineAuthorize pipelineAuthorize);

    /**
     * 删除流水线授权
     * @param authorizeId 流水线授权id
     */
    void deleteAuthorize(String authorizeId);
    
    /**
     * 更新授权信息
     * @param pipelineAuthorize 信息
     */
    void updateAuthorize(PipelineAuthorize pipelineAuthorize);
    
    /**
     * 查询授权信息
     * @param authorizeId id
     * @return 信息
     */
    PipelineAuthorize findOneAuthorize(String authorizeId);

    /**
     * 根据类型查询授权信息
     * @param type 类型
     * @return 授权
     */
    PipelineAuthorize findOneAuthorize(int type);

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @FindAll
    List<PipelineAuthorize> findAllAuthorize();


    @FindList
    List<PipelineAuthorize> findAllAuthorizeList(List<String> idList);
    
}
