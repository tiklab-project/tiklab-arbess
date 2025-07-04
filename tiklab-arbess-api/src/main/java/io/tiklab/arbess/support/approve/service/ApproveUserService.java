package io.tiklab.arbess.support.approve.service;

import io.tiklab.arbess.support.approve.model.ApproveUser;
import io.tiklab.arbess.support.approve.model.ApproveUserQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 审批用户接口
 */
@JoinProvider(model = ApproveUser.class)
public interface ApproveUserService {
    
    /**
     * 创建审批用户
     * @param approveUser 审批用户信息
     * @return ID
     */
    String createApproveUser(ApproveUser approveUser) ;

    /**
     * 更新审批用户
     * @param approveUser 审批用户信息
     */
    void updateApproveUser(ApproveUser approveUser);


    /**
     * 删除审批用户
     * @param id 审批用户ID
     */
    void deleteApproveUser(String id);

    /**
     * 查询流水线审批用户
     * @param id 审批用户ID
     * @return 审批用户
     */
    @FindOne
    ApproveUser findApproveUser(String id);

    /**
     * 查询流水线审批用户
     * @param approveId  用户id
     * @param pipelineId 流水线id
     * @return 审批用户
     */
    ApproveUser findApproveUser(String approveId, String pipelineId);

    /**
     * 条件查询环流水线审批用户
     * @param approveUserQuery 条件
     * @return 审批用户列表
     */
    @FindList
    List<ApproveUser> findApproveUserList(ApproveUserQuery approveUserQuery) ;

    /**
     * 查询所有审批用户
     * @return 审批用户列表
     */
    @FindAll
    List<ApproveUser> findAllApproveUser() ;

    /**
     * 分页条件查询环流水线审批用户
     * @param approveUserQuery 条件
     * @return 审批用户列表
     */
    Pagination<ApproveUser> findApproveUserPage(ApproveUserQuery approveUserQuery);




}
