package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.dao.PipelineAuthOtherDao;
import net.tiklab.matflow.setting.entity.PipelineAuthOtherEntity;
import net.tiklab.matflow.setting.model.PipelineAuthOther;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineAuthOtherServerImpl implements PipelineAuthOtherServer {

    @Autowired
    PipelineAuthOtherDao authOtherDao;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 创建流水线授权
     * @param pipelineAuthOther 流水线授权
     * @return 流水线授权id
     */
    @Override
    public String createAuthOther(PipelineAuthOther pipelineAuthOther) {
        PipelineAuthOtherEntity pipelineAuthOtherEntity = BeanMapper.map(pipelineAuthOther, PipelineAuthOtherEntity.class);
        return authOtherDao.createAuthOther(pipelineAuthOtherEntity);
    }

    /**
     * 删除流水线授权
     * @param authOtherId 流水线授权id
     */
    @Override
    public void deleteAuthOther(String authOtherId) {
        authOtherDao.deleteAuthOther(authOtherId);
    }

    /**
     * 更新授权信息
     * @param pipelineAuthOther 信息
     */
    @Override
    public void updateAuthOther(PipelineAuthOther pipelineAuthOther) {
        PipelineAuthOtherEntity authOtherEntity = BeanMapper.map(pipelineAuthOther, PipelineAuthOtherEntity.class);
        authOtherDao.updateAuthOther(authOtherEntity);
    }

    /**
     * 查询授权信息
     * @param authOtherId id
     * @return 信息集合
     */
    @Override
    public PipelineAuthOther findOneAuthOther(String authOtherId) {
        PipelineAuthOtherEntity oneAuthOther = authOtherDao.findOneAuthOther(authOtherId);
        PipelineAuthOther pipelineAuthOther = BeanMapper.map(oneAuthOther, PipelineAuthOther.class);
        joinTemplate.joinQuery(pipelineAuthOther);
        return pipelineAuthOther;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineAuthOther> findAllAuthOther() {
        List<PipelineAuthOtherEntity> allAuthOther = authOtherDao.findAllAuthOther();
        List<PipelineAuthOther> pipelineAuthOthers = BeanMapper.mapList(allAuthOther, PipelineAuthOther.class);
        joinTemplate.joinQuery(pipelineAuthOthers);
        return pipelineAuthOthers;
    }

    @Override
    public List<PipelineAuthOther> findAllAuthOtherList(List<String> idList) {
        List<PipelineAuthOtherEntity> allAuthOtherList = authOtherDao.findAllAuthOtherList(idList);
        return  BeanMapper.mapList(allAuthOtherList, PipelineAuthOther.class);
    }
    
    
}
