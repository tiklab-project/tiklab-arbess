package io.tiklab.arbess.task.artifact.service;

import io.tiklab.arbess.task.artifact.model.TaskArtifact;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 任务推送制品服务接口
 */
@JoinProvider(model = TaskArtifact.class)
public interface TaskArtifactService {

    /**
     * 创建流水线推送制品
     * @param taskArtifact 流水线推送制品
     * @return 流水线推送制品id
     */
    String createProduct(TaskArtifact taskArtifact);

    /**
     * 删除流水线推送制品
     * @param productId 流水线推送制品id
     */
    void deleteProduct(String productId);


    /**
     * 根据配置id查询任务
     * @param taskId 配置id
     * @return 任务
     */
    TaskArtifact findOneArtifactByAuth(String taskId);


    /**
     * 查询流水线推送制品是否有效
     * @param taskType 任务类型
     * @param object object
     * @return 是否有效
     */
    Boolean artifactValid(String taskType,Object object);


    /**
     * 更新推送制品信息
     * @param taskArtifact 信息
     */
    void updateProduct(TaskArtifact taskArtifact);

    /**
     * 查询推送制品信息
     * @param artifactId id
     * @return 信息
     */
    @FindOne
    TaskArtifact findOneProduct(String artifactId);

    /**
     * 查询所有流水线推送制品
     * @return 流水线推送制品列表
     */
    @FindAll
    List<TaskArtifact> findAllProduct();


    @FindList
    List<TaskArtifact> findAllProductList(List<String> idList);
    
}
