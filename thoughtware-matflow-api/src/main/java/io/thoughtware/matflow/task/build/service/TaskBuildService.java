package io.thoughtware.matflow.task.build.service;


import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.matflow.task.build.model.TaskBuild;

import java.util.List;
/**
 * 任务构建服务接口
 */
@JoinProvider(model = TaskBuild.class)
public interface TaskBuildService {

    /**
     * 创建
     * @param TaskBuild build信息
     * @return buildId
     */
    String createBuild(TaskBuild TaskBuild);

    /**
     * 删除
     * @param buildId buildId
     */
    void deleteBuild(String buildId);

    /**
     * 更新
     * @param TaskBuild 更新信息
     */
    void updateBuild(TaskBuild TaskBuild);


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
    TaskBuild findOneBuildConfig(String configId);


    /**
     * 查询单个信息
     * @param buildId buildId
     * @return build信息
     */
    @FindOne
    TaskBuild findOneBuild(String buildId);

    /**
     * 查询所有信息
     * @return build信息集合
     */
    @FindAll
    List<TaskBuild> findAllBuild();

    @FindList
    List<TaskBuild> findAllBuildList(List<String> idList);
}
