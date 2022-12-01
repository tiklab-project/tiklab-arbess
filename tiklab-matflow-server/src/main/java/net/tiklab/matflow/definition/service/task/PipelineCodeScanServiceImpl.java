package net.tiklab.matflow.definition.service.task;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.dao.task.PipelineCodeScanDao;
import net.tiklab.matflow.definition.entity.task.PipelineCodeScanEntity;
import net.tiklab.matflow.definition.model.task.PipelineCodeScan;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.setting.service.PipelineAuthThirdServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineCodeScanServiceImpl implements PipelineCodeScanService {


    @Autowired
    PipelineCodeScanDao CodeScanDao;

    @Autowired
    PipelineAuthThirdServer thirdServer;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 创建流水线代码扫描
     * @param pipelineCodeScan 流水线代码扫描
     * @return 流水线代码扫描id
     */
    @Override
    public String createCodeScan(PipelineCodeScan pipelineCodeScan) {
        PipelineCodeScanEntity pipelineCodeScanEntity = BeanMapper.map(pipelineCodeScan, PipelineCodeScanEntity.class);
        return CodeScanDao.createCodeScan(pipelineCodeScanEntity);
    }

    /**
     * 删除流水线代码扫描
     * @param CodeScanId 流水线代码扫描id
     */
    @Override
    public void deleteCodeScan(String CodeScanId) {
        CodeScanDao.deleteCodeScan(CodeScanId);
    }

    /**
     * 更新代码扫描信息
     * @param pipelineCodeScan 信息
     */
    @Override
    public void updateCodeScan(PipelineCodeScan pipelineCodeScan) {
        PipelineCodeScanEntity codeScanEntity = BeanMapper.map(pipelineCodeScan, PipelineCodeScanEntity.class);
        CodeScanDao.updateCodeScan(codeScanEntity);
    }

    /**
     * 查询代码扫描信息
     * @param CodeScanId id
     * @return 信息集合
     */
    @Override
    public PipelineCodeScan findOneCodeScan(String CodeScanId) {
        PipelineCodeScanEntity oneCodeScan = CodeScanDao.findOneCodeScan(CodeScanId);
        PipelineCodeScan codeScan = BeanMapper.map(oneCodeScan, PipelineCodeScan.class);
        if (PipelineUntil.isNoNull(codeScan.getAuthId())){
            PipelineAuthThird authServer = thirdServer.findOneAuthServer(codeScan.getAuthId());
            codeScan.setAuth(authServer);
        }
        joinTemplate.joinQuery(codeScan);
        return codeScan;
    }

    /**
     * 查询所有流水线代码扫描
     * @return 流水线代码扫描列表
     */
    @Override
    public List<PipelineCodeScan> findAllCodeScan() {
        List<PipelineCodeScanEntity> allCodeScan = CodeScanDao.findAllCodeScan();
        return BeanMapper.mapList(allCodeScan, PipelineCodeScan.class);
    }

    @Override
    public List<PipelineCodeScan> findAllCodeScanList(List<String> idList) {
        List<PipelineCodeScanEntity> allCodeScanList = CodeScanDao.findAllCodeScanList(idList);
        List<PipelineCodeScan> pipelineCodeScans = BeanMapper.mapList(allCodeScanList, PipelineCodeScan.class);
        joinTemplate.joinQuery(pipelineCodeScans);
        return pipelineCodeScans;
    }


}
