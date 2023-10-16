package io.tiklab.matflow.task.build.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.beans.BeanMapper;
import io.tiklab.eam.common.model.EamTicket;
import io.tiklab.matflow.task.build.dao.TaskBuildProductDao;
import io.tiklab.matflow.task.build.entity.TaskBuildProductEntity;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.matflow.task.build.model.TaskBuildProductQuery;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public List<TaskBuildProduct> findAllBuildProduct() {
        List<TaskBuildProductEntity> allBuildProduct = taskBuildProductDao.findAllBuildProduct();
        if (allBuildProduct == null || allBuildProduct.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(allBuildProduct, TaskBuildProduct.class);
    }

    @Override
    public List<TaskBuildProduct> findBuildProductList(TaskBuildProductQuery taskBuildProductQuery) {
        List<TaskBuildProductEntity> allBuildProduct = taskBuildProductDao.findBuildProductList(taskBuildProductQuery);
        if (allBuildProduct == null || allBuildProduct.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(allBuildProduct, TaskBuildProduct.class);
    }


    @Override
    public String replace(String instanceId,String strings){
        TaskBuildProductQuery taskBuildProductQuery = new TaskBuildProductQuery();
        taskBuildProductQuery.setInstanceId(instanceId);
        List<TaskBuildProduct> buildProductList = findBuildProductList(taskBuildProductQuery);

        if (buildProductList.isEmpty()){
            return strings;
        }

        for (TaskBuildProduct taskBuildProduct : buildProductList) {

            LinkedHashMap<String,Object> linkedHashMap = JSONObject.parseObject(taskBuildProduct.getValue(), LinkedHashMap.class);
            for (Map.Entry<String, Object> entry : linkedHashMap.entrySet()) {
                String value = (String)entry.getValue();
                String key = entry.getKey();
                strings = strings.replaceAll("\\$\\{" + key  + "}",value);
                strings = strings.replaceAll(key,value);
            }
        }
        return strings;
    }


}
