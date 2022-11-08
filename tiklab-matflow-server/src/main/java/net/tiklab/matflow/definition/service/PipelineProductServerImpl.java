package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineProductDao;
import net.tiklab.matflow.definition.entity.PipelineProductEntity;
import net.tiklab.matflow.definition.model.PipelineProduct;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.setting.service.PipelineAuthThirdServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineProductServerImpl implements PipelineProductServer {


    @Autowired
    PipelineProductDao productDao;

    @Autowired
    PipelineAuthThirdServer thirdServer;

    /**
     * 创建流水线推送制品
     * @param pipelineProduct 流水线推送制品
     * @return 流水线推送制品id
     */
    @Override
    public String createProduct(PipelineProduct pipelineProduct) {
        PipelineProductEntity pipelineProductEntity = BeanMapper.map(pipelineProduct, PipelineProductEntity.class);
        return productDao.createProduct(pipelineProductEntity);
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
     * @param pipelineProduct 信息
     */
    @Override
    public void updateProduct(PipelineProduct pipelineProduct) {
        PipelineProductEntity productEntity = BeanMapper.map(pipelineProduct, PipelineProductEntity.class);
        productDao.updateProduct(productEntity);
    }

    /**
     * 查询推送制品信息
     * @param ProductId id
     * @return 信息集合
     */
    @Override
    public PipelineProduct findOneProduct(String ProductId) {
        PipelineProductEntity oneProduct = productDao.findOneProduct(ProductId);
        PipelineProduct product = BeanMapper.map(oneProduct, PipelineProduct.class);
        if (PipelineUntil.isNoNull(product.getAuthId())){
            PipelineAuthThird oneAuthServer = thirdServer.findOneAuthServer(product.getAuthId());
            product.setAuth(oneAuthServer);
        }
        return product;
    }

    /**
     * 查询所有流水线推送制品
     * @return 流水线推送制品列表
     */
    @Override
    public List<PipelineProduct> findAllProduct() {
        List<PipelineProductEntity> allProduct = productDao.findAllProduct();
        return BeanMapper.mapList(allProduct, PipelineProduct.class);
    }

    @Override
    public List<PipelineProduct> findAllProductList(List<String> idList) {
        List<PipelineProductEntity> allProductList = productDao.findAllProductList(idList);
        return BeanMapper.mapList(allProductList, PipelineProduct.class);
    }


}
