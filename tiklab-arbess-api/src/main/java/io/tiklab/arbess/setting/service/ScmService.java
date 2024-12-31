package io.tiklab.arbess.setting.service;

import io.tiklab.arbess.setting.model.Scm;

import java.util.List;
/**
 * 流水线环境配置服务接口
 */
public interface ScmService {

    /**
     * 创建环境配置
     * @param scm 环境配置模型
     * @return 环境配置id
     */
    String createPipelineScm(Scm scm);

    /**
     * 删除环境配置
     * @param scmId 配置id
     */
    void deletePipelineScm(String scmId);

    /**
     * 更新环境配置
     * @param scm 配置模型
     */
    void updatePipelineScm(Scm scm);

    /**
     * 查询单个环境配置
     * @param scmId 环境配置id
     * @return 环境配置模型
     */
    Scm findOnePipelineScm(String scmId);

    /**
     * 查询所有环境配置
     * @return 环境配置模型列表
     */
    List<Scm> findAllPipelineScm();

    /**
     * 根据获取配置
     * @param type 类型
     * @return 配置信息
     */
    Scm findOnePipelineScm(int type);

    /**
     * 根据ID列表批量查询环境配置
     * @param idList 环境配置ID列表
     * @return 环境配置列表
     */
    List<Scm> selectPipelineScmList(List<String> idList);

    /**
     * 查询环境配置数量
     * @return 环境配置数量
     */
    Integer findScmNumber();

}
