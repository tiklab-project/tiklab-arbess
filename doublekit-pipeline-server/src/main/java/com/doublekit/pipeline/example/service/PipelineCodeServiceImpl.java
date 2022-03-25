package com.doublekit.pipeline.example.service;


import com.doublekit.beans.BeanMapper;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.dao.PipelineCodeDao;
import com.doublekit.pipeline.example.entity.PipelineCodeEntity;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineCodeServiceImpl implements PipelineCodeService {

    @Autowired
    PipelineCodeDao pipelineCodeDao;

    @Autowired
    ProofService proofService;

    @Autowired
    PipelineTestService pipelineTestService;


    //创建
    @Override
    public String createCode(PipelineCode pipelineCode) {
        return pipelineCodeDao.createCode(BeanMapper.map(pipelineCode, PipelineCodeEntity.class));
    }

    //创建test表
    @Override
    public Map<String, String> createTest(PipelineConfigure pipelineConfigure){
        Map<String, String> map = pipelineTestService.createStructure(pipelineConfigure);
        PipelineCode pipelineCode = new PipelineCode();
        if (pipelineConfigure.getPipelineCode() != null){
            pipelineCode = pipelineConfigure.getPipelineCode();
        }
        String code = createCode(pipelineCode);
        map.put("codeId",code);
        return map;
    }

    //删除
    @Override
    public void deleteCode(String codeId) {
        pipelineCodeDao.deleteCode(codeId);
    }

    //删除测试表
    @Override
    public void deleteTest(PipelineConfigure pipelineConfigure){
        deleteCode(pipelineConfigure.getPipelineCode().getCodeId());
        pipelineTestService.deleteStructure(pipelineConfigure);
    }

    //修改
    @Override
    public void updateCode(PipelineCode pipelineCode) {
        pipelineCodeDao.updateCode(BeanMapper.map(pipelineCode,PipelineCodeEntity.class));
    }

    //修改测试表
    @Override
    public void updateTest(PipelineConfigure pipelineConfigure){
        updateCode(pipelineConfigure.getPipelineCode());
        pipelineTestService.updateStructure(pipelineConfigure);
    }

    //查询单个
    @Override
    public PipelineCode findOneCode(String codeId) {
        PipelineCodeEntity oneCode = pipelineCodeDao.findOneCode(codeId);
        return  BeanMapper.map(oneCode, PipelineCode.class);
    }

    @Override
    public Proof findOneProof(PipelineConfigure pipelineConfigure){
        String proofName = pipelineConfigure.getPipelineCode().getProofName();
        return proofService.fondOneName(proofName);
    }

    //查询所有
    @Override
    public List<PipelineCode> findAllCode() {
        return BeanMapper.mapList(pipelineCodeDao.findAllCode(), PipelineCode.class);
    }

    @Override
    public List<PipelineCode> findAllCodeList(List<String> idList) {
        return BeanMapper.mapList(pipelineCodeDao.findAllCodeList(idList), PipelineCode.class);
    }
}


