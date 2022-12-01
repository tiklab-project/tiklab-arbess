package net.tiklab.matflow.definition.service.task;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.task.PipelineProduct;

import java.util.List;
@JoinProvider(model = PipelineProduct.class)
public interface PipelineProductServer {

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
