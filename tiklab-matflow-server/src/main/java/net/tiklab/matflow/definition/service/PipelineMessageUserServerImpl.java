package net.tiklab.matflow.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.definition.dao.PipelineMessageUserDao;
import net.tiklab.matflow.definition.entity.PipelineMessageUserEntity;
import net.tiklab.matflow.definition.model.PipelineMessageUser;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineMessageUserServerImpl implements PipelineMessageUserServer  {

    @Autowired
    private PipelineMessageUserDao pipelineMessageUserDao;

    //创建
    @Override
    public String createMessage(PipelineMessageUser pipelineMessageUser) {
        PipelineMessageUserEntity pipelineMessageUserEntity = BeanMapper.map(pipelineMessageUser, PipelineMessageUserEntity.class);
        return pipelineMessageUserDao.createMessage(pipelineMessageUserEntity);
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
        return BeanMapper.mapList(pipelineMessageUserDao.findAllCodeList(idList), PipelineMessageUser.class);
    }
    
    
}
