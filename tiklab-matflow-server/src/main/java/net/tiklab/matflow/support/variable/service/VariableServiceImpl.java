package net.tiklab.matflow.support.variable.service;

import net.tiklab.matflow.support.variable.dao.VariableDao;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.support.variable.model.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VariableServiceImpl implements VariableService {

    @Autowired
    VariableDao variableDao;

    /**
     * 获取全局以及任务变量
     * @param pipelineId 流水线id
     * @param taskId 任务id
     * @return 变量名称
     */
    public List<String> findAllTaskValues(String pipelineId,String taskId){
        //全局变量
        // LinkedList<String> list = new LinkedList<>();
        // Set
        List<Variable> allVariable = findAllVariable(pipelineId);
        if (allVariable.size() != 0){
            for (Variable variable : allVariable) {
                String varKey = variable.getVarKey();
                String varValue = variable.getVarValue();

            }
        }
        //局部变量
        List<Variable> variableList = findAllVariable(taskId);
        if (variableList.size() != 0){
            for (Variable variable : variableList) {
                String varValue = variable.getVarValue();
                String varKey = variable.getVarKey();

            }
        }

    return Collections.emptyList();
    }

    /**
     * 创建变量
     * @param variable 变量信息
     * @return 变量id
     */
    @Override
    public String createVariable(Variable variable) {
        int taskType = variable.getTaskType();
        variable.setCreateTime(PipelineUntil.date(1));
        if (taskType == 2){
            String values = updateValues(variable.getValueList());
            variable.setVarValues(values);
        }
        return  variableDao.createVariable(variable);
    }

    private String updateValues(List<String> list ){
        StringBuilder values = new StringBuilder();
        for (String s : list) {
            if (!PipelineUntil.isNoNull(values.toString())){
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
        int taskType = variable.getTaskType();
        if (taskType == 2){
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
        if (allVariable == null || allVariable.size() == 0){
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
        if (allVariable.size() == 0){
            return Collections.emptyList();
        }
        List<Variable> list = new ArrayList<>();
        for (Variable variable : allVariable) {
            String id = variable.getTaskId();
            if (id == null ||!id.equals(taskId)){
                continue;
            }
            if (variable.getTaskType()==2){
                String values = variable.getVarValues();
                String[] split = values.split(",");
                List<String> stringList = new ArrayList<>(List.of(split));
                variable.setValueList(stringList);
            }
            list.add(variable);
        }
        return list;
    }


}































