package com.tiklab.matflow.execute.service;

import com.doublekit.beans.BeanMapper;
import com.tiklab.matflow.definition.model.Pipeline;
import com.tiklab.matflow.definition.model.PipelineConfigure;
import com.tiklab.matflow.definition.model.PipelineExecConfigure;
import com.tiklab.matflow.definition.service.PipelineConfigureService;
import com.tiklab.matflow.execute.dao.PipelineStructureDao;
import com.tiklab.matflow.execute.entity.PipelineStructureEntity;
import com.tiklab.matflow.execute.model.PipelineStructure;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        //pipelineConfigure.setView(pipelineStructure.getView());
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
        Pipeline pipeline = pipelineExecConfigure.getPipeline();
        PipelineConfigure oneConfigure = pipelineConfigureService.findOneConfigure(pipeline.getPipelineId(), 30);

        //判断新配置是否删除了构建配置
        if (oneConfigure != null && pipelineStructure.getType() == 0){
            deleteStructure(oneConfigure.getTaskId());
            pipelineConfigureService.deleteConfigure(oneConfigure.getConfigureId());
            pipelineDeployService.updateTask(pipelineExecConfigure);
            return;
        }
        if (oneConfigure == null ){
            oneConfigure = new PipelineConfigure();
        }

        if (pipelineStructure.getType() == 0){
            pipelineDeployService.updateTask(pipelineExecConfigure);
            return;
        }

        oneConfigure.setTaskSort(pipelineStructure.getSort());
        oneConfigure.setTaskAlias(pipelineStructure.getStructureAlias());
        oneConfigure.setView(pipelineExecConfigure.getView());
        oneConfigure.setPipeline(pipeline);
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        oneConfigure.setTaskType(pipelineStructure.getType());

        //存在构建配置，更新或者创建
        if (pipelineStructure.getStructureId() != null){
            updateStructure(pipelineStructure);
            pipelineConfigureService.updateConfigure(oneConfigure);
        }else {
            String testId = createStructure(pipelineStructure);
            oneConfigure.setTaskId(testId);
            pipelineConfigureService.createConfigure(oneConfigure);
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
