package io.thoughtware.matflow.task.pullArtifact.service;

import io.thoughtware.matflow.setting.model.AuthHost;
import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.service.AuthHostService;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.task.artifact.model.TaskArtifact;
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

import static io.thoughtware.matflow.support.util.util.PipelineFinal.*;

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


    @Override
    public String createPullArtifact(TaskPullArtifact taskPullArtifact) {
        TaskPullArtifactEntity taskPullArtifactEntity = BeanMapper.map(taskPullArtifact, TaskPullArtifactEntity.class);
        return productDao.createProduct(taskPullArtifactEntity);
    }


    @Override
    public Boolean pullArtifactValid(String taskType,Object object){
        TaskPullArtifact pullArtifact = (TaskPullArtifact) object;
        String pullType = pullArtifact.getPullType();

        switch (taskType) {
            case TASK_PULL_DOCKER -> {
                if (pullType.equals(TASK_ARTIFACT_NEXUS)) {
                    return PipelineUtil.isNoNull(pullArtifact.getDockerImage());
                }
                if (pullType.equals(TASK_ARTIFACT_XPACK)) {
                    if (!PipelineUtil.isNoNull(pullArtifact.getDockerImage())) {
                        return false;
                    }
                    return !Objects.isNull(pullArtifact.getRepository());
                }
                return true;
            }
            case TASK_PULL_NODEJS -> {
                return true;
            }
            case TASK_PULL_MAVEN -> {
                if (pullType.equals(TASK_ARTIFACT_NEXUS)) {
                    if (!PipelineUtil.isNoNull(pullArtifact.getArtifactId())) {
                        return false;
                    }
                    if (!PipelineUtil.isNoNull(pullArtifact.getVersion())) {
                        return false;
                    }
                    if (!PipelineUtil.isNoNull(pullArtifact.getGroupId())) {
                        return false;
                    }
                }
                if (pullType.equals(TASK_ARTIFACT_XPACK)) {
                    if (!PipelineUtil.isNoNull(pullArtifact.getArtifactId())) {
                        return false;
                    }
                    if (!PipelineUtil.isNoNull(pullArtifact.getVersion())) {
                        return false;
                    }
                    if (!PipelineUtil.isNoNull(pullArtifact.getGroupId())) {
                        return false;
                    }
                    if (Objects.isNull(pullArtifact.getRepository())) {
                        return false;
                    }
                }

                if (pullType.equals(TASK_ARTIFACT_SSH)) {
                    if (!PipelineUtil.isNoNull(pullArtifact.getLocalAddress())) {
                        return false;
                    }
                    return PipelineUtil.isNoNull(pullArtifact.getRemoteAddress());
                }
                return true;
            }
        }

        return true;
    };


    /**
     * 根据配置id查询任务
     * @return 任务
     */
    @Override
    public TaskPullArtifact findPullArtifactByAuth(String taskId){

        TaskPullArtifact artifact = findOnePullArtifact(taskId);
        if (Objects.isNull(artifact)){
            return null;
        }

        String authId = artifact.getAuthId();
        if (Objects.isNull(authId)){
            return artifact;
        }
        String pullType = artifact.getPullType();

        if (pullType.equals(PipelineFinal.TASK_ARTIFACT_SSH)){
            AuthHost oneAuthHost = hostServer.findOneAuthHost(authId);
            artifact.setAuth(oneAuthHost);
        }

        if (pullType.equals(PipelineFinal.TASK_ARTIFACT_NEXUS)){
            AuthThird authServer = thirdServer.findOneAuthServer(authId);
            artifact.setAuth(authServer);
        }
        if (pullType.equals(PipelineFinal.TASK_ARTIFACT_XPACK) && !Objects.isNull(artifact.getRepository())){
            XpackRepository xpackRepository = taskArtifactXpackService.findRepository(authId, artifact.getRepository().getId());
            if (!Objects.isNull(xpackRepository)){
                artifact.setRepository(xpackRepository);
            }
        }

        return artifact;
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
