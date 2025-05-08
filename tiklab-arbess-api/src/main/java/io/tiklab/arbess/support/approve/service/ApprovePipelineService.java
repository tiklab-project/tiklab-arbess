package io.tiklab.arbess.support.approve.service;

import io.tiklab.arbess.support.approve.model.ApprovePipeline;
import io.tiklab.arbess.support.approve.model.ApprovePipelineQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 审批流水线接口
 */
@JoinProvider(model = ApprovePipeline.class)
public interface ApprovePipelineService {
    
    /**
     * 创建审批流水线
     * @param approvePipeline 审批流水线信息
     * @return ID
     */
    String createApprovePipeline(ApprovePipeline approvePipeline) ;

    /**
     * 更新审批流水线
     * @param approvePipeline 审批流水线信息
     */
    void updateApprovePipeline(ApprovePipeline approvePipeline);

    /**
     * 更新流水线审批实例
     * @param approveId 审批ID
     */
    void updateApproveInstance(String approveId);

    /**
     * 删除审批流水线
     * @param id 审批流水线ID
     */
    void deleteApprovePipeline(String id);

    /**
     * 查询流水线审批流水线
     * @param id 审批流水线ID
     * @return 审批流水线
     */
    @FindOne
    ApprovePipeline findApprovePipeline(String id);

    /**
     * 查询流水线审批实例
     * @param instanceId 实例ID
     * @return 审批流水线
     */
    ApprovePipeline findApproveByInstance(String instanceId);

    /**
     * 条件查询环流水线审批流水线
     * @param approvePipelineQuery 条件
     * @return 审批流水线列表
     */
    @FindList
    List<ApprovePipeline> findApprovePipelineList(ApprovePipelineQuery approvePipelineQuery) ;

    /**
     * 查询所有审批流水线
     * @return 审批流水线列表
     */
    @FindAll
    List<ApprovePipeline> findAllApprovePipeline() ;

    /**
     * 分页条件查询环流水线审批流水线
     * @param approvePipelineQuery 条件
     * @return 审批流水线列表
     */
    Pagination<ApprovePipeline> findApprovePipelinePage(ApprovePipelineQuery approvePipelineQuery);




}
