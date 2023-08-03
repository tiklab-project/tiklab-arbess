package io.tiklab.matflow.task.artifact.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.matflow.setting.model.AuthHost;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthHostService;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.artifact.dao.TaskProductDao;
import io.tiklab.matflow.task.artifact.entity.TaskArtifactEntity;
import io.tiklab.matflow.task.artifact.model.TaskArtifact;
import io.tiklab.matflow.task.artifact.model.XpackRepository;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class TaskArtifactServiceImpl implements TaskArtifactService {


    @Autowired
    private TaskProductDao productDao;

    @Autowired
    private AuthThirdService thirdServer;

    @Autowired
    private AuthHostService hostServer;

    @Autowired
    private TaskArtifactXpackService taskArtifactXpackService;

    /**
     * 创建流水线推送制品
     * @param taskArtifact 流水线推送制品
     * @return 流水线推送制品id
     */
    @Override
    public String createProduct(TaskArtifact taskArtifact) {
        TaskArtifactEntity taskArtifactEntity = BeanMapper.map(taskArtifact, TaskArtifactEntity.class);
        return productDao.createProduct(taskArtifactEntity);
    }

    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deleteProductConfig(String configId){
        TaskArtifact oneProductConfig = findOneProductConfig(configId,"");
        deleteProduct(oneProductConfig.getTaskId());
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    @Override
    public TaskArtifact findOneProductConfig(String configId,String taskType){
        List<TaskArtifact> allProduct = findAllProduct();
        if (allProduct == null){
            return null;
        }
        for (TaskArtifact taskArtifact : allProduct) {
            if (taskArtifact.getTaskId().equals(configId)){
                TaskArtifact product = findOneProduct(taskArtifact.getTaskId());
                String authId = product.getAuthId();
                if (Objects.isNull(authId)){
                    return product;
                }
                AuthThird authServer = thirdServer.findOneAuthServer(authId);
                product.setAuth(authServer);

                if (taskType.equals("ssh")){
                    AuthHost oneAuthHost = hostServer.findOneAuthHost(authId);
                    product.setAuth(oneAuthHost);
                }

                XpackRepository repositorys = taskArtifact.getRepository();
                if (taskType.equals("xpack") && !Objects.isNull(repositorys)){
                    XpackRepository repository = taskArtifactXpackService.findRepository(authId, taskArtifact.getRepository().getId());
                    if (!Objects.isNull(repository)){
                        product.setPutAddress(repository.getName());
                        product.setRepository(repository);
                    }
                }
                return product;
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
     * @param taskArtifact 信息
     */
    @Override
    public void updateProduct(TaskArtifact taskArtifact) {
        TaskArtifactEntity productEntity = BeanMapper.map(taskArtifact, TaskArtifactEntity.class);
        productDao.updateProduct(productEntity);
    }

    /**
     * 查询推送制品信息
     * @param ProductId id
     * @return 信息集合
     */
    @Override
    public TaskArtifact findOneProduct(String ProductId) {
        TaskArtifactEntity oneProduct = productDao.findOneProduct(ProductId);
        TaskArtifact product = BeanMapper.map(oneProduct, TaskArtifact.class);
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
    public List<TaskArtifact> findAllProduct() {
        List<TaskArtifactEntity> allProduct = productDao.findAllProduct();
        return BeanMapper.mapList(allProduct, TaskArtifact.class);
    }

    @Override
    public List<TaskArtifact> findAllProductList(List<String> idList) {
        List<TaskArtifactEntity> allProductList = productDao.findAllProductList(idList);
        return BeanMapper.mapList(allProductList, TaskArtifact.class);
    }

    private Object findAuth(String id){
        AuthThird oneAuthServer =thirdServer.findOneAuthServer(id);
        if (oneAuthServer != null){
            return oneAuthServer;
        }
        return hostServer.findOneAuthHost(id);
    }

}
