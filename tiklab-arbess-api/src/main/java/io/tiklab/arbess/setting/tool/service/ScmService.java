package io.tiklab.arbess.setting.tool.service;

import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.arbess.setting.tool.model.ScmQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;
import java.util.Map;

/**
 * 流水线环境配置服务接口
 */
@JoinProvider(model = Scm.class)
public interface ScmService {

    /**
     * 创建环境配置
     * @param scm 环境配置模型
     * @return 环境配置id
     */
    String createPipelineScm(Scm scm);


    String createPipelineScmNoValid(Scm scm);

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
    @FindOne
    Scm findOnePipelineScm(String scmId);

    /**
     * 查询所有环境配置
     * @return 环境配置模型列表
     */
    @FindAll
    List<Scm> findAllPipelineScm();

    /**
     * 根据ID列表批量查询环境配置
     * @param idList 环境配置ID列表
     * @return 环境配置列表
     */
    @FindList
    List<Scm> findPipelineScmList(List<String> idList);


    List<Scm> findPipelineScmList(ScmQuery scmQuery);

    Pagination<Scm> findPipelineScmPage(ScmQuery scmQuery);

    /**
     * 查询环境配置数量
     * @return 环境配置数量
     */
    Integer findScmNumber();

    /**
     * 查询环境配置类型分组
     * @return 环境配置类型分组
     */
    List<Map<String, Object>> findScmByTypeGroup();

}
