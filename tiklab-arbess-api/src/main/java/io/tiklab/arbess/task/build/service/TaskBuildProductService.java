package io.tiklab.arbess.task.build.service;

import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.build.model.TaskBuildProductQuery;

import java.util.List;

/**
 * 构建制品服务接口
 */
public interface TaskBuildProductService {

    /**
     * 创建构建制品
     * @param taskBuildProduct 构建制品
     * @return 构建制品ID
     */
    String createBuildProduct(TaskBuildProduct taskBuildProduct);

    /**
     * 更新构建制品
     * @param taskBuildProduct 构建制品
     */
    void updateBuildProduct(TaskBuildProduct taskBuildProduct);

    /**
     * 删除构建制品
     * @param id 构建制品ID
     */
    void deleteBuildProduct(String id);

    /**
     * 根据ID查询构建制品
     * @param id 构建制品ID
     * @return 构建制品
     */
    TaskBuildProduct findOneBuildProduct(String id);

    /**
     * 查询所有构建制品
     * @return 构建制品列表
     */
    List<TaskBuildProduct> findAllBuildProduct();

    /**
     * 查询构建制品列表
     * @param taskBuildProductQuery 构建制品查询
     * @return 构建制品列表
     */
    List<TaskBuildProduct> findBuildProductList(TaskBuildProductQuery taskBuildProductQuery);

    /**
     * 替换构建制品
     * @param instanceId 实例ID
     * @param strings 字符串
     * @return 替换后的字符串
     */
    String replace(String instanceId,String strings);





}
