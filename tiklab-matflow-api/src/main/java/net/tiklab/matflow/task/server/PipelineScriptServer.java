package net.tiklab.matflow.task.server;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.model.PipelineScript;

import java.util.List;

@JoinProvider(model = PipelineScript.class)
public interface PipelineScriptServer {

    /**
     * 创建
     * @param pipelineScript script信息
     * @return scriptId
     */
    String createScript(PipelineScript pipelineScript) ;


    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    PipelineScript findScript(String configId);


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
     * @param pipelineScript 信息
     */
    void updateScript(PipelineScript pipelineScript);

    /**
     * 查询单个信息
     * @param scriptId pipelineScriptId
     * @return script信息
     */
    @FindOne
    PipelineScript findOneScript(String scriptId) ;

    /**
     * 查询所有信息
     * @return script信息集合
     */
    @FindAll
    List<PipelineScript> findAllScript() ;

    @FindList
    List<PipelineScript> findAllScriptList(List<String> idList);


}
