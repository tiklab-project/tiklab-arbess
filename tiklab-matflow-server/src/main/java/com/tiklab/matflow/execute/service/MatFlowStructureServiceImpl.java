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

    //创建
    @Override
    public String createStructure(MatFlowStructure matFlowStructure) {
        return matFlowStructureDao.createStructure(BeanMapper.map(matFlowStructure, MatFlowStructureEntity.class));
    }

    //删除
    @Override
    public void deleteStructure(String structureId) {
        matFlowStructureDao.deleteStructure(structureId);
    }

    //修改
    @Override
    public void updateStructure(MatFlowStructure matFlowStructure) {
        matFlowStructureDao.updateStructure(BeanMapper.map(matFlowStructure, MatFlowStructureEntity.class));
    }

    //查询单个
    @Override
    public MatFlowStructure findOneStructure(String structureId) {
        return BeanMapper.map(matFlowStructureDao.findOneStructure(structureId), MatFlowStructure.class);
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
