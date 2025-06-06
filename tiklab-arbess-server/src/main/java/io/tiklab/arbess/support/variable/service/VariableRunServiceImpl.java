package io.tiklab.arbess.support.variable.service;

import io.tiklab.arbess.support.variable.model.ExecVariable;
import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.model.VariableQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VariableRunServiceImpl implements VariableRunService {


    // 流水线id  变量名 ，变量值
    private final Map<String,List<ExecVariable>> variableMap = new HashMap<>();

    @Autowired
    VariableService variableService;


    @Override
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

    @Override
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































