package io.thoughtware.arbess.task.build.service;

import io.thoughtware.arbess.task.build.model.TaskBuildProduct;
import io.thoughtware.arbess.task.build.model.TaskBuildProductQuery;

import java.util.List;

public interface TaskBuildProductService {


    String createBuildProduct(TaskBuildProduct taskBuildProduct);


    void updateBuildProduct(TaskBuildProduct taskBuildProduct);


    void deleteBuildProduct(String id);


    TaskBuildProduct findOneBuildProduct(String id);


    List<TaskBuildProduct> findAllBuildProduct();

    List<TaskBuildProduct> findBuildProductList(TaskBuildProductQuery taskBuildProductQuery);


    String replace(String instanceId,String strings);





}
