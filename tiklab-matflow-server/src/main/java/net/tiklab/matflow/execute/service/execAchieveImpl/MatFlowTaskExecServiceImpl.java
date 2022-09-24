package net.tiklab.matflow.execute.service.execAchieveImpl;

import net.tiklab.matflow.orther.model.MatFlowProcess;
import net.tiklab.matflow.execute.service.execAchieveService.MatFlowTaskExecService;
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

    public String beginState(MatFlowProcess matFlowProcess, int type){
        String state = null;
        switch (type) {
            case 1,2,3,4,5 -> state = codeAchieveServiceImpl.clone(matFlowProcess);
            case 11 -> state = testAchieveServiceImpl.test(matFlowProcess);
            case 21, 22 -> state  = buildAchieveServiceImpl.build(matFlowProcess);
            case 31, 32-> state = deployAchieveServiceImpl.deploy(matFlowProcess);
        }
        return state;
    }
}
