package net.tiklab.matflow.task.message.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.task.message.dao.PipelineMessageTypeDao;
import net.tiklab.matflow.task.message.entity.PipelineMessageTypeEntity;
import net.tiklab.matflow.task.message.model.PipelineMessage;
import net.tiklab.matflow.task.message.model.PipelineMessageType;
import net.tiklab.matflow.task.message.model.PipelineUserMessage;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineMessageTypeServiceImpl implements PipelineMessageTypeService {

    @Autowired
    private PipelineMessageTypeDao pipelineMessageTypeDao;
    
    @Autowired
    private PipelineMessageUserService messageUserServer;

    //创建
    @Override
    public String createMessage(PipelineMessageType pipelineMessageType) {
        PipelineMessageTypeEntity pipelineMessageTypeEntity = BeanMapper.map(pipelineMessageType, PipelineMessageTypeEntity.class);
        return pipelineMessageTypeDao.createMessage(pipelineMessageTypeEntity);
    }

    /**
     * 根据配置查询发送方式
     * @param configId 配置id
     * @return 发送方式
     */
    public List<String> findAllMessage(String configId){
        List<PipelineMessageType> allMessage = findAllMessage();
        if (allMessage == null){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (PipelineMessageType messageType : allMessage) {
            if (messageType.getConfigId().equals(configId)){
                list.add(messageType.getTaskType());
            }
        }
        return list;
    }

    /**
     * 查询消息发送信息
     * @param configId 配置id
     * @return 信息
     */
    public PipelineMessage findConfigMessage(String configId){
        List<String> allMessage = findAllMessage(configId);
        PipelineMessage message = new PipelineMessage();
        message.setTypeList(allMessage);
        PipelineMessageType messageType = findMessage(configId);
        if (messageType != null){
            String taskId = messageType.getMessageTaskId();
            List<PipelineUserMessage> allUserMessage = messageUserServer.findAllUserMessage(taskId);
            message.setUserList(allUserMessage);
        }
        message.setConfigId(configId);
        return message;
    }


    /**
     * 删除任务
     * @param configId 配置id
     */
    public void deleteAllMessage(String configId){
        List<PipelineMessageType> allMessage = findAllMessage();
        if (allMessage == null){
            return;
        }
        for (PipelineMessageType messageType : allMessage) {
            if (messageType.getConfigId().equals(configId)){
                String messageTaskId = messageType.getMessageTaskId();
                deleteMessage(messageType.getMessageTaskId());
                messageUserServer.deleteAllMessage(messageTaskId);
            }
        }

    }

    /**
     * 根据配置id查询消息类型
     * @param configId 配置id
     * @return 消息
     */
    public PipelineMessageType findMessage(String configId){
        List<PipelineMessageType> allMessage = findAllMessage();
        if (allMessage == null){
            return null;
        }

        for (PipelineMessageType messageType : allMessage) {
            if (messageType.getConfigId().equals(configId)){
               return messageType;
            }
        }
        return null;
    }


    //删除
    @Override
    public void deleteMessage(String messageId) {
        pipelineMessageTypeDao.deleteMessage(messageId);
    }

    //更新
    @Override
    public void updateMessage(PipelineMessageType pipelineMessageType) {
        PipelineMessageTypeEntity pipelineMessageTypeEntity = BeanMapper.map(pipelineMessageType, PipelineMessageTypeEntity.class);
        pipelineMessageTypeDao.updateMessage(pipelineMessageTypeEntity);
    }

    //查询单个
    @Override
    public PipelineMessageType findOneMessage(String messageId) {
        PipelineMessageTypeEntity messageTypeEntity = pipelineMessageTypeDao.findOneMessage(messageId);
        return BeanMapper.map(messageTypeEntity,PipelineMessageType.class);

    }

    //查询所有
    @Override
    public List<PipelineMessageType> findAllMessage() {
        return BeanMapper.mapList(pipelineMessageTypeDao.findAllMessage(), PipelineMessageType.class);
    }

    @Override
    public List<PipelineMessageType> findAllMessageList(List<String> idList) {
        return BeanMapper.mapList(pipelineMessageTypeDao.findAllMessageTypeList(idList), PipelineMessageType.class);
    }
    
    
}
