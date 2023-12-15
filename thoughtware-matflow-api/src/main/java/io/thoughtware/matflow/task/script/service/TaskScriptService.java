package io.thoughtware.matflow.task.script.service;

import io.thoughtware.matflow.task.script.model.TaskScript;
import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;

import java.util.List;
/**
 * 脚本执行服务接口
 */
@JoinProvider(model = TaskScript.class)
public interface TaskScriptService {

    /**
     * 创建
     * @param taskScript script信息
     * @return scriptId
     */
    String createScript(TaskScript taskScript) ;


    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    TaskScript findScript(String configId);


    /**
     * 删除任务
     * @param configId 配置id
     */
    void deleteOneScript(String configId);

    /**
     * 删除
     * @param scriptId scriptId
     */
    void deleteScript(String scriptId) ;


    /**
     * 更新信息
     * @param taskScript 信息
     */
    void updateScript(TaskScript taskScript);

    /**
     * 查询单个信息
     * @param scriptId pipelineScriptId
     * @return script信息
     */
    @FindOne
    TaskScript findOneScript(String scriptId) ;

    /**
     * 查询所有信息
     * @return script信息集合
     */
    @FindAll
    List<TaskScript> findAllScript() ;

    @FindList
    List<TaskScript> findAllScriptList(List<String> idList);


}