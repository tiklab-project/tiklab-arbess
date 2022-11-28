package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineMessageDao;
import net.tiklab.matflow.definition.entity.PipelineMessageEntity;
import net.tiklab.matflow.definition.model.PipelineMessage;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineMessageServerImpl implements PipelineMessageServer {

    @Autowired
    private PipelineMessageDao pipelineMessageDao;

    //创建
    @Override
    public String createMessage(PipelineMessage pipelineMessage) {
        pipelineMessage.setMessageType("site");
        PipelineMessageEntity pipelineMessageEntity = BeanMapper.map(pipelineMessage, PipelineMessageEntity.class);
        return pipelineMessageDao.createMessage(pipelineMessageEntity);
    }

    //删除
    @Override
    public void deleteMessage(String messageId) {
        pipelineMessageDao.deleteMessage(messageId);
    }

    //更新
    @Override
    public void updateMessage(PipelineMessage pipelineMessage) {
        List<String> typeList = pipelineMessage.getTypeList();
        StringBuilder type = new StringBuilder();
        for (String s : typeList) {
            type.append("\n").append(s);
        }
        pipelineMessage.setMessageType(type.toString());
        PipelineMessageEntity pipelineMessageEntity = BeanMapper.map(pipelineMessage, PipelineMessageEntity.class);
        pipelineMessageDao.updateMessage(pipelineMessageEntity);
    }

    //查询单个
    @Override
    public PipelineMessage findOneMessage(String messageId) {
        PipelineMessageEntity oneMessage = pipelineMessageDao.findOneMessage(messageId);
        String messageType = oneMessage.getMessageType();
        PipelineMessage message = BeanMapper.map(oneMessage, PipelineMessage.class);
        List<String> list = new ArrayList<>();
        for (String s : messageType.split("\n")) {
            if (!PipelineUntil.isNoNull(s)){
                continue;
            }
            list.add(s);
        }
        message.setTypeList(list);
        return message;
    }

    //查询所有
    @Override
    public List<PipelineMessage> findAllMessage() {
        return BeanMapper.mapList(pipelineMessageDao.findAllMessage(), PipelineMessage.class);
    }

    @Override
    public List<PipelineMessage> findAllMessageList(List<String> idList) {
        return BeanMapper.mapList(pipelineMessageDao.findAllCodeList(idList), PipelineMessage.class);
    }
    
    
}
