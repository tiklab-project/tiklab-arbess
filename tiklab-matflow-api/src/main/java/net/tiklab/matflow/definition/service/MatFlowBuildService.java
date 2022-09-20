package net.tiklab.matflow.definition.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.MatFlowBuild;

import java.util.List;

@JoinProvider(model = MatFlowBuild.class)
public interface MatFlowBuildService {

    /**
     * 创建
     * @param MatFlowBuild build信息
     * @return buildId
     */
    String createBuild(MatFlowBuild MatFlowBuild);

    /**
     * 删除
     * @param buildId buildId
     */
    void deleteBuild(String buildId);

    /**
     * 更新
     * @param MatFlowBuild 更新信息
     */
    void updateBuild(MatFlowBuild MatFlowBuild);

    /**
     * 查询单个信息
     * @param buildId buildId
     * @return build信息
     */
    @FindOne
    MatFlowBuild findOneBuild(String buildId);

    /**
     * 查询所有信息
     * @return build信息集合
     */
    @FindAll
    List<MatFlowBuild> findAllBuild();

    @FindList
    List<MatFlowBuild> findAllBuildList(List<String> idList);
}
