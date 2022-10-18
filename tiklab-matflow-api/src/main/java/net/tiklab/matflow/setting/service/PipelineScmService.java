package net.tiklab.matflow.setting.service;

import net.tiklab.matflow.setting.model.PipelineScm;

import java.util.List;

public interface PipelineScmService {


    String createPipelineScm(PipelineScm pipelineScm);

    //删除
    void deletePipelineScm(String pathId);

    //更新
    void updatePipelineScm(PipelineScm pipelineScm);

    //查询
    PipelineScm findOnePipelineScm(String pathId);

    //查询所有
    List<PipelineScm> findAllPipelineScm();

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    PipelineScm findOnePipelineScm(int type);

    List<PipelineScm> selectPipelineScmList(List<String> idList);

}
