package net.tiklab.matflow.task.artifact.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.setting.model.AuthThird;
import net.tiklab.matflow.setting.service.AuthHostService;
import net.tiklab.matflow.setting.service.AuthThirdService;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.artifact.dao.TaskProductDao;
import net.tiklab.matflow.task.artifact.entity.TaskProductEntity;
import net.tiklab.matflow.task.artifact.model.TaskProduct;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class TaskProductServiceImpl implements TaskProductService {


    @Autowired
    TaskProductDao productDao;

    @Autowired
    AuthThirdService thirdServer;

    @Autowired
    AuthHostService hostServer;

    /**
     * 创建流水线推送制品
     * @param taskProduct 流水线推送制品
     * @return 流水线推送制品id
     */
    @Override
    public String createProduct(TaskProduct taskProduct) {
        TaskProductEntity taskProductEntity = BeanMapper.map(taskProduct, TaskProductEntity.class);
        return productDao.createProduct(taskProductEntity);
    }

    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteProductConfig(String configId){
        TaskProduct oneProductConfig = findOneProductConfig(configId);
        deleteProduct(oneProductConfig.getProductId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public TaskProduct findOneProductConfig(String configId){
        List<TaskProduct> allProduct = findAllProduct();
        if (allProduct == null){
            return null;
        }
        for (TaskProduct taskProduct : allProduct) {
            if (taskProduct.getConfigId().equals(configId)){
                return findOneProduct(taskProduct.getProductId());
            }
        }
        return null;
    }



    /**
     * 删除流水线推送制品
     * @param ProductId 流水线推送制品id
     */
    @Override
    public void deleteProduct(String ProductId) {
        productDao.deleteProduct(ProductId);
    }

    /**
     * 更新推送制品信息
     * @param taskProduct 信息
     */
    @Override
    public void updateProduct(TaskProduct taskProduct) {
        TaskProductEntity productEntity = BeanMapper.map(taskProduct, TaskProductEntity.class);
        productDao.updateProduct(productEntity);
    }

    /**
     * 查询推送制品信息
     * @param ProductId id
     * @return 信息集合
     */
    @Override
    public TaskProduct findOneProduct(String ProductId) {
        TaskProductEntity oneProduct = productDao.findOneProduct(ProductId);
        TaskProduct product = BeanMapper.map(oneProduct, TaskProduct.class);
        if (PipelineUtil.isNoNull(product.getAuthId())){
            Object auth = findAuth(product.getAuthId());
            product.setAuth(auth);
        }
        return product;
    }

    /**
     * 查询所有流水线推送制品
     * @return 流水线推送制品列表
     */
    @Override
    public List<TaskProduct> findAllProduct() {
        List<TaskProductEntity> allProduct = productDao.findAllProduct();
        return BeanMapper.mapList(allProduct, TaskProduct.class);
    }

    @Override
    public List<TaskProduct> findAllProductList(List<String> idList) {
        List<TaskProductEntity> allProductList = productDao.findAllProductList(idList);
        return BeanMapper.mapList(allProductList, TaskProduct.class);
    }

    private Object findAuth(String id){
        AuthThird oneAuthServer =thirdServer.findOneAuthServer(id);
        if (oneAuthServer != null){
            return oneAuthServer;
        }
        return hostServer.findOneAuthHost(id);
    }

}
