package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.achieve.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineTaskExecServiceImpl implements PipelineTaskExecService {

    @Autowired
    CodeService code ;

    @Autowired
    BuildService build ;

    @Autowired
    TestService test;

    @Autowired
    DeployService deploy ;

    @Autowired
    CodeScanService codeScan;

    @Autowired
    ProductService product;

    @Autowired
    MessageExecService message;

    public boolean beginState(PipelineProcess pipelineProcess, Object config, int type){
        boolean state = true;
        switch (type/10) {
            case 0 -> state = code.clone(pipelineProcess,(PipelineCode) config);
            case 1 -> state = test.test(pipelineProcess,(PipelineTest) config);
            case 2 -> state = build.build(pipelineProcess, (PipelineBuild) config);
            case 3 -> state = deploy.deploy(pipelineProcess,(PipelineDeploy) config);
            case 4 -> state = codeScan.codeScan(pipelineProcess, (PipelineCodeScan) config);
            case 5 -> state = product.product(pipelineProcess, (PipelineProduct) config);
            case 6 -> state = message.message(pipelineProcess, (PipelineMessage) config);
        }
        return state;
    }
}































