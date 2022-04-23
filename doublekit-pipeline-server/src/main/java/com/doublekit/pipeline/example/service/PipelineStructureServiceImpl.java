package com.doublekit.pipeline.example.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.model.PipelineExecConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.dao.PipelineStructureDao;
import com.doublekit.pipeline.example.entity.PipelineStructureEntity;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String createConfigure( String pipelineId,int taskType,PipelineStructure pipelineStructure ){
        pipelineStructure.setType(taskType);
        PipelineConfigure pipelineConfigure = new PipelineConfigure();
        pipelineConfigure.setTaskAlias("构建");
        if (pipelineStructure.getStructureAlias() != null){
            pipelineConfigure.setTaskAlias(pipelineStructure.getStructureAlias());
        }
        String structureId = createStructure(pipelineStructure);
        pipelineConfigure.setTaskId(structureId);
        pipelineConfigure.setTaskType(taskType);
        pipelineConfigure.setTaskSort(pipelineStructure.getSort());
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
    public void updateTask(PipelineExecConfigure pipelineExecConfigure) {
        PipelineStructure pipelineStructure = pipelineExecConfigure.getPipelineStructure();
        String pipelineId = pipelineExecConfigure.getPipelineId();
        PipelineConfigure oneConfigure = pipelineConfigureService.findOneConfigure(pipelineId, 30);
        if (oneConfigure != null){
            if (pipelineStructure.getType() != 0){
                updateStructure(pipelineStructure);
                oneConfigure.setTaskSort(pipelineStructure.getSort());
                oneConfigure.setTaskAlias(pipelineStructure.getStructureAlias());
                pipelineConfigureService.updateConfigure(oneConfigure);
            }else {
                pipelineConfigureService.deleteTask(oneConfigure.getTaskId(),pipelineId);
            }
        }
        if (oneConfigure == null && pipelineStructure.getType() != 0){
            createConfigure(pipelineId,pipelineStructure.getType(),pipelineStructure);
        }
        pipelineDeployService.updateTask(pipelineExecConfigure);
    }


    //查询单个
    @Override
    public PipelineStructure findOneStructure(String structureId) {
        return BeanMapper.map(pipelineStructureDao.findOneStructure(structureId),PipelineStructure.class);
    }

    @Override
    public List<Object> findOneTask(PipelineConfigure pipelineConfigure, List<Object> list) {
        if (pipelineConfigure.getTaskType() > 20 && pipelineConfigure.getTaskType() < 30){
            PipelineStructure oneStructure = findOneStructure(pipelineConfigure.getTaskId());
            list.add(oneStructure);
        }
        return pipelineDeployService.findOneTask(pipelineConfigure,list);
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
