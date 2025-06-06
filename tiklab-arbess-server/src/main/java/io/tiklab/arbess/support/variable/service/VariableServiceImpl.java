package io.tiklab.arbess.support.variable.service;

import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.model.VariableQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.support.variable.dao.VariableDao;
import io.tiklab.arbess.support.variable.entity.VariableEntity;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VariableServiceImpl implements VariableService {

    @Autowired
    VariableDao variableDao;


    @Override
    public String replaceVariable(String pipelineId,String taskId,String order){
        Map<String , String > map = new HashMap<>();

        //替换局部变量
        VariableQuery variableQuery = new VariableQuery();
        variableQuery.setTaskId(taskId);
        List<Variable> variableList = findVariableList(variableQuery);
        if (!variableList.isEmpty()){
            for (Variable variable : variableList) {
                String varValue = variable.getVarValue();
                String varKey = variable.getVarKey();
                map.put(varKey,varValue);
            }
        }

        //替换全局变量
        variableQuery.setTaskId(null);
        variableQuery.setPipelineId(pipelineId);
        List<Variable> allVariable = findVariableList(variableQuery);
        if (!allVariable.isEmpty()){
            for (Variable variable : allVariable) {
                String varKey = variable.getVarKey();
                String varValue = variable.getVarValue();
                map.put(varKey,varValue);
            }
        }
        StrSubstitutor substitutor = new StrSubstitutor(map);
        return substitutor.replace(order);
    }

    @Override
    public String createVariable(Variable variable) {
        variable.setCreateTime(PipelineUtil.date(1));
        return  variableDao.createVariable(variable);
    }

    @Override
    public void deleteVariable(String varId) {
        variableDao.deleteVariable(varId);
    }

    @Override
    public void updateVariable(Variable variable) {
        variableDao.updateVariable(variable);
    }

    @Override
    public Variable findOneVariable(String varId) {
        return variableDao.findOneVariable(varId);
    }


    @Override
    public List<Variable> findAllVariable() {
        List<Variable> allVariable = variableDao.findAllVariable();
        if (allVariable == null || allVariable.isEmpty()){
            return new ArrayList<>();
        }
        return allVariable;
    }

    @Override
    public List<Variable> findVariableList(VariableQuery query){
        List<VariableEntity> variableList = variableDao.findVariableList(query);
        if (variableList == null || variableList.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(variableList, Variable.class);
    }


    @Override
    public Pagination<Variable> findVariablePage(VariableQuery query){
        Pagination<VariableEntity> variablePage = variableDao.findVariablePage(query);

        List<VariableEntity> dataList = variablePage.getDataList();

        if (dataList == null || dataList.isEmpty()){
            return PaginationBuilder.build(variablePage, new ArrayList<>());
        }
        List<Variable> variables = BeanMapper.mapList(dataList, Variable.class);
        return PaginationBuilder.build(variablePage,variables);
    }


    @Override
    public void cloneVariable(String id,String cloneId){
        VariableQuery variableQuery = new VariableQuery();
        variableQuery.setTaskId(id);
        List<Variable> variableList = findVariableList(variableQuery);
        for (Variable variable : variableList) {
            variable.setTaskId(cloneId);
            createVariable(variable);
        }
    }

}































