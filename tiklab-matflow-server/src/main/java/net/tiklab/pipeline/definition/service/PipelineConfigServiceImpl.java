package net.tiklab.pipeline.definition.service;

import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.definition.model.PipelineConfig;
import net.tiklab.pipeline.definition.model.PipelineCode;
import net.tiklab.pipeline.definition.model.PipelineDeploy;
import net.tiklab.pipeline.definition.model.PipelineBuild;
import net.tiklab.pipeline.definition.model.PipelineTest;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维护配置关联信息
 */

@Service
@Exporter
public class PipelineConfigServiceImpl implements PipelineConfigService {

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    PipelineBuildService pipelineBuildService;

    @Autowired
    PipelineTestService pipelineTestService;

    @Autowired
    PipelineDeployService pipelineDeployService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineConfigServiceImpl.class);

    //更新流水线配置
    @Override
    public void updateConfig(PipelineConfig pipelineConfig){

        PipelineCode pipelineCode = pipelineConfig.getPipelineCode();
        pipelineCodeService.updateCode(pipelineCode,pipelineCode.getPipeline().getPipelineId());

        PipelineBuild pipelineBuild = pipelineConfig.getPipelineBuild();
        pipelineBuildService.updateBuild(pipelineBuild, pipelineBuild.getPipeline().getPipelineId());

        PipelineTest pipelineTest = pipelineConfig.getPipelineTest();
        pipelineTestService.updateTest(pipelineTest, pipelineTest.getPipeline().getPipelineId());

        PipelineDeploy pipelineDeploy = pipelineConfig.getPipelineDeploy();
        pipelineDeployService.updateDeploy(pipelineDeploy, pipelineDeploy.getPipeline().getPipelineId());

    }

    //删除流水线配置
    @Override
    public void deleteConfig(String pipelineId){
        PipelineCode code = pipelineCodeService.findCode(pipelineId);
        if (code != null){
            pipelineCodeService.deleteCode(code.getCodeId());
        }

        PipelineTest test = pipelineTestService.findTest(pipelineId);
        if (test != null){
            pipelineTestService.deleteTest(test.getTestId());
        }

        PipelineBuild build = pipelineBuildService.findBuild(pipelineId);
        if(build!= null){
            pipelineBuildService.deleteBuild(build.getBuildId());
        }

        PipelineDeploy deploy = pipelineDeployService.findDeploy(pipelineId);
        if (deploy != null){
            pipelineDeployService.deleteDeploy(deploy.getDeployId());
        }

    }

    //按顺序获取配置
    public Map<Integer,Integer> findConfig(String pipelineId){
        HashMap<Integer, Integer> map = new HashMap<>();
        PipelineCode code = pipelineCodeService.findCode(pipelineId);
        if (code != null){
            map.put(1, 10);
        }

        PipelineTest test = pipelineTestService.findTest(pipelineId);
        if (test != null){
            map.put(test.getSort(),20);
        }

        PipelineBuild build = pipelineBuildService.findBuild(pipelineId);
        if(build!= null){
            map.put(build.getSort(), 30);
        }

        PipelineDeploy deploy = pipelineDeployService.findDeploy(pipelineId);
        if (deploy != null){
            map.put(deploy.getSort(), 40);
        }
        return map;
    }

    //查询流水线配置
    @Override
    public List<Object> findAllConfig(String pipelineId){
        List<Object> list = new ArrayList<>();
        Map<Integer, Integer> map = findConfig(pipelineId);
        for (int i = 1; i <= 4; i++) {
            if (map.get(i) == null){
                continue;
            }
            Object configure = configure(map.get(i), pipelineId);
            if (configure != null){
                list.add(configure);
            }
        }
        return list;
    }

    @Override
    public Object configure(Integer type,String pipelineId){
        switch (type){
            case 10:
                PipelineCode code = pipelineCodeService.findCode(pipelineId);
                if (code != null){
                    return code;
                }
            case 20:
                PipelineTest test = pipelineTestService.findTest(pipelineId);
                if (test != null){
                    return test;
                }
            case 30:
                PipelineBuild build = pipelineBuildService.findBuild(pipelineId);
                if(build!= null){
                    return build;
                }
            case 40:
                PipelineDeploy deploy = pipelineDeployService.findDeploy(pipelineId);
                if (deploy != null){
                    return deploy;
                }
        }
        return null;
    }


    @Override
    public PipelineConfig AllConfig(String pipelineId){

        PipelineConfig pipelineConfig = new PipelineConfig();

        PipelineCode code = pipelineCodeService.findCode(pipelineId);
        if (code != null){
           pipelineConfig.setPipelineCode(code);
        }

        PipelineTest test = pipelineTestService.findTest(pipelineId);
        if (test != null){
            pipelineConfig.setPipelineTest(test);
        }

        PipelineBuild build = pipelineBuildService.findBuild(pipelineId);
        if(build!= null){
           pipelineConfig.setPipelineBuild(build);
        }

        PipelineDeploy deploy = pipelineDeployService.findDeploy(pipelineId);
        if (deploy != null){
            pipelineConfig.setPipelineDeploy(deploy);
        }
        return pipelineConfig;
    }

}


































