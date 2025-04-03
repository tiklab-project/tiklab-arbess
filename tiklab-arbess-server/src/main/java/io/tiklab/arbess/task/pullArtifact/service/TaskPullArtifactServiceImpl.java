package io.tiklab.arbess.task.pullArtifact.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.setting.model.AuthHost;
import io.tiklab.arbess.setting.model.AuthThird;
import io.tiklab.arbess.setting.service.AuthHostService;
import io.tiklab.arbess.setting.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.pullArtifact.model.TaskPullArtifact;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.task.pullArtifact.dao.TaskPullArtifactDao;
import io.tiklab.arbess.task.pullArtifact.entity.TaskPullArtifactEntity;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

@Service
@Exporter
public class TaskPullArtifactServiceImpl implements TaskPullArtifactService {


    @Autowired
    private TaskPullArtifactDao productDao;

    @Autowired
    private AuthThirdService thirdServer;

    @Autowired
    private AuthHostService hostServer;

    @Override
    public String createPullArtifact(TaskPullArtifact taskPullArtifact) {
        TaskPullArtifactEntity taskPullArtifactEntity = BeanMapper.map(taskPullArtifact, TaskPullArtifactEntity.class);
        return productDao.createProduct(taskPullArtifactEntity);
    }


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

        if (pullType.equals(PipelineFinal.TASK_UPLOAD_SSH)){
            AuthHost oneAuthHost = hostServer.findOneAuthHost(authId);
            artifact.setAuth(oneAuthHost);
        }

        if (pullType.equals(PipelineFinal.TASK_UPLOAD_NEXUS)){
            AuthThird authServer = thirdServer.findOneAuthServer(authId);
            artifact.setAuth(authServer);
        }
        if (pullType.equals(TASK_UPLOAD_HADESS)){
            AuthThird authServer = thirdServer.findOneAuthServer(authId);
            artifact.setAuth(authServer);
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
