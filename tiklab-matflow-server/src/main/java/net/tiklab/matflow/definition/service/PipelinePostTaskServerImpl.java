package net.tiklab.matflow.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.PipelinePost;
import net.tiklab.matflow.task.model.PipelineMessage;
import net.tiklab.matflow.task.model.PipelineMessageType;
import net.tiklab.matflow.task.model.PipelineScript;
import net.tiklab.matflow.task.model.PipelineUserMessage;
import net.tiklab.matflow.task.server.PipelineMessageTypeServer;
import net.tiklab.matflow.task.server.PipelineMessageUserServer;
import net.tiklab.matflow.task.server.PipelineScriptServer;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelinePostTaskServerImpl implements PipelinePostTaskServer {

    @Autowired
    private PipelineMessageTypeServer messageTypeServer;

    @Autowired
    private PipelineMessageUserServer messageUserServer;

    @Autowired
    private PipelineScriptServer scriptServer;

    /**
     * 更新后置任务配置信息
     * @param config 配置
     */
    @Override
    public void updateConfig(PipelinePost config){
        int taskType = config.getTaskType();
        String configId = config.getConfigId();
        switch (taskType / 10) {
            case 6 -> {
                PipelineMessageType message = messageTypeServer.findMessage(configId);
                if (message != null){
                    deleteConfig(config);
                }
                createMessage(config);
            }
            case 7 -> {
                PipelineScript script = scriptServer.findScript(configId);
                if (script != null){
                    deleteConfig(config);
                }
                createScript(config);
            }
            default -> {
                throw new ApplicationException("未知的操作类型taskType："+ taskType);
            }
        }
    }

    /**
     * 创建后置任务
     * @param config 配置信息
     */
    private void createMessage(PipelinePost config){
        String object = JSON.toJSONString(config.getValues());
        PipelineMessage message = JSON.parseObject(object, PipelineMessage.class);
        List<String> typeList = null;
        List<PipelineUserMessage> userList = null;
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
            PipelineUserMessage pipelineUserMessage = new PipelineUserMessage();
            User user = new User();
            user.setId(LoginContext.getLoginId());
            pipelineUserMessage.setUser(user);
            pipelineUserMessage.setType(1);
            userList.add(pipelineUserMessage);
        }

        for (String s : typeList) {
            PipelineMessageType messageType = new PipelineMessageType();
            messageType.setTaskType(s);
            messageType.setConfigId(config.getConfigId());
            String messageId = messageTypeServer.createMessage(messageType);
            messageUserServer.createAllMessage(userList,messageId);
        }
    }

    //执行脚本
    private void createScript(PipelinePost config){
        String object = JSON.toJSONString(config.getValues());
        PipelineScript script = JSON.parseObject(object, PipelineScript.class);
        if (script == null){
            script = new PipelineScript();
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
    public Object findOneConfig(PipelinePost config){
        int taskType = config.getTaskType();
        String configId = config.getConfigId();
        int taskSort = config.getTaskSort();
        switch (taskType / 10) {
            case 6 -> {
                PipelineMessage message = messageTypeServer.findConfigMessage(configId);
                message.setType(taskType);
                message.setSort(taskSort);
                return message;
            }
            case 7 -> {
                PipelineScript oneScript = scriptServer.findScript(configId);
                if (oneScript == null){
                    oneScript = new PipelineScript();
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
    public void deleteConfig(PipelinePost config){
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




















