package io.tiklab.matflow.support.authority.service;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.privilege.role.model.PatchUser;
import io.tiklab.user.user.model.User;

import java.util.List;
/**
 * 流水线项目权限服务接口
 */
public interface PipelineAuthorityService {


    /**
     * 获取用户拥有的流水线
     * @param userId 用户id
     * @return 流水线支付串集合
     */
    String[] findUserPipelineIdString(String userId);

    /**
     * 获取拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
    List<User> findPipelineUser(String pipelineId);

    /**
     * 获取用户拥有的流水线
     * @param userId 用户id
     * @return 流水线
     */
    List<Pipeline> findUserPipeline(String userId);

    /**
     * 更新项目域权限
     * @param pipelineId 流水线id
     */
    void deleteDmUser(String pipelineId);

    /**
     * 创建流水线关联用户
     * @param pipelineId 流水线id
     */
    void createDmUser(String pipelineId, List<PatchUser> userList);


    void cloneDomainRole(String sourceDomainId,String cloneDomainId);

}
