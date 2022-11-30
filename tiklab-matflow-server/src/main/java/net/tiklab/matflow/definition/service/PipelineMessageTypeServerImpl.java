package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineMessageTypeDao;
import net.tiklab.matflow.definition.entity.PipelineMessageTypeEntity;
import net.tiklab.matflow.definition.model.PipelineMessageType;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineMessageTypeServerImpl implements PipelineMessageTypeServer {

    @Autowired
    private PipelineMessageTypeDao pipelineMessageTypeDao;

    //创建
    @Override
    public String createMessage(PipelineMessageType pipelineMessageType) {
        PipelineMessageTypeEntity pipelineMessageTypeEntity = BeanMapper.map(pipelineMessageType, PipelineMessageTypeEntity.class);
        return pipelineMessageTypeDao.createMessage(pipelineMessageTypeEntity);
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
        return BeanMapper.mapList(pipelineMessageTypeDao.findAllCodeList(idList), PipelineMessageType.class);
    }
    
    
}
