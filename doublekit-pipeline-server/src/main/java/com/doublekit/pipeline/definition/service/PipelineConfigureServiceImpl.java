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
        String configureId = pipelineConfigureDao.createConfigure(BeanMapper.map(pipelineConfigure, PipelineConfigureEntity.class));
        PipelineCode pipelineCode = new PipelineCode();
        pipelineConfigure.setPipelineCode(pipelineCode);
        PipelineStructure pipelineStructure = new PipelineStructure();
        pipelineConfigure.setPipelineStructure(pipelineStructure);
        PipelineTest pipelineTest = new PipelineTest();
        pipelineConfigure.setPipelineTest(pipelineTest);
        PipelineDeploy pipelineDeploy = new PipelineDeploy();
        pipelineConfigure.setPipelineDeploy(pipelineDeploy);
        Map<String, String> map = pipelineCodeService.createTest(pipelineConfigure);
        map.put("configureId",configureId);
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
