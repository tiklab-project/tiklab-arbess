package io.tiklab.arbess.support.variable.service;

import io.tiklab.arbess.support.variable.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VariableRunServiceImpl implements VariableRunService {

    @Autowired
    VariableService variableService;

    @Autowired
    SystemVariableService systemVariableService;


    @Override
    public List<ExecVariable> findPipelineVariable(String pipelineId){

        Map<String, ExecVariable> systemVariableMap = findSystemVariableMap();
        Map<String, ExecVariable> pipelineVariableMap = findPipelineVariableMap(pipelineId);

        systemVariableMap.putAll(pipelineVariableMap);

        return systemVariableMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExecVariable> findTaskVariable(String pipelineId,String taskId){

        Map<String, ExecVariable> systemVariableMap = findSystemVariableMap();
        Map<String, ExecVariable> pipelineVariableMap = findPipelineVariableMap(pipelineId);
        Map<String, ExecVariable> taskVariableMap = findTaskVariableMap(taskId);

        systemVariableMap.putAll(pipelineVariableMap);
        systemVariableMap.putAll(taskVariableMap);

        return systemVariableMap.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
        }


    /**
     * 查询系统变量
     * @return 系统变量
     */
    private Map<String,ExecVariable> findSystemVariableMap(){
        Map<String,ExecVariable> execVariableMap = new HashMap<>();
        SystemVariableQuery systemVariableQuery = new SystemVariableQuery();
        systemVariableQuery.setType(2);
        List<SystemVariable> systemVariableList = systemVariableService.findSystemVariableList(systemVariableQuery);
        if (!systemVariableList.isEmpty()){
            for (SystemVariable systemVariable : systemVariableList) {
                String varKey = systemVariable.getVarKey();
                String value = systemVariable.getVarValue();
                execVariableMap.put(varKey,new ExecVariable(varKey,value));
            }
        }
        return execVariableMap;
    }

    /**
     * 查询流水线变量
     * @param pipelineId 流水线ID
     * @return 流水线变量
     */
    private Map<String,ExecVariable> findPipelineVariableMap(String pipelineId){
        Map<String,ExecVariable> execVariableMap = new HashMap<>();
        VariableQuery variableQuery = new VariableQuery();
        variableQuery.setPipelineId(pipelineId);
        List<Variable> variableList = variableService.findVariableList(variableQuery);
        if (!variableList.isEmpty()){
            for (Variable variable : variableList) {
                String varKey = variable.getVarKey();
                String value = variable.getVarValue();
                execVariableMap.put(varKey,new ExecVariable(varKey,value));
            }
        }
        return execVariableMap;
    }

    /**
     * 查询任务变量
     * @param taskId 任务ID
     * @return 任务变量
     */
    private Map<String,ExecVariable> findTaskVariableMap(String taskId){
        Map<String,ExecVariable> execVariableMap = new HashMap<>();
        VariableQuery variableQuery = new VariableQuery();
        variableQuery.setTaskId(taskId);
        List<Variable> variableList = variableService.findVariableList(variableQuery);
        if (!variableList.isEmpty()){
            for (Variable variable : variableList) {
                String varKey = variable.getVarKey();
                String value = variable.getVarValue();
                execVariableMap.put(varKey,new ExecVariable(varKey,value));
            }
        }
        return execVariableMap;
    }



}































