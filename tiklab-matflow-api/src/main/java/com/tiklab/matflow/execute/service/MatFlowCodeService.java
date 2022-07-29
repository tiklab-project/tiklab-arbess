package com.tiklab.matflow.execute.service;


import com.tiklab.join.annotation.FindAll;
import com.tiklab.join.annotation.FindList;
import com.tiklab.join.annotation.FindOne;
import com.tiklab.join.annotation.JoinProvider;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.execute.model.MatFlowCode;

import java.util.List;


@JoinProvider(model = MatFlowCode.class)
public interface MatFlowCodeService {

    /**
     * 创建
     * @param matFlowCode code信息
     * @return codeId
     */
     String createCode(MatFlowCode matFlowCode);

    /**
     * 创建配置
     * @param pipelineId 流水线id
     * @return 配置id
     */
    String createConfigure(String pipelineId, MatFlowCode matFlowCode);

    /**
     * 删除
     * @param codeId codeId
     */
     void deleteCode(String codeId);

    /**
     * 删除关联配置
     * @param taskId 任务id
     * @param taskType 任务类型
     */
    void deleteTask(String taskId, int taskType );

    /**
     * 更新
     * @param matFlowCode 更新信息
     */
     void updateCode(MatFlowCode matFlowCode);

    /**
     * 更新任务
     * @param matFlowExecConfigure 更新信息
     */
     void updateTask(MatFlowExecConfigure matFlowExecConfigure);

    /**
     * 查询单个信息
     * @param codeId codeId
     * @return code信息
     */
    @FindOne
    MatFlowCode findOneCode(String codeId);

    /**
     * 查询信息
     * @param matFlowConfigure 配置信息
     * @return 配置
     */
    List<Object> findOneTask(MatFlowConfigure matFlowConfigure, List<Object> list);

    /**
     * 查询所有信息
     * @return code信息集合
     */
    @FindAll
     List<MatFlowCode> findAllCode();

    @FindList
    List<MatFlowCode> findAllCodeList(List<String> idList);

}
