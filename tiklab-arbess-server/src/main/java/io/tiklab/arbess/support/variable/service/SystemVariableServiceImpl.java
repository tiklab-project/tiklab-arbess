package io.tiklab.arbess.support.variable.service;

import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.support.variable.dao.SystemVariableDao;
import io.tiklab.arbess.support.variable.entity.SystemVariableEntity;
import io.tiklab.arbess.support.variable.model.SystemVariable;
import io.tiklab.arbess.support.variable.model.SystemVariableQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemVariableServiceImpl implements SystemVariableService {

    @Autowired
    SystemVariableDao systemVariableDao;

    
    @Override
    public String createSystemVariable(SystemVariable systemVariable) {
        systemVariable.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return  systemVariableDao.createSystemVariable(systemVariable);
    }

    @Override
    public void deleteSystemVariable(String varId) {
        systemVariableDao.deleteSystemVariable(varId);
    }

    @Override
    public void updateSystemVariable(SystemVariable systemVariable) {
        systemVariableDao.updateSystemVariable(systemVariable);
    }

    @Override
    public SystemVariable findSystemVariable(String varId) {
        return systemVariableDao.findOneSystemVariable(varId);
    }


    @Override
    public List<SystemVariable> findAllSystemVariable() {
        List<SystemVariable> allSystemVariable = systemVariableDao.findAllSystemVariable();
        if (allSystemVariable == null || allSystemVariable.isEmpty()){
            return new ArrayList<>();
        }
        return allSystemVariable;
    }

    @Override
    public List<SystemVariable> findSystemVariableList(SystemVariableQuery query){
        List<SystemVariableEntity> systemVariableList = systemVariableDao.findSystemVariableList(query);
        if (systemVariableList == null || systemVariableList.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(systemVariableList, SystemVariable.class);
    }


    @Override
    public Pagination<SystemVariable> findSystemVariablePage(SystemVariableQuery query){
        Pagination<SystemVariableEntity> systemVariablePage = systemVariableDao.findSystemVariablePage(query);

        List<SystemVariableEntity> dataList = systemVariablePage.getDataList();

        if (dataList == null || dataList.isEmpty()){
            return PaginationBuilder.build(systemVariablePage, new ArrayList<>());
        }
        List<SystemVariable> systemVariables = BeanMapper.mapList(dataList, SystemVariable.class);
        return PaginationBuilder.build(systemVariablePage,systemVariables);
    }
}




