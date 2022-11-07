package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.definition.service.ProductAchieveService;
import net.tiklab.matflow.execute.model.PipelineProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineTaskExecServiceImpl implements PipelineTaskExecService {

    @Autowired
    CodeAchieveService code ;

    @Autowired
    BuildAchieveService build ;

    @Autowired
    TestAchieveService test;

    @Autowired
    DeployAchieveService deploy ;

    @Autowired
    CodeScanService codeScan;

    @Autowired
    ProductAchieveService product;

    public boolean beginState(PipelineProcess pipelineProcess, Object config, int type){
        boolean state = true;
        switch (type/10) {
            case 0 -> state = code.clone(pipelineProcess,(PipelineCode) config);
            case 1 -> state = test.test(pipelineProcess,(PipelineTest) config);
            case 2 -> state = build.build(pipelineProcess, (PipelineBuild) config);
            case 3 -> state = deploy.deploy(pipelineProcess,(PipelineDeploy) config);
            case 4 -> state = codeScan.codeScan(pipelineProcess, (PipelineCodeScan) config);
            case 5 -> state = product.product(pipelineProcess, (PipelineProduct) config);
        }
        return state;
    }
}































