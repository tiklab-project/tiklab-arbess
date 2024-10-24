package io.tiklab.arbess.task.artifact.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.setting.model.AuthHost;
import io.tiklab.arbess.setting.model.AuthThird;
import io.tiklab.arbess.setting.service.AuthHostService;
import io.tiklab.arbess.setting.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.artifact.model.TaskArtifact;
import io.tiklab.arbess.task.artifact.model.XpackRepository;
import io.tiklab.arbess.task.pullArtifact.model.TaskPullArtifact;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.task.artifact.dao.TaskArtifactDao;
import io.tiklab.arbess.task.artifact.entity.TaskArtifactEntity;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;
import static io.tiklab.arbess.support.util.util.PipelineFinal.TASK_ARTIFACT_SSH;

@Service
@Exporter
public class TaskArtifactServiceImpl implements TaskArtifactService {


    @Autowired
    private TaskArtifactDao productDao;

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
     * 根据配置id查询任务
     * @return 任务
     */
    @Override
    public TaskArtifact findOneArtifactByAuth(String taskId){

        TaskArtifact artifact = findOneProduct(taskId);
        if (Objects.isNull(artifact)){
            return null;
        }
        String authId = artifact.getAuthId();
        if (Objects.isNull(authId)){
            return artifact;
        }
        String artifactType = artifact.getArtifactType();
        AuthThird authServer = thirdServer.findOneAuthServer(authId);
        artifact.setAuth(authServer);

        if (artifactType.equals(PipelineFinal.TASK_ARTIFACT_SSH)){
            AuthHost oneAuthHost = hostServer.findOneAuthHost(authId);
            artifact.setAuth(oneAuthHost);
        }

        XpackRepository repository = artifact.getRepository();
        if (artifactType.equals(PipelineFinal.TASK_ARTIFACT_XPACK) && !Objects.isNull(repository)){
            XpackRepository xpackRepository = taskArtifactXpackService.findRepository(authId, artifact.getRepository().getId());
            if (!Objects.isNull(xpackRepository)){
                artifact.setPutAddress(xpackRepository.getName());
                artifact.setRepository(xpackRepository);
            }
        }
        return artifact;
    }


    @Override
    public Boolean artifactValid(String taskType,TaskArtifact artifact){
        String artifactType = artifact.getArtifactType();

        if (taskType.equals(TASK_ARTIFACT_DOCKER)){
            if (artifactType.equals(TASK_ARTIFACT_NEXUS)){
                if (!PipelineUtil.isNoNull(artifact.getDockerImage())){
                    return false;
                }
                if (!PipelineUtil.isNoNull(artifact.getAuthId())){
                    return false;
                }
            }
            if (artifactType.equals(TASK_ARTIFACT_XPACK)){
                if (!PipelineUtil.isNoNull(artifact.getDockerImage())){
                    return false;
                }
                if (!PipelineUtil.isNoNull(artifact.getAuthId())){
                    return false;
                }
                if (Objects.isNull(artifact.getRepository()) || !PipelineUtil.isNoNull(artifact.getRepository().getId())){
                    return false;
                }
            }
        }

        if (taskType.equals(TASK_ARTIFACT_NODEJS)){
            return true;
        }
        if (taskType.equals(TASK_ARTIFACT_MAVEN)){
            if (artifactType.equals(TASK_ARTIFACT_NEXUS)){
                if (!PipelineUtil.isNoNull(artifact.getAuthId())){
                    return false;
                }
                if (!PipelineUtil.isNoNull(artifact.getArtifactId())){
                    return false;
                }
                if (!PipelineUtil.isNoNull(artifact.getVersion())){
                    return false;
                }
                if (!PipelineUtil.isNoNull(artifact.getGroupId())){
                    return false;
                }
            }
            if (artifactType.equals(TASK_ARTIFACT_XPACK)){
                if (!PipelineUtil.isNoNull(artifact.getAuthId())){
                    return false;
                }
                if (!PipelineUtil.isNoNull(artifact.getRepository().getName())){
                    return false;
                }
            }
            if (artifactType.equals(TASK_ARTIFACT_SSH)){
                if (!PipelineUtil.isNoNull(artifact.getAuthId())){
                    return false;
                }
                return PipelineUtil.isNoNull(artifact.getPutAddress());
            }
        }
        return true;
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
     * @param artifactId id
     * @return 信息集合
     */
    @Override
    public TaskArtifact findOneProduct(String artifactId) {
        TaskArtifactEntity oneProduct = productDao.findOneProduct(artifactId);
        return BeanMapper.map(oneProduct, TaskArtifact.class);
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
        AuthThird oneAuthServer = thirdServer.findOneAuthServer(id);
        if (oneAuthServer != null){
            return oneAuthServer;
        }
        return hostServer.findOneAuthHost(id);
    }

}
