package net.tiklab.matflow.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.PipelineAfterConfig;
import net.tiklab.matflow.definition.model.task.PipelineMessage;
import net.tiklab.matflow.definition.model.task.PipelineMessageType;
import net.tiklab.matflow.definition.model.task.PipelineScript;
import net.tiklab.matflow.definition.model.task.PipelineUserMessage;
import net.tiklab.matflow.definition.service.task.PipelineMessageTypeServer;
import net.tiklab.matflow.definition.service.task.PipelineMessageUserServer;
import net.tiklab.matflow.definition.service.task.PipelineScriptServer;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineAfterConfigTaskServerImpl implements PipelineAfterConfigTaskServer{

    @Autowired
    private PipelineMessageTypeServer messageTypeServer;

    @Autowired
    private PipelineMessageUserServer messageUserServer;

    @Autowired
    private PipelineScriptServer scriptServer;

    /**
     * 配置信息
     * @param config 配置
     */
    @Override
    public void updateConfig(PipelineAfterConfig config){
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

    //消息
    private void createMessage(PipelineAfterConfig config){
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
    private void createScript(PipelineAfterConfig config){
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
     * 查询配置信息
     * @param config 配置类型
     * @return 配置
     */
    @Override
    public Object findOneConfig(PipelineAfterConfig config){
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
                throw new ApplicationException("未知的操作类型taskType："+ taskType);
            }
        }
    }

    /**
     * 删除配置
     * @param config 配置
     */
    public void deleteConfig(PipelineAfterConfig config){
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
                throw new ApplicationException("未知的操作类型taskType："+ taskType);
            }
        }
    }

}



















