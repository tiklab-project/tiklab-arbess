package net.tiklab.matflow.orther.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.orther.model.PipelineThirdAddress;

import java.util.List;


@JoinProvider(model = PipelineThirdAddress.class)
public interface PipelineThirdAddressService {

    /**
     * 创建流水线授权
     * @param pipelineThirdAddress 流水线授权
     * @return 流水线授权id
     */
    String createAuthorize(PipelineThirdAddress pipelineThirdAddress);

    /**
     * 删除流水线授权
     * @param authorizeId 流水线授权id
     */
    void deleteAuthorize(String authorizeId);
    
    /**
     * 更新授权信息
     * @param pipelineThirdAddress 信息
     */
    void updateAuthorize(PipelineThirdAddress pipelineThirdAddress);
    
    /**
     * 查询授权信息
     * @param authorizeId id
     * @return 信息
     */
    @FindOne
    PipelineThirdAddress findOneAuthorize(String authorizeId);

    /**
     * 根据类型查询授权信息
     * @param type 类型
     * @return 授权
     */
    PipelineThirdAddress findOneAuthorize(int type);

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @FindAll
    List<PipelineThirdAddress> findAllAuthorize();


    @FindList
    List<PipelineThirdAddress> findAllAuthorizeList(List<String> idList);
    
}
