package io.thoughtware.matflow.task.pullArtifact.service;

import io.thoughtware.matflow.setting.model.AuthHost;
import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.service.AuthHostService;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.task.pullArtifact.model.TaskPullArtifact;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.matflow.task.artifact.model.XpackRepository;
import io.thoughtware.matflow.task.artifact.service.TaskArtifactXpackService;
import io.thoughtware.matflow.task.pullArtifact.dao.TaskPullArtifactDao;
import io.thoughtware.matflow.task.pullArtifact.entity.TaskPullArtifactEntity;
import io.thoughtware.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class TaskPullArtifactServiceImpl implements TaskPullArtifactService {


    @Autowired
    private TaskPullArtifactDao productDao;

    @Autowired
    private AuthThirdService thirdServer;

    @Autowired
    private AuthHostService hostServer;

    @Autowired
    private TaskArtifactXpackService taskArtifactXpackService;

    /**
     * 创建流水线推送制品
     * @param taskPullArtifact 流水线推送制品
     * @return 流水线推送制品id
     */
    @Override
    public String createPullArtifact(TaskPullArtifact taskPullArtifact) {
        TaskPullArtifactEntity taskPullArtifactEntity = BeanMapper.map(taskPullArtifact, TaskPullArtifactEntity.class);
        return productDao.createProduct(taskPullArtifactEntity);
    }

    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    @Override
    public void deletePullArtifactTask(String configId){
        TaskPullArtifact oneProductConfig = findPullArtifact(configId,"");
        deletePullArtifact(oneProductConfig.getTaskId());
    }


    /**
     * 根据配置id查询任务
     * @return 任务
     */
    @Override
    public TaskPullArtifact findPullArtifact(String taskId,String taskType){
        List<TaskPullArtifact> allProduct = findAllPullArtifact();
        if (allProduct == null){
            return null;
        }
        for (TaskPullArtifact taskPullArtifact : allProduct) {
            if (taskPullArtifact.getTaskId().equals(taskId)){
                TaskPullArtifact pullArtifact = findOnePullArtifact(taskPullArtifact.getTaskId());
                String authId = pullArtifact.getAuthId();
                if (Objects.isNull(authId)){
                    return pullArtifact;
                }
                String pullType = pullArtifact.getPullType();

                if (pullType.equals(PipelineFinal.TASK_ARTIFACT_SSH)){
                    AuthHost oneAuthHost = hostServer.findOneAuthHost(authId);
                    pullArtifact.setAuth(oneAuthHost);
                }

                if (pullType.equals(PipelineFinal.TASK_ARTIFACT_NEXUS)){
                    AuthThird authServer = thirdServer.findOneAuthServer(authId);
                    pullArtifact.setAuth(authServer);
                }
                if (pullType.equals(PipelineFinal.TASK_ARTIFACT_XPACK) && !Objects.isNull(pullArtifact.getRepository())){
                    XpackRepository xpackRepository = taskArtifactXpackService.findRepository(authId, pullArtifact.getRepository().getId());
                    if (!Objects.isNull(xpackRepository)){
                        pullArtifact.setRepository(xpackRepository);
                    }
                }

                return pullArtifact;
            }
        }
        return null;
    }



    /**
     * 删除流水线推送制品
     * @param ProductId 流水线推送制品id
     */
    @Override
    public void deletePullArtifact(String ProductId) {
        productDao.deleteProduct(ProductId);
    }

    /**
     * 更新推送制品信息
     * @param taskPullArtifact 信息
     */
    @Override
    public void updatePullArtifact(TaskPullArtifact taskPullArtifact) {
        TaskPullArtifactEntity productEntity = BeanMapper.map(taskPullArtifact, TaskPullArtifactEntity.class);
        productDao.updateProduct(productEntity);
    }

    /**
     * 查询推送制品信息
     * @param ProductId id
     * @return 信息集合
     */
    @Override
    public TaskPullArtifact findOnePullArtifact(String ProductId) {
        TaskPullArtifactEntity oneProduct = productDao.findOneProduct(ProductId);
        TaskPullArtifact product = BeanMapper.map(oneProduct, TaskPullArtifact.class);
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
    public List<TaskPullArtifact> findAllPullArtifact() {
        List<TaskPullArtifactEntity> allProduct = productDao.findAllProduct();
        return BeanMapper.mapList(allProduct, TaskPullArtifact.class);
    }

    @Override
    public List<TaskPullArtifact> findAllPullArtifactList(List<String> idList) {
        List<TaskPullArtifactEntity> allProductList = productDao.findAllProductList(idList);
        return BeanMapper.mapList(allProductList, TaskPullArtifact.class);
    }

    private Object findAuth(String id){
        AuthThird oneAuthServer =thirdServer.findOneAuthServer(id);
        if (oneAuthServer != null){
            return oneAuthServer;
        }
        return hostServer.findOneAuthHost(id);
    }

}
