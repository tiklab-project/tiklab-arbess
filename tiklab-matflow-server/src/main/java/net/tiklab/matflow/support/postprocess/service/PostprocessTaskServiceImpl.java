package net.tiklab.matflow.support.postprocess.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.support.postprocess.model.Postprocess;
import net.tiklab.matflow.task.message.model.TaskMessageType;
import net.tiklab.matflow.task.script.model.TaskScript;
import net.tiklab.matflow.task.script.service.TaskScriptService;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.service.TasksService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Exporter
public class PostprocessTaskServiceImpl implements PostprocessTaskService {


    @Autowired
    private TasksService tasksService;

    @Autowired
    private TaskScriptService scriptServer;

    /**
     * 更新后置任务配置信息
     * @param config 配置
     */
    @Override
    public void updateConfig(Postprocess config){
        int taskType = config.getTaskType();
        String configId = "config.getConfigId()";
        switch (taskType / 10) {
            case 6 -> {
                // TaskMessageType message = messageTypeServer.findMessage(configId);
                // if (message != null){
                //     messageTypeServer.deleteAllMessage(configId);
                // }
                // createMessage(config);
            }
            case 7 -> {
                TaskScript script = scriptServer.findScript(configId);
                if (script != null){
                    deleteConfig(config);
                }
                createScript(config);
            }
            default -> throw new ApplicationException("未知的操作类型taskType："+ taskType);
        }
    }

    /**
     * 初始化任务名称
     * @param taskType 任务类型
     * @return 名称
     */
    @Override
    public String findConfigName(int taskType){
        switch (taskType) {
            case 61 -> {
                return "消息通知";
            }
            case 71 -> {
                return "执行Bat脚本";
            }
            case 72 -> {
                return "执行Shell脚本";
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * 创建后置任务
     * @param postprocess 配置信息
     */
    private void createMessage(Postprocess postprocess){
        String object = JSON.toJSONString(postprocess.getValues());
        TaskMessageType message = JSON.parseObject(object, TaskMessageType.class);
        Tasks tasks = new Tasks();
        tasks.setValues(message);
        tasks.setTaskSort(postprocess.getTaskSort());
        tasks.setTaskType(postprocess.getTaskType());

    }

    //执行脚本
    private void createScript(Postprocess config){
        String object = JSON.toJSONString(config.getValues());
        TaskScript script = JSON.parseObject(object, TaskScript.class);
        if (script == null){
            script = new TaskScript();
        }
        // script.setTaskId(config.getConfigId());
        script.setType(config.getTaskType());
        scriptServer.createScript(script);
    }

    /**
     * 查询任务信息
     * @param config 配置类型
     * @return 配置
     */
    @Override
    public Object findOneConfig(Postprocess config){
        int taskType = config.getTaskType();
        // String configId = config.getConfigId();
        int taskSort = config.getTaskSort();
        switch (taskType / 10) {
            case 6 -> {
                // TaskMessage message = messageTypeServer.findConfigMessage(configId);
                // message.setType(taskType);
                // message.setSort(taskSort);
                // return message;
            }
            case 7 -> {
                TaskScript oneScript = scriptServer.findScript("configId");
                if (oneScript == null){
                    oneScript = new TaskScript();
                }
                oneScript.setTaskId("configId");
                oneScript.setType(taskType);
                oneScript.setSort(taskSort);
                return oneScript;
            }
            default -> {
                throw new ApplicationException(50001,"未知的操作类型taskType："+ taskType);
            }
        }
        return null;
    }

    /**
     * 删除任务
     * @param config 配置
     */
    public void deleteConfig(Postprocess config){
        int taskType = config.getTaskType();
        String configId = "config.getConfigId()";
        switch (taskType / 10) {
            case 6 -> {
                // messageTypeServer.deleteAllMessage(configId);
            }
            case 7 -> {
                scriptServer.deleteOneScript(configId);
            }
            default -> {
                throw new ApplicationException(50001,"未知的操作类型taskType："+ taskType);
            }
        }
    }

}




















