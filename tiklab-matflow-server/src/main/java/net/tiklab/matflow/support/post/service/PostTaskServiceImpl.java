package net.tiklab.matflow.support.post.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.support.post.model.Post;
import net.tiklab.matflow.task.message.model.TaskMessage;
import net.tiklab.matflow.task.message.model.TaskMessageType;
import net.tiklab.matflow.task.script.model.TaskScript;
import net.tiklab.matflow.task.message.model.TaskUserSendMessageType;
import net.tiklab.matflow.task.message.service.TaskMessageTypeService;
import net.tiklab.matflow.task.message.service.TaskMessageUserService;
import net.tiklab.matflow.task.script.service.TaskScriptService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PostTaskServiceImpl implements PostTaskService {

    @Autowired
    private TaskMessageTypeService messageTypeServer;

    @Autowired
    private TaskMessageUserService messageUserServer;

    @Autowired
    private TaskScriptService scriptServer;

    /**
     * 更新后置任务配置信息
     * @param config 配置
     */
    @Override
    public void updateConfig(Post config){
        int taskType = config.getTaskType();
        String configId = config.getConfigId();
        switch (taskType / 10) {
            case 6 -> {
                TaskMessageType message = messageTypeServer.findMessage(configId);
                if (message != null){
                    messageTypeServer.deleteAllMessage(configId);
                }
                createMessage(config);
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
     * @param config 配置信息
     */
    private void createMessage(Post config){
        String object = JSON.toJSONString(config.getValues());
        TaskMessage message = JSON.parseObject(object, TaskMessage.class);
        List<String> typeList = null;
        List<TaskUserSendMessageType> userList = null;
        if (message != null){
            typeList = message.getTypeList();
            userList = message.getUserList();
        }

        if (typeList == null){
            typeList = new ArrayList<>();
            typeList.add("site");
        }

        if (userList == null){
            userList = new ArrayList<>();
            TaskUserSendMessageType taskUserSendMessageType = new TaskUserSendMessageType();
            User user = new User();
            user.setId(LoginContext.getLoginId());
            taskUserSendMessageType.setUser(user);
            taskUserSendMessageType.setMessageType(1);
            userList.add(taskUserSendMessageType);
        }

        for (String s : typeList) {
            TaskMessageType messageType = new TaskMessageType();
            messageType.setTaskType(s);
            messageType.setConfigId(config.getConfigId());
            String messageId = messageTypeServer.createMessage(messageType);
            messageUserServer.createAllMessage(userList,messageId);
        }
    }

    //执行脚本
    private void createScript(Post config){
        String object = JSON.toJSONString(config.getValues());
        TaskScript script = JSON.parseObject(object, TaskScript.class);
        if (script == null){
            script = new TaskScript();
        }
        script.setConfigId(config.getConfigId());
        script.setType(config.getTaskType());
        scriptServer.createScript(script);
    }

    /**
     * 查询任务信息
     * @param config 配置类型
     * @return 配置
     */
    @Override
    public Object findOneConfig(Post config){
        int taskType = config.getTaskType();
        String configId = config.getConfigId();
        int taskSort = config.getTaskSort();
        switch (taskType / 10) {
            case 6 -> {
                TaskMessage message = messageTypeServer.findConfigMessage(configId);
                message.setType(taskType);
                message.setSort(taskSort);
                return message;
            }
            case 7 -> {
                TaskScript oneScript = scriptServer.findScript(configId);
                if (oneScript == null){
                    oneScript = new TaskScript();
                }
                oneScript.setConfigId(configId);
                oneScript.setType(taskType);
                oneScript.setSort(taskSort);
                return oneScript;
            }
            default -> {
                throw new ApplicationException(50001,"未知的操作类型taskType："+ taskType);
            }
        }
    }

    /**
     * 删除任务
     * @param config 配置
     */
    public void deleteConfig(Post config){
        int taskType = config.getTaskType();
        String configId = config.getConfigId();
        switch (taskType / 10) {
            case 6 -> {
                messageTypeServer.deleteAllMessage(configId);
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




















