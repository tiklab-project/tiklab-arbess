package net.tiklab.matflow.task.product.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.product.model.TaskProduct;

import java.util.List;
@JoinProvider(model = TaskProduct.class)
public interface TaskProductService {

    /**
     * 创建流水线推送制品
     * @param taskProduct 流水线推送制品
     * @return 流水线推送制品id
     */
    String createProduct(TaskProduct taskProduct);

    /**
     * 删除流水线推送制品
     * @param productId 流水线推送制品id
     */
    void deleteProduct(String productId);


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    void deleteProductConfig(String configId);

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    TaskProduct findOneProductConfig(String configId);


    /**
     * 更新推送制品信息
     * @param taskProduct 信息
     */
    void updateProduct(TaskProduct taskProduct);

    /**
     * 查询推送制品信息
     * @param productId id
     * @return 信息
     */
    @FindOne
    TaskProduct findOneProduct(String productId);

    /**
     * 查询所有流水线推送制品
     * @return 流水线推送制品列表
     */
    @FindAll
    List<TaskProduct> findAllProduct();


    @FindList
    List<TaskProduct> findAllProductList(List<String> idList);
    
}
