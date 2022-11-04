package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.dao.PipelineAuthCodeScanDao;
import net.tiklab.matflow.setting.entity.PipelineAuthCodeScanEntity;
import net.tiklab.matflow.setting.model.PipelineAuthCodeScan;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineAuthCodeScanServerImpl implements PipelineAuthCodeScanServer {

    @Autowired
    PipelineAuthCodeScanDao authCodeScanDao;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 创建流水线授权
     * @param pipelineAuthCodeScan 流水线授权
     * @return 流水线授权id
     */
    @Override
    public String createAuthCodeScan(PipelineAuthCodeScan pipelineAuthCodeScan) {
        PipelineAuthCodeScanEntity pipelineAuthCodeScanEntity = BeanMapper.map(pipelineAuthCodeScan, PipelineAuthCodeScanEntity.class);
        return authCodeScanDao.createAuthCodeScan(pipelineAuthCodeScanEntity);
    }

    /**
     * 删除流水线授权
     * @param authCodeScanId 流水线授权id
     */
    @Override
    public void deleteAuthCodeScan(String authCodeScanId) {
        authCodeScanDao.deleteAuthCodeScan(authCodeScanId);
    }

    /**
     * 更新授权信息
     * @param pipelineAuthCodeScan 信息
     */
    @Override
    public void updateAuthCodeScan(PipelineAuthCodeScan pipelineAuthCodeScan) {
        PipelineAuthCodeScanEntity authCodeScanEntity = BeanMapper.map(pipelineAuthCodeScan, PipelineAuthCodeScanEntity.class);
        authCodeScanDao.updateAuthCodeScan(authCodeScanEntity);
    }

    /**
     * 查询授权信息
     * @param authCodeScanId id
     * @return 信息集合
     */
    @Override
    public PipelineAuthCodeScan findOneAuthCodeScan(String authCodeScanId) {
        PipelineAuthCodeScanEntity oneAuthCodeScan = authCodeScanDao.findOneAuthCodeScan(authCodeScanId);
        PipelineAuthCodeScan pipelineAuthCodeScan = BeanMapper.map(oneAuthCodeScan, PipelineAuthCodeScan.class);
        joinTemplate.joinQuery(pipelineAuthCodeScan);
        return pipelineAuthCodeScan;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineAuthCodeScan> findAllAuthCodeScan() {
        List<PipelineAuthCodeScanEntity> allAuthCodeScan = authCodeScanDao.findAllAuthCodeScan();
        List<PipelineAuthCodeScan> pipelineAuthCodeScans = BeanMapper.mapList(allAuthCodeScan, PipelineAuthCodeScan.class);
        joinTemplate.joinQuery(pipelineAuthCodeScans);
        return pipelineAuthCodeScans;
    }

    @Override
    public List<PipelineAuthCodeScan> findAllAuthCodeScanList(List<String> idList) {
        List<PipelineAuthCodeScanEntity> allAuthCodeScanList = authCodeScanDao.findAllAuthCodeScanList(idList);
        return  BeanMapper.mapList(allAuthCodeScanList, PipelineAuthCodeScan.class);
    }
    
    
}
