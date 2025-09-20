package io.tiklab.arbess.task.build.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.task.build.dao.TaskBuildProductDao;
import io.tiklab.arbess.task.build.entity.TaskBuildProductEntity;
import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.build.model.TaskBuildProductQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@Exporter
public class TaskBuildProductServiceImpl implements TaskBuildProductService {

    @Autowired
    TaskBuildProductDao taskBuildProductDao;

    @Value("${DATA_HOME}")
    private String dataHome;

    @Value("${external.url}")
    private String externalUrl;

    @Override
    public String createBuildProduct(TaskBuildProduct taskBuildProduct) {
        TaskBuildProductEntity taskBuildProductEntity = BeanMapper.map(taskBuildProduct, TaskBuildProductEntity.class);
        taskBuildProductEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
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
            return new ArrayList<>();
        }
        return BeanMapper.mapList(allBuildProduct, TaskBuildProduct.class);
    }

    @Override
    public List<TaskBuildProduct> findBuildProductList(TaskBuildProductQuery taskBuildProductQuery) {
        List<TaskBuildProductEntity> allBuildProduct = taskBuildProductDao.findBuildProductList(taskBuildProductQuery);
        if (allBuildProduct == null || allBuildProduct.isEmpty()){
            return new ArrayList<>();
        }
        List<TaskBuildProduct> taskBuildProductList = BeanMapper.mapList(allBuildProduct, TaskBuildProduct.class);
        for (TaskBuildProduct product : taskBuildProductList) {
            String value = product.getValue();
            if (!StringUtils.isEmpty(value)){
                value = value.replace("${DATA_HOME}", dataHome);
            }
            product.setValue(value);

            String downloadUrl =externalUrl+"/instance/artifact/download/"+product.getInstanceId();
            product.setDownloadUrl(downloadUrl);
        }

        return taskBuildProductList;
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
