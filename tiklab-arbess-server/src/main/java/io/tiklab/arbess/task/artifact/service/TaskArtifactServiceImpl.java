package io.tiklab.arbess.task.artifact.service;

import io.tiklab.arbess.setting.host.model.AuthHost;
import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.host.service.AuthHostService;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.task.artifact.model.TaskArtifact;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.task.artifact.dao.TaskArtifactDao;
import io.tiklab.arbess.task.artifact.entity.TaskArtifactEntity;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@Exporter
public class TaskArtifactServiceImpl implements TaskArtifactService {


    @Autowired
    private TaskArtifactDao productDao;

    @Autowired
    private AuthThirdService thirdServer;

    @Autowired
    private AuthHostService hostServer;

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

        if (artifactType.equals(PipelineFinal.TASK_UPLOAD_SSH)){
            AuthHost oneAuthHost = hostServer.findOneAuthHost(authId);
            artifact.setAuth(oneAuthHost);
        }
        return artifact;
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
