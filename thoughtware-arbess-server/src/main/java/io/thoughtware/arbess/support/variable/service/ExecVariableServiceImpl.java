package io.thoughtware.arbess.support.variable.service;

import io.thoughtware.arbess.support.variable.model.ExecVariable;
import io.thoughtware.arbess.support.variable.model.Variable;
import io.thoughtware.arbess.support.variable.model.VariableQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExecVariableServiceImpl implements ExecVariableService {


    // 流水线id  变量名 ，变量值
    private static final Map<String,List<ExecVariable>> variableMap = new HashMap<>();

    @Autowired
    VariableService variableService;


    public void initPipelineVariable(String pipelineId,String taskId){
        VariableQuery variableQuery = new VariableQuery();
        variableQuery.setTaskId(taskId);
        List<Variable> variableList = variableService.findVariableList(variableQuery);
        if (variableList.isEmpty()){
            return;
        }

        // 添加到缓存中
        for (Variable variable : variableList) {
            ExecVariable execVariable = new ExecVariable();
            execVariable.setVarType(variable.getVarType());
            execVariable.setVarKey(variable.getVarKey());
            execVariable.setVarValue(variable.getVarValue());
            execVariable.setPipelineId(pipelineId);

            addExecVariable(execVariable);
        }
    }

    public void addExecVariable(ExecVariable variable){
        String pipelineId = variable.getPipelineId();
        List<ExecVariable> list = variableMap.get(pipelineId);
        if (Objects.isNull(list) || list.isEmpty()){
            list = new ArrayList<>();
        }
        list.add(variable);
        variableMap.put(pipelineId,list);
    }






}































