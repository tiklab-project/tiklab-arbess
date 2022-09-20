package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.execute.service.execAchieveImpl.CodeAchieveServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveImpl.DeployAchieveServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveImpl.BuildAchieveServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveImpl.TestAchieveServiceImpl;
import net.tiklab.matflow.orther.model.MatFlowProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatFlowTaskExecServiceImpl implements MatFlowTaskExecService {

    @Autowired
    CodeAchieveServiceImpl codeAchieveServiceImpl ;

    @Autowired
    BuildAchieveServiceImpl buildAchieveServiceImpl ;

    @Autowired
    TestAchieveServiceImpl testAchieveServiceImpl;

    @Autowired
    DeployAchieveServiceImpl deployAchieveServiceImpl ;

    public Integer beginState(MatFlowProcess matFlowProcess, int type){
        int state = 1;
        switch (type) {
            case 1,2,3,4,5 -> state = codeAchieveServiceImpl.clone(matFlowProcess);
            case 11 -> state = testAchieveServiceImpl.test(matFlowProcess);
            case 21, 22 -> state  = buildAchieveServiceImpl.build(matFlowProcess);
            case 31, 32-> state = deployAchieveServiceImpl.deploy(matFlowProcess);
        }
        if (state == 0) {
            return 0;
        }
        return 1;
    }
}
