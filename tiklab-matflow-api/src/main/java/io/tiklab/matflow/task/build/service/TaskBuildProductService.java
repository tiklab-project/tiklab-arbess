package io.tiklab.matflow.task.build.service;

import io.tiklab.matflow.task.build.model.TaskBuildProduct;

import java.util.List;

public interface TaskBuildProductService {

    String createBuildProduct(TaskBuildProduct taskBuildProduct);

    void updateBuildProduct(TaskBuildProduct taskBuildProduct);

    void deleteBuildProduct(String id);

    TaskBuildProduct findOneBuildProduct(String id);

    TaskBuildProduct findBuildProduct(String instanceId);


    List<TaskBuildProduct> findAllBuildProduct();





}
