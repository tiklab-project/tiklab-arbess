package com.tiklab.matflow.execute.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.model.MatFlowExecConfigure;
import com.tiklab.matflow.definition.service.MatFlowConfigureService;
import com.tiklab.matflow.execute.dao.MatFlowStructureDao;
import com.tiklab.matflow.execute.entity.MatFlowStructureEntity;
import com.tiklab.matflow.execute.model.MatFlowStructure;
import com.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Exporter
public class MatFlowStructureServiceImpl implements MatFlowStructureService {

    @Autowired
    MatFlowStructureDao matFlowStructureDao;

    @Autowired
    MatFlowDeployService matFlowDeployService;

    @Autowired
    MatFlowConfigureService matFlowConfigureService;

    //创建
    @Override
    public String createStructure(MatFlowStructure matFlowStructure) {
        return matFlowStructureDao.createStructure(BeanMapper.map(matFlowStructure, MatFlowStructureEntity.class));
    }

    @Override
    public String createConfigure(String matFlowId, int taskType, MatFlowStructure matFlowStructure){
        matFlowStructure.setType(taskType);
        MatFlowConfigure matFlowConfigure = new MatFlowConfigure();
        matFlowConfigure.setTaskAlias("构建");
        if (matFlowStructure.getStructureAlias() != null){
            matFlowConfigure.setTaskAlias(matFlowStructure.getStructureAlias());
        }
        String structureId = createStructure(matFlowStructure);
        matFlowConfigure.setTaskId(structureId);
        matFlowConfigure.setTaskType(taskType);
        matFlowConfigure.setTaskSort(matFlowStructure.getSort());
        //matFlowConfigure.setView(matFlowStructure.getView());
        matFlowConfigureService.createTask(matFlowConfigure,matFlowId);
        return structureId;
    }

    //删除
    @Override
    public void deleteStructure(String structureId) {
        matFlowStructureDao.deleteStructure(structureId);
    }

    @Override
    public void deleteTask(String taskId, int taskType) {
        if (taskType > 20 && taskType < 30){
            deleteStructure(taskId);
            return;
        }
        matFlowDeployService.deleteTask(taskId,taskType);
    }


    //修改
    @Override
    public void updateStructure(MatFlowStructure matFlowStructure) {
        matFlowStructureDao.updateStructure(BeanMapper.map(matFlowStructure, MatFlowStructureEntity.class));
    }

    //更改构建信息
    @Override
    public void updateTask(MatFlowExecConfigure matFlowExecConfigure) {
        MatFlowStructure matFlowStructure = matFlowExecConfigure.getMatFlowStructure();
        MatFlow matFlow = matFlowExecConfigure.getMatFlow();

        MatFlowConfigure oneConfigure = matFlowConfigureService.findOneConfigure(matFlow.getMatflowId(), 30);

        //判断新配置是否删除了构建配置
        if (oneConfigure != null && matFlowStructure.getType() == 0){
            deleteStructure(oneConfigure.getTaskId());
            matFlowConfigureService.deleteConfigure(oneConfigure.getConfigureId());
            matFlowDeployService.updateTask(matFlowExecConfigure);
            return;
        }
        if (oneConfigure == null ){
            oneConfigure = new MatFlowConfigure();
        }

        if (matFlowStructure.getType() == 0){
            matFlowDeployService.updateTask(matFlowExecConfigure);
            return;
        }

        oneConfigure.setTaskSort(matFlowStructure.getSort());
        oneConfigure.setTaskAlias(matFlowStructure.getStructureAlias());
        oneConfigure.setView(matFlowExecConfigure.getView());
        oneConfigure.setMatFlow(matFlow);
        oneConfigure.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        oneConfigure.setTaskType(matFlowStructure.getType());

        //存在构建配置，更新或者创建
        if (matFlowStructure.getStructureId() != null){
            updateStructure(matFlowStructure);
            matFlowConfigureService.updateConfigure(oneConfigure);
        }else {
            String testId = createStructure(matFlowStructure);
            oneConfigure.setTaskId(testId);
            matFlowConfigureService.createConfigure(oneConfigure);
        }
        matFlowDeployService.updateTask(matFlowExecConfigure);
    }


    //查询单个
    @Override
    public MatFlowStructure findOneStructure(String structureId) {
        return BeanMapper.map(matFlowStructureDao.findOneStructure(structureId), MatFlowStructure.class);
    }

    @Override
    public List<Object> findOneTask(MatFlowConfigure matFlowConfigure, List<Object> list) {
        if (matFlowConfigure.getTaskType() > 20 && matFlowConfigure.getTaskType() < 30){
            MatFlowStructure oneStructure = findOneStructure(matFlowConfigure.getTaskId());
            list.add(oneStructure);
        }
        return matFlowDeployService.findOneTask(matFlowConfigure,list);
    }

    //查询所有
    @Override
    public List<MatFlowStructure> findAllStructure() {
        return BeanMapper.mapList(matFlowStructureDao.findAllStructure(), MatFlowStructure.class);
    }

    @Override
    public List<MatFlowStructure> findAllStructureList(List<String> idList) {
        return BeanMapper.mapList(matFlowStructureDao.findAllCodeList(idList), MatFlowStructure.class);
    }
}
