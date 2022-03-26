package com.doublekit.pipeline.definition.service;


import com.doublekit.beans.BeanMapper;
import com.doublekit.join.JoinTemplate;
import com.doublekit.pipeline.definition.dao.PipelineConfigureDao;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.model.PipelineTest;
import com.doublekit.pipeline.example.service.PipelineCodeService;
import com.doublekit.pipeline.example.service.PipelineDeployService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Exporter
public class PipelineConfigureServiceImpl implements PipelineConfigureService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineConfigureDao pipelineConfigureDao;

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    PipelineDeployService pipelineDeployService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigureServiceImpl.class);

    //创建
    @Override
    public  Map<String, String> createConfigure(PipelineConfigure pipelineConfigure) {
        Map<String, String> map = pipelineCodeService.createTest(pipelineConfigure);
        pipelineConfigure.setCreateTime(pipelineConfigure.getPipeline().getPipelineCreateTime());
        PipelineCode pipelineCode = new PipelineCode();
        pipelineCode.setCodeId(map.get("codeId"));
        pipelineConfigure.setPipelineCode(pipelineCode);
        PipelineTest pipelineTest = new PipelineTest();
        pipelineTest.setTestId(map.get("testId"));
        pipelineConfigure.setPipelineTest(pipelineTest);
        PipelineStructure pipelineStructure = new PipelineStructure();
        pipelineStructure.setStructureId(map.get("structureId"));
        pipelineConfigure.setPipelineStructure(pipelineStructure);
        PipelineDeploy pipelineDeploy = new PipelineDeploy();
        pipelineDeploy.setDeployId(map.get("deployId"));
        pipelineConfigure.setPipelineDeploy(pipelineDeploy);
        String configure = pipelineConfigureDao.createConfigure(BeanMapper.map(pipelineConfigure, PipelineConfigureEntity.class));
        map.put("configureId",configure);
        return map;
    }

    //删除
    @Override
    public void deletePipelineIdConfigure(String pipelineId) {
        List<PipelineConfigure> allConfigure = findAllConfigure();
        for (PipelineConfigure pipelineConfigure : allConfigure) {
            if (pipelineConfigure.getPipeline().getPipelineId().equals(pipelineId)){
                PipelineConfigure oneConfigure = findOneConfigure(pipelineConfigure.getConfigureId());
                pipelineCodeService.deleteTest(oneConfigure);
                pipelineConfigureDao.deleteConfigure(pipelineConfigure.getConfigureId());
            }
        }
    }

    //更新
    @Override
    public void updateConfigure(PipelineConfigure pipelineConfigure) {
        pipelineCodeService.updateTest(pipelineConfigure);
        pipelineConfigureDao.updateConfigure(BeanMapper.map(pipelineConfigure,PipelineConfigureEntity.class));
    }

    //查询
    @Override
    public PipelineConfigure findOneConfigure(String configureId) {
        PipelineConfigureEntity oneConfigure = pipelineConfigureDao.findOneConfigure(configureId);
        joinTemplate.joinQuery(oneConfigure);
        return BeanMapper.map(oneConfigure,PipelineConfigure.class);
    }

    //根据流水线id查询配置
    @Override
    public PipelineConfigure findPipelineIdConfigure(String pipelineId) {
        List<PipelineConfigure> allConfigure = findAllConfigure();
        for (PipelineConfigure pipelineConfigure : allConfigure) {
            if (pipelineConfigure.getPipeline().getPipelineId().equals(pipelineId)){
                return pipelineConfigure;
            }
        }
        return null;
    }

    //获取code凭证
    @Override
    public Proof findCodeProof(PipelineConfigure pipelineConfigure) {
        return pipelineCodeService.findOneProof(pipelineConfigure);
    }

    @Override
    public Proof findDeployProof(PipelineConfigure pipelineConfigure) {
        return pipelineDeployService.findOneProof(pipelineConfigure);
    }

    @Override
    public PipelineExecHistory addHistoryOne(String pipelineId, PipelineExecHistory pipelineExecHistory) {
        return null;
    }

    //查询所有
    @Override
    public List<PipelineConfigure> findAllConfigure() {
        List<PipelineConfigureEntity> allConfigure = pipelineConfigureDao.findAllConfigure();
        List<PipelineConfigure> pipelineConfigureList = BeanMapper.mapList(allConfigure, PipelineConfigure.class);
        joinTemplate.joinQuery(pipelineConfigureList);
        return pipelineConfigureList;
    }

    @Override
    public List<PipelineConfigure> findAllConfigureList(List<String> idList) {
        List<PipelineConfigureEntity> pipelineConfigureEntityList = pipelineConfigureDao.findAllConfigureList(idList);
        return BeanMapper.mapList(pipelineConfigureEntityList, PipelineConfigure.class);
    }


}
