package net.tiklab.pipeline.orther.service;

public interface PipelineActivityService {

    ///**
    // * 创建
    // * @param pipelineActivity 动态信息
    // */
    //void createActive(PipelineActivity pipelineActivity);
    //
    ///**
    // * 删除动态
    // * @param activeId 动态id
    // */
    //void deleteActivity(String activeId);
    //
    ///**
    // * 删除一条流水线的所有动态
    // * @param pipelineId 流水线id
    // */
    //void deletePipelineActivity(String pipelineId);
    //
    ///**
    // * 查询所有动态
    // * @return 动态列表
    // */
    //List<PipelineActivity> findAllActive();
    //
    //
    ///**
    // * 对外提供创建动态
    // * @param userId 用户偶读
    // * @param massage 动态信息
    // */
    //void createActive(String userId, Pipeline pipeline, String massage);
    //
    ///**
    // * 查询所有动态
    // * @return 动态列表
    // */
    //List<PipelineActivity> findUserActivity(PipelineActivityQuery pipelineActivityQuery);


    /**
     * 创建日志
     * @param type 日志类型 (创建 create，删除 delete，执行 exec，更新 update)
     * @param templateId 模板id (创建 流水线--pipeline，运行 pipelineExec，凭证--pipelineProof,其他--pipelineOther)
     * @param massage 日志信息
     */
    void log(String type, String templateId,String massage);


}
