package com.doublekit.pipeline.example.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.dao.PipelineStructureDao;
import com.doublekit.pipeline.example.entity.PipelineStructureEntity;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.rpc.annotation.Exporter;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineStructureServiceImpl implements PipelineStructureService {

    @Autowired
    PipelineStructureDao pipelineStructureDao;

    @Autowired
    PipelineDeployService pipelineDeployService;


    //创建
    @Override
    public String createStructure(PipelineStructure pipelineStructure) {
        return pipelineStructureDao.createStructure(BeanMapper.map(pipelineStructure, PipelineStructureEntity.class));
    }

    public Map<String ,String> createDeploy(PipelineConfigure pipelineConfigure){
        Map<String, String> map = new HashMap<>();
        PipelineStructure pipelineStructure = new PipelineStructure();
        if (pipelineConfigure.getPipelineStructure() != null){
            pipelineStructure = pipelineConfigure.getPipelineStructure();
        }
        String structure = createStructure(pipelineStructure);
        PipelineDeploy pipelineDeploy = new PipelineDeploy();
        if (pipelineConfigure.getPipelineDeploy() != null){
            pipelineDeploy = pipelineConfigure.getPipelineDeploy();
        }
        String deploy = pipelineDeployService.createDeploy(pipelineDeploy);
        map.put("structureId",structure);
        map.put("deployId",deploy);
        return map;
    }

    //删除
    @Override
    public void deleteStructure(String structureId) {
        pipelineStructureDao.deleteStructure(structureId);
    }

    //删除部署信息
    public void deleteDeploy(PipelineConfigure pipelineConfigure){
        deleteStructure(pipelineConfigure.getPipelineStructure().getStructureId());
        pipelineDeployService.deleteDeploy(pipelineConfigure.getPipelineDeploy().getDeployId());
    }

    //修改
    @Override
    public void updateStructure(PipelineStructure pipelineStructure) {
        pipelineStructureDao.updateStructure(BeanMapper.map(pipelineStructure,PipelineStructureEntity.class));
    }

    //更新部署表
    @Override
    public void updateDeploy(PipelineConfigure pipelineConfigure){
        updateStructure(pipelineConfigure.getPipelineStructure());
        pipelineDeployService.updateDeploy(pipelineConfigure.getPipelineDeploy());
    }

    //查询单个
    @Override
    public PipelineStructure findOneStructure(String structureId) {
        return BeanMapper.map(pipelineStructureDao.findOneStructure(structureId),PipelineStructure.class);
    }

    //查询所有
    @Override
    public List<PipelineStructure> findAllStructure() {
        return BeanMapper.mapList(pipelineStructureDao.findAllStructure(),PipelineStructure.class);
    }

    @Override
    public List<PipelineStructure> findAllStructureList(List<String> idList) {
        return BeanMapper.mapList(pipelineStructureDao.findAllCodeList(idList),PipelineStructure.class);
    }
}
