package io.tiklab.matflow.task.build.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.task.build.dao.TaskBuildProductDao;
import io.tiklab.matflow.task.build.entity.TaskBuildProductEntity;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Exporter
public class TaskBuildProductServiceImpl implements TaskBuildProductService {

    @Autowired
    TaskBuildProductDao taskBuildProductDao;


    @Override
    public String createBuildProduct(TaskBuildProduct taskBuildProduct) {
        TaskBuildProductEntity taskBuildProductEntity = BeanMapper.map(taskBuildProduct, TaskBuildProductEntity.class);
        return taskBuildProductDao.createBuildProduct(taskBuildProductEntity);
    }

    @Override
    public void updateBuildProduct(TaskBuildProduct taskBuildProduct) {
        TaskBuildProductEntity taskBuildProductEntity = BeanMapper.map(taskBuildProduct, TaskBuildProductEntity.class);
        taskBuildProductDao.updateBuildProduct(taskBuildProductEntity);
    }

    @Override
    public void deleteBuildProduct(String id) {
        taskBuildProductDao.deleteBuildProduct(id);
    }

    @Override
    public TaskBuildProduct findOneBuildProduct(String id) {
        TaskBuildProductEntity buildProductEntity = taskBuildProductDao.findOneBuildProduct(id);
        return BeanMapper.map(buildProductEntity, TaskBuildProduct.class);
    }

    @Override
    public TaskBuildProduct findBuildProduct(String instanceId) {
        List<TaskBuildProductEntity> allBuildProduct = taskBuildProductDao.findBuildProductList(instanceId);
        if (allBuildProduct == null || allBuildProduct.size() == 0){
            return null;
        }
        List<TaskBuildProduct> taskBuildProducts = BeanMapper.mapList(allBuildProduct, TaskBuildProduct.class);
        return taskBuildProducts.get(0);
    }

    @Override
    public List<TaskBuildProduct> findAllBuildProduct() {
        List<TaskBuildProductEntity> allBuildProduct = taskBuildProductDao.findAllBuildProduct();
        if (allBuildProduct == null || allBuildProduct.size() == 0){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(allBuildProduct, TaskBuildProduct.class);
    }
}
