package com.doublekit.pipeline.example.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.setting.proof.model.Proof;

import java.util.List;
import java.util.Map;


@JoinProvider(model = PipelineCode.class)
public interface PipelineCodeService {

    /**
     * 创建
     * @param pipelineCode code信息
     * @return codeId
     */
     String createCode(PipelineCode pipelineCode);

    /**
     * 创建配置
     * @param pipelineId 流水线id
     * @return 配置id
     */
    String createConfigure(String pipelineId,int taskType,PipelineCode pipelineCode);

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
    void deleteTask(String taskId,int taskType);

    /**
     * 更新
     * @param pipelineCode 更新信息
     */
     void updateCode(PipelineCode pipelineCode);

    /**
     * 更新任务
     * @param map 更新信息
     */
     void updateTask(Map<String,Object> map);

    /**
     * 查询单个信息
     * @param codeId codeId
     * @return code信息
     */
    @FindOne
    PipelineCode findOneCode(String codeId);

    /**
     * 查询信息
     * @param pipelineConfigure 配置信息
     * @return 配置
     */
    List<Object> findOneTask( PipelineConfigure pipelineConfigure ,List<Object> list);

    /**
     * 查询所有信息
     * @return code信息集合
     */
    @FindAll
     List<PipelineCode> findAllCode();

    @FindList
    List<PipelineCode> findAllCodeList(List<String> idList);
    
}
