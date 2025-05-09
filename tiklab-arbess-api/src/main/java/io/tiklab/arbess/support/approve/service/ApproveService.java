package io.tiklab.arbess.support.approve.service;


import io.tiklab.arbess.support.approve.model.Approve;
import io.tiklab.arbess.support.approve.model.ApprovePipeline;
import io.tiklab.arbess.support.approve.model.ApproveQuery;
import io.tiklab.arbess.support.approve.model.ApproveUser;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 审批接口
 */
@JoinProvider(model = Approve.class)
public interface ApproveService {
    
    /**
     * 创建审批
     * @param approve 审批信息
     * @return ID
     */
    String createApprove(Approve approve) ;

    /**
     * 更新审批
     * @param approve 审批信息
     */
    void updateApprove(Approve approve);


    /**
     * 更新审批
     * @param approve 审批信息
     */
    void update(Approve approve);


    /**
     * 撤销审批
     * @param id 审批ID
     */
    void revokeApprove(String id,String userId);


    /**
     * 删除审批
     * @param id 审批ID
     */
    void deleteApprove(String id);

    /**
     * 评审
     * @param approveUser 评审信息
     */
    void approve(ApproveUser approveUser);

    /**
     * 执行审批
     * @param approveId 审批ID
     */
    void execApprove(String approveId);

    /**
     * 更新流水线审批执行信息
     * @param approvePipeline 流水线审批信息
     */
    void updateApprovePipeline(ApprovePipeline approvePipeline);

    /**
     * 查询流水线审批
     * @param id 审批ID
     * @return 审批
     */
    @FindOne
    Approve findApprove(String id);

    /**
     * 条件查询环流水线审批
     * @param approveQuery 条件
     * @return 审批列表
     */
    @FindList
    List<Approve> findApproveList(ApproveQuery approveQuery) ;

    /**
     * 查询所有审批
     * @return 审批列表
     */
    @FindAll
    List<Approve> findAllApprove() ;

    /**
     * 分页条件查询环流水线审批
     * @param approveQuery 条件
     * @return 审批列表
     */
    Pagination<Approve> findApprovePage(ApproveQuery approveQuery);




}
