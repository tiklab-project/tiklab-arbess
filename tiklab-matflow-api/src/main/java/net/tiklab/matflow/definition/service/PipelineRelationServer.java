package net.tiklab.matflow.definition.service;

import net.tiklab.core.page.Pagination;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineExecMessage;
import net.tiklab.matflow.execute.model.PipelineAllHistoryQuery;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineOverview;
import net.tiklab.matflow.orther.model.PipelineFollow;
import net.tiklab.matflow.orther.model.PipelineOpen;
import net.tiklab.privilege.role.model.PatchUser;
import net.tiklab.user.user.model.User;

import java.util.List;

/**
 * 流水线关联操作 （用户，日志，收藏等）
 */

public interface PipelineRelationServer {

    /**
     * 删除关联信息
     * @param pipeline 流水线
     */
    void deleteHistory(Pipeline pipeline);

    /**
     * 更新收藏信息
     * @param pipelineFollow 信息
     */
    void updateFollow(PipelineFollow pipelineFollow);

    /**
     * 查询流水线最近构建信息
     * @param allPipeline 流水线
     * @return 构建信息
     */
    List<PipelineExecMessage> findAllExecMessage(List<Pipeline> allPipeline);

    /**
     * 获取用户名下的流水线
     * @param userId 用户id
     * @return 所有流水线id
     */
    StringBuilder findUserPipelineId(String userId, List<Pipeline> userPipeline);

    /**
     * 获取拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
    List<User>  findPipelineUser(String pipelineId);

    /**
     * 创建流水线关联角色信息
     * @param pipelineId 流水线id
     */
    void createDmUser(String pipelineId,List<PatchUser> userList);

    /**
     * 删除关联用户
     * @param pipelineId 流水线id
     */
    void deleteDmUser(String pipelineId);

    /**
     * 流水线执行信息统计
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    PipelineOverview pipelineCensus(String pipelineId);

    /**
     * 最近打开
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    PipelineOverview census(String pipelineId);

    /**
     * 查询最近打开的流水线
     * @param s 流水线id信息
     * @param number 数量
     * @return 最近打开的流水线列表
     */
    List<PipelineOpen> findAllOpen(StringBuilder s,int number);


    /**
     * 查询用户所有历史
     * @param pipelineHistoryQuery 流水线
     * @return 历史
     */
    Pagination<PipelineExecHistory> findUserAllHistory(PipelineAllHistoryQuery pipelineHistoryQuery);





}





























