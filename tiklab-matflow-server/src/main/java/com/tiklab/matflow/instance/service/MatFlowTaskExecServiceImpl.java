package com.tiklab.matflow.instance.service;

import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowProcess;
import com.tiklab.matflow.instance.service.execAchieveImpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatFlowTaskExecServiceImpl implements MatFlowTaskExecService {

    @Autowired
    CodeAchieveServiceImpl codeAchieveServiceImpl ;

    @Autowired
    StructureAchieveServiceImpl structureAchieveServiceImpl ;

    @Autowired
    TestAchieveServiceImpl testAchieveServiceImpl;

    @Autowired
    DeployAchieveServiceImpl deployAchieveServiceImpl ;

    public Integer beginState(MatFlowProcess matFlowProcess, int type){
        int state = 1;
        switch (type) {
            case 1,2,3,4,5 -> state = codeAchieveServiceImpl.clone(matFlowProcess);
            case 11 -> state = testAchieveServiceImpl.test(matFlowProcess);
            case 21, 22 -> state  = structureAchieveServiceImpl.structure(matFlowProcess);
            case 31, 32-> state = deployAchieveServiceImpl.deploy(matFlowProcess);
        }
        if (state == 0) {
            return 0;
        }
        return 1;
    }
}
