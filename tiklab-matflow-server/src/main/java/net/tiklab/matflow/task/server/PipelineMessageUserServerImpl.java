package net.tiklab.matflow.task.server;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.task.dao.PipelineMessageUserDao;
import net.tiklab.matflow.task.entity.PipelineMessageUserEntity;
import net.tiklab.matflow.task.model.PipelineMessageUser;
import net.tiklab.matflow.task.model.PipelineUserMessage;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineMessageUserServerImpl implements PipelineMessageUserServer {

    @Autowired
    private PipelineMessageUserDao pipelineMessageUserDao;

    @Autowired
    private JoinTemplate joinTemplate;

    //创建
    @Override
    public String createMessage(PipelineMessageUser pipelineMessageUser) {
        PipelineMessageUserEntity pipelineMessageUserEntity = BeanMapper.map(pipelineMessageUser, PipelineMessageUserEntity.class);
        return pipelineMessageUserDao.createMessage(pipelineMessageUserEntity);
    }

    /**
     * 添加所有接收人
     * @param userMessages 接收人信息
     * @param taskId 任务id
     */
    public void createAllMessage(List<PipelineUserMessage> userMessages,String taskId){
        if (userMessages == null){
            return;
        }
        for (PipelineUserMessage userMessage : userMessages) {
            User user = userMessage.getUser();
            int type = userMessage.getMessageType();
            PipelineMessageUser messageUser = new PipelineMessageUser();
            messageUser.setReceiveType(type);
            messageUser.setUser(user);
            messageUser.setMessageTaskId(taskId);
            createMessage(messageUser);
        }
    }

    /**
     * 查询所有发送人
     * @param taskId 任务id
     * @return 发送人
     */
    public List<PipelineUserMessage> findAllUserMessage(String taskId){
        List<PipelineUserMessage> list = new ArrayList<>();
        for (PipelineMessageUser messageUser : findAllMessage()) {
            if (messageUser.getMessageTaskId().equals(taskId)){
                joinTemplate.joinQuery(messageUser);
                User user = messageUser.getUser();
                int receiveType = messageUser.getReceiveType();
                PipelineUserMessage pipelineUserMessage = new PipelineUserMessage();
                pipelineUserMessage.setMessageType(receiveType);
                pipelineUserMessage.setUser(user);
                list.add(pipelineUserMessage);
            }
        }
        return list;
    }

    /**
     * 删除任务
     * @param taskId 配置id
     */
    public void deleteAllMessage(String taskId){
        List<PipelineMessageUser> allMessage = findAllMessage();
        for (PipelineMessageUser messageUser : allMessage) {
            if (messageUser.getMessageTaskId().equals(taskId)){
                deleteMessage(messageUser.getMessageId());
            }
        }
    }

    //删除
    @Override
    public void deleteMessage(String messageId) {
        pipelineMessageUserDao.deleteMessage(messageId);
    }

    //更新
    @Override
    public void updateMessage(PipelineMessageUser pipelineMessageUser) {
        PipelineMessageUserEntity pipelineMessageUserEntity = BeanMapper.map(pipelineMessageUser, PipelineMessageUserEntity.class);
        pipelineMessageUserDao.updateMessage(pipelineMessageUserEntity);
    }

    //查询单个
    @Override
    public PipelineMessageUser findOneMessage(String messageId) {
        PipelineMessageUserEntity messageTypeEntity = pipelineMessageUserDao.findOneMessage(messageId);
        return BeanMapper.map(messageTypeEntity,PipelineMessageUser.class);

    }

    //查询所有
    @Override
    public List<PipelineMessageUser> findAllMessage() {
        return BeanMapper.mapList(pipelineMessageUserDao.findAllMessage(), PipelineMessageUser.class);
    }

    @Override
    public List<PipelineMessageUser> findAllMessageList(List<String> idList) {
        return BeanMapper.mapList(pipelineMessageUserDao.findAllMessageUserList(idList), PipelineMessageUser.class);
    }
    
    
}
