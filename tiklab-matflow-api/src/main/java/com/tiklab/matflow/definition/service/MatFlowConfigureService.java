package com.tiklab.matflow.definition.service;



import com.tiklab.join.annotation.FindAll;
import com.tiklab.join.annotation.FindList;
import com.tiklab.join.annotation.FindOne;
import com.tiklab.join.annotation.JoinProvider;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线配置
 */
@JoinProvider(model = MatFlowConfigure.class)
public interface MatFlowConfigureService {

    /**
     * 创建流水线配置
     * @param matFlowConfigure 流水线配置信息
     * @return 流水线配置id
     */
    String createConfigure(@NotNull @Valid MatFlowConfigure matFlowConfigure);

    /**
     * 创建任务
     * @param matFlowConfigure 配置信息
     * @param matFlowId 流水线id
     */
    void createTask(MatFlowConfigure matFlowConfigure, String matFlowId);

    /**
     * 根据流水线id删除流水线配置
     * @param configureId 流水线id
     */
    void deleteConfigure(String configureId);

    /**
     * 根据类型查询配置信息
     * @param matFlowId 流水线id
     * @param type 类型
     * @return 配置信息
     */
    MatFlowConfigure findOneConfigure(String matFlowId , int type);


    /**
     * 删除单个任务
     * @param matFlowId 流水线id
     */
    void deleteTask(String matFlowId);

    /**
     * 更新流水线配置
     * @param matFlowConfigure 配置信息
     */
    void updateConfigure(MatFlowConfigure matFlowConfigure);

    /**
     * 更新任务信息
     * @param matFlowExecConfigure 更新信息
     */
    void updateTask(MatFlowExecConfigure matFlowExecConfigure);

    /**
     * 查询流水线配置信息
     * @param configureId 流水线配置id
     * @return 配置信息
     */
    @FindOne
    MatFlowConfigure findOneConfigure(String configureId);

    /**
     * 查询所有配置文件
     * @return 配置文件列表
     */
    @FindAll
    List<MatFlowConfigure> findAllConfigure();

    /**
     * 根据流水线id查询所有配置
     * @param matFlowId 流水线id
     * @return 配置集合
     */
    List<MatFlowConfigure> findAllConfigure(String matFlowId);

    /**
     * 查询所有配置
     * @param matFlowId 流水线id
     * @return 配置信息
     */
    List<Object> findAll(String matFlowId);

    @FindList
    List<MatFlowConfigure> findAllConfigureList(List<String> idList);
}
