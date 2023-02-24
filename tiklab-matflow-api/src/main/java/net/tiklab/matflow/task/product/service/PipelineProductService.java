package net.tiklab.matflow.task.product.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.product.model.PipelineProduct;

import java.util.List;
@JoinProvider(model = PipelineProduct.class)
public interface PipelineProductService {

    /**
     * 创建流水线推送制品
     * @param pipelineProduct 流水线推送制品
     * @return 流水线推送制品id
     */
    String createProduct(PipelineProduct pipelineProduct);

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
    PipelineProduct findOneProductConfig(String configId);


    /**
     * 更新推送制品信息
     * @param pipelineProduct 信息
     */
    void updateProduct(PipelineProduct pipelineProduct);

    /**
     * 查询推送制品信息
     * @param productId id
     * @return 信息
     */
    @FindOne
    PipelineProduct findOneProduct(String productId);

    /**
     * 查询所有流水线推送制品
     * @return 流水线推送制品列表
     */
    @FindAll
    List<PipelineProduct> findAllProduct();


    @FindList
    List<PipelineProduct> findAllProductList(List<String> idList);
    
}
