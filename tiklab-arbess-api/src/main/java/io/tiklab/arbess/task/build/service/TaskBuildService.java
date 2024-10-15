package io.tiklab.arbess.task.build.service;


import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.task.build.model.TaskBuild;

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
     * 验证
     * @param taskType taskType
     * @param object object
     * @return Boolean
     */
    Boolean buildValid(String taskType,Object object);


    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    TaskBuild findBuildByAuth(String configId);


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
