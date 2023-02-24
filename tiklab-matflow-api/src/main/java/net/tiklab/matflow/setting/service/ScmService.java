package net.tiklab.matflow.setting.service;

import net.tiklab.matflow.setting.model.Scm;

import java.util.List;

public interface ScmService {


    String createPipelineScm(Scm scm);

    //删除
    void deletePipelineScm(String pathId);

    //更新
    void updatePipelineScm(Scm scm);

    //查询
    Scm findOnePipelineScm(String pathId);

    //查询所有
    List<Scm> findAllPipelineScm();

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    Scm findOnePipelineScm(int type);

    List<Scm> selectPipelineScmList(List<String> idList);

}
