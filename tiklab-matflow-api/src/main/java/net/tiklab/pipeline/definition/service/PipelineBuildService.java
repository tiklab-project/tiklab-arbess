package net.tiklab.pipeline.definition.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.pipeline.definition.model.PipelineBuild;


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
