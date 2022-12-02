package net.tiklab.matflow.definition.service.task;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.task.PipelineBuild;

import java.util.List;

@JoinProvider(model = PipelineBuild.class)
public interface PipelineBuildService {

    /**
     * 创建
     * @param PipelineBuild build信息
     * @return buildId
     */
    String createBuild(PipelineBuild PipelineBuild);

    /**
     * 删除
     * @param buildId buildId
     */
    void deleteBuild(String buildId);

    /**
     * 更新
     * @param PipelineBuild 更新信息
     */
    void updateBuild(PipelineBuild PipelineBuild);


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    void deleteBuildConfig(String configId);

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    PipelineBuild findOneBuildConfig(String configId);


    /**
     * 查询单个信息
     * @param buildId buildId
     * @return build信息
     */
    @FindOne
    PipelineBuild findOneBuild(String buildId);

    /**
     * 查询所有信息
     * @return build信息集合
     */
    @FindAll
    List<PipelineBuild> findAllBuild();

    @FindList
    List<PipelineBuild> findAllBuildList(List<String> idList);
}
