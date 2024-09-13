package io.thoughtware.arbess.support.variable.service;

import io.thoughtware.arbess.support.util.util.PipelineUtil;
import io.thoughtware.arbess.support.variable.model.Variable;
import io.thoughtware.arbess.support.variable.model.VariableQuery;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.arbess.support.variable.dao.VariableDao;
import io.thoughtware.arbess.support.variable.entity.VariableEntity;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VariableServiceImpl implements VariableService {

    @Autowired
    VariableDao variableDao;


    public String replaceVariable(String pipelineId,String taskId,String order){
        Map<String , String > map = new HashMap<>();
        //替换全局变量
        List<Variable> allVariable = findAllVariable(pipelineId);
        if (!allVariable.isEmpty()){
            for (Variable variable : allVariable) {
                String varKey = variable.getVarKey();
                String varValue = variable.getVarValue();
                map.put(varKey,varValue);
            }
        }
        //替换局部变量
        List<Variable> variableList = findAllVariable(taskId);
        if (!variableList.isEmpty()){
            for (Variable variable : variableList) {
                String varValue = variable.getVarValue();
                String varKey = variable.getVarKey();
                map.put(varKey,varValue);
            }
        }

        StrSubstitutor substitutor = new StrSubstitutor(map);
        return substitutor.replace(order);
    }

    @Override
    public String createVariable(Variable variable) {
        String taskType = variable.getVarType();
        variable.setCreateTime(PipelineUtil.date(1));
        if (taskType.equals("single")){
            String values = updateValues(variable.getValueList());
            variable.setVarValues(values);
        }
        return  variableDao.createVariable(variable);
    }

    private String updateValues(List<String> list ){
        StringBuilder values = new StringBuilder();
        for (String s : list) {
            if (!PipelineUtil.isNoNull(values.toString())){
                values = new StringBuilder(s);
            }else {
                values.append(",").append(s);
            }
        }
        return values.toString();
    }

    /**
     * 删除变量
     * @param varId 变量id
     */
    @Override
    public void deleteVariable(String varId) {
        variableDao.deleteVariable(varId);
    }

    /**
     * 更新变量
     * @param variable 变量信息
     */
    @Override
    public void updateVariable(Variable variable) {
        String taskType = variable.getVarType();
        if (taskType.equals("single")){
            String values = updateValues(variable.getValueList());
            variable.setVarValues(values);
        }
        variableDao.updateVariable(variable);
    }

    /**
     * 查询单个变量
     * @param varId 变量id
     * @return 变量信息
     */
    @Override
    public Variable findOneVariable(String varId) {
        return variableDao.findOneVariable(varId);
    }

    /**
     * 查询所有变量
     * @return 变量集合
     */
    public List<Variable> findAllVariable() {
        List<Variable> allVariable = variableDao.findAllVariable();
        if (allVariable == null || allVariable.isEmpty()){
            return Collections.emptyList();
        }
        return allVariable;
    }

    /**
     * 查询流水线所有变量
     * @param taskId 流水线id
     * @return 变量
     */
    @Override
    public List<Variable> findAllVariable(String taskId) {
        List<Variable> allVariable = findAllVariable();
        if (allVariable.isEmpty()){
            return Collections.emptyList();
        }
        List<Variable> list = new ArrayList<>();
        for (Variable variable : allVariable) {
            String id = variable.getTaskId();
            if (id == null ||!id.equals(taskId)){
                continue;
            }
            if (variable.getVarType().equals("single")){
                String values = variable.getVarValues();
                String[] split = values.split(",");
                List<String> stringList = new ArrayList<>(List.of(split));
                variable.setValueList(stringList);
            }
            list.add(variable);
        }
        list.sort(Comparator.comparing(Variable::getCreateTime).reversed());
        return list;
    }

    @Override
    public List<Variable> findVariableList(VariableQuery query){
        List<VariableEntity> variableList = variableDao.findVariableList(query);
        if (variableList == null || variableList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(variableList, Variable.class);
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































