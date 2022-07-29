package com.tiklab.matflow.execute.service;


import com.tiklab.join.annotation.FindAll;
import com.tiklab.join.annotation.FindList;
import com.tiklab.join.annotation.FindOne;
import com.tiklab.join.annotation.JoinProvider;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.execute.model.MatFlowStructure;

import java.util.List;

@JoinProvider(model = MatFlowStructure.class)
public interface MatFlowStructureService {

    /**
     * 创建
     * @param MatFlowStructure structure信息
     * @return structureId
     */
    String createStructure(MatFlowStructure MatFlowStructure);

    /**
     * 创建配置信息
     * @param matFlowId 流水线id
     * @return 配置id
     */
    String createConfigure(String matFlowId, int taskType, MatFlowStructure matFlowStructure);

    /**
     * 删除
     * @param structureId structureId
     */
    void deleteStructure(String structureId);

    /**
     * 删除关联配置
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    void deleteTask(String taskId,int taskType);

    /**
     * 更新
     * @param MatFlowStructure 更新信息
     */
    void updateStructure(MatFlowStructure MatFlowStructure);

    /**
     * 更新任务
     * @param matFlowExecConfigure 更新信息
     */
    void updateTask(MatFlowExecConfigure matFlowExecConfigure);

    /**
     * 查询单个信息
     * @param structureId structureId
     * @return structure信息
     */
    @FindOne
    MatFlowStructure findOneStructure(String structureId);

    /**
     * 查询信息
     * @param matFlowConfigure 配置信息
     * @return 配置
     */
    List<Object>  findOneTask(MatFlowConfigure matFlowConfigure, List<Object> list);

    /**
     * 查询所有信息
     * @return structure信息集合
     */
    @FindAll
    List<MatFlowStructure> findAllStructure();

    @FindList
    List<MatFlowStructure> findAllStructureList(List<String> idList);
}
