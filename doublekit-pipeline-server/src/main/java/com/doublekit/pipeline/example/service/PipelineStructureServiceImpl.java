package com.doublekit.pipeline.example.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.controller.PipelineStructureController;
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

    @Autowired
    PipelineConfigureService pipelineConfigureService;


    //创建
    @Override
    public String createStructure(PipelineStructure pipelineStructure) {
        return pipelineStructureDao.createStructure(BeanMapper.map(pipelineStructure, PipelineStructureEntity.class));
    }

    @Override
    public String createConfigure( String pipelineId,int taskType){
        PipelineStructure pipelineStructure = new PipelineStructure();
        pipelineStructure.setType(taskType);
        pipelineStructure.setType(taskType);
        PipelineConfigure pipelineConfigure = new PipelineConfigure();
        String structureId = createStructure(pipelineStructure);
        pipelineConfigure.setTaskId(structureId);
        pipelineConfigure.setTaskType(taskType);
        pipelineConfigureService.createTask(pipelineConfigure,pipelineId);
        return structureId;
    }

    //删除
    @Override
    public void deleteStructure(String structureId) {
        pipelineStructureDao.deleteStructure(structureId);
    }

    @Override
    public void deleteTask(String taskId, int taskType) {
        if (taskType > 20 && taskType < 30){
            deleteStructure(taskId);
            return;
        }
        pipelineDeployService.deleteTask(taskId,taskType);
    }


    //修改
    @Override
    public void updateStructure(PipelineStructure pipelineStructure) {
        pipelineStructureDao.updateStructure(BeanMapper.map(pipelineStructure,PipelineStructureEntity.class));
    }

    //更改构建信息
    @Override
    public void updateTask(Map<String,Object> map) {
        PipelineStructure pipelineStructure = (PipelineStructure) map.get("pipelineStructure");
        String pipelineId = map.get("pipelineId").toString();
        PipelineConfigure oneConfigure = pipelineConfigureService.findOneConfigure(pipelineId, 30);
        if (oneConfigure != null){
            if (pipelineStructure.getType() != 0){
                updateStructure(pipelineStructure);
            }else {
                pipelineConfigureService.deleteTask(oneConfigure.getTaskId(),pipelineId);
            }
        }
        if (oneConfigure == null && pipelineStructure.getType() != 0){
            createConfigure(pipelineId,pipelineStructure.getType());
        }
        pipelineDeployService.updateTask(map);
    }


    //查询单个
    @Override
    public PipelineStructure findOneStructure(String structureId) {
        return BeanMapper.map(pipelineStructureDao.findOneStructure(structureId),PipelineStructure.class);
    }

    @Override
    public List<Object>  findOneTask(PipelineConfigure pipelineConfigure, List<Object> list) {
        if (pipelineConfigure.getTaskType() > 20 && pipelineConfigure.getTaskType() < 30){
            PipelineStructure oneStructure = findOneStructure(pipelineConfigure.getTaskId());
            list.add(oneStructure);
        }
        if (pipelineConfigure.getTaskType() > 30){
            PipelineDeploy oneDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
            list.add(oneDeploy);
        }
        return list;
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
