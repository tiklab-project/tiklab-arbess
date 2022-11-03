package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.dao.PipelineAuthBasicDao;
import net.tiklab.matflow.setting.entity.PipelineAuthBasicEntity;
import net.tiklab.matflow.setting.model.PipelineAuthBasic;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineAuthBasicServerImpl implements PipelineAuthBasicServer {

    @Autowired
    PipelineAuthBasicDao authBasicDao;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 创建流水线授权
     * @param pipelineAuthBasic 流水线授权
     * @return 流水线授权id
     */
    @Override
    public String createAuthBasic(PipelineAuthBasic pipelineAuthBasic) {
        PipelineAuthBasicEntity pipelineAuthBasicEntity = BeanMapper.map(pipelineAuthBasic, PipelineAuthBasicEntity.class);
        return authBasicDao.createAuthBasic(pipelineAuthBasicEntity);
    }

    /**
     * 删除流水线授权
     * @param authBasicId 流水线授权id
     */
    @Override
    public void deleteAuthBasic(String authBasicId) {
        authBasicDao.deleteAuthBasic(authBasicId);
    }

    /**
     * 更新授权信息
     * @param pipelineAuthBasic 信息
     */
    @Override
    public void updateAuthBasic(PipelineAuthBasic pipelineAuthBasic) {
        PipelineAuthBasicEntity authBasicEntity = BeanMapper.map(pipelineAuthBasic, PipelineAuthBasicEntity.class);
        authBasicDao.updateAuthBasic(authBasicEntity);
    }

    /**
     * 查询授权信息
     * @param authBasicId id
     * @return 信息集合
     */
    @Override
    public PipelineAuthBasic findOneAuthBasic(String authBasicId) {
        PipelineAuthBasicEntity oneAuthBasic = authBasicDao.findOneAuthBasic(authBasicId);
        PipelineAuthBasic pipelineAuthBasic = BeanMapper.map(oneAuthBasic, PipelineAuthBasic.class);
        joinTemplate.joinQuery(pipelineAuthBasic);
        return pipelineAuthBasic;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineAuthBasic> findAllAuthBasic() {
        List<PipelineAuthBasicEntity> allAuthBasic = authBasicDao.findAllAuthBasic();
        List<PipelineAuthBasic> pipelineAuthBasics = BeanMapper.mapList(allAuthBasic, PipelineAuthBasic.class);
        joinTemplate.joinQuery(pipelineAuthBasics);
        return pipelineAuthBasics;
    }

    @Override
    public List<PipelineAuthBasic> findAllAuthBasicList(List<String> idList) {
        List<PipelineAuthBasicEntity> allAuthBasicList = authBasicDao.findAllAuthBasicList(idList);
        return BeanMapper.mapList(allAuthBasicList, PipelineAuthBasic.class);
    }


}
