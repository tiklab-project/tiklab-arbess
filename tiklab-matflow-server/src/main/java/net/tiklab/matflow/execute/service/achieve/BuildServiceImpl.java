package net.tiklab.matflow.execute.service.achieve;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.definition.model.task.PipelineBuild;
import net.tiklab.matflow.definition.service.task.PipelineBuildService;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 构建执行方法
 */

@Service
@Exporter
public class BuildServiceImpl implements BuildService {

    @Autowired
    private PipelineExecCommonService commonService;

    @Autowired
    PipelineBuildService buildService;

    // 构建
    public boolean build(PipelineProcess pipelineProcess, PipelineCourseConfig config)  {

        PipelineBuild pipelineBuild = buildService.findOneBuildConfig(config.getConfigId());

        pipelineBuild.setType(config.getTaskType());

        Pipeline pipeline = pipelineProcess.getPipeline();

        String log = PipelineUntil.date(4);

        //项目地址
        String path = PipelineUntil.findFileAddress()+ pipeline.getPipelineName();

        try {
            String a = log+"开始构建" + " \n"
                    +log+"项目地址：" +path + " \n"
                    +log+"执行构建命令:"  + pipelineBuild.getBuildOrder() ;

            //更新日志
            commonService.execHistory(pipelineProcess,a);

            //执行命令
            Process process = getOrder(pipelineBuild, path);
            if (process == null){
                commonService.execHistory(pipelineProcess,log+"构建命令执行错误");
                return false;
            }

            pipelineProcess.setInputStream(process.getInputStream());
            pipelineProcess.setErrInputStream(process.getErrorStream());
            pipelineProcess.setError(error(pipelineBuild.getType()));

            //构建失败
            int state = commonService.log(pipelineProcess);

            process.destroy();
            if (state == 0){
                commonService.execHistory(pipelineProcess,log+"构建失败");
                return false;
            }
        } catch (IOException | ApplicationException e) {
            commonService.execHistory(pipelineProcess,log+e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 执行build
     * @param pipelineBuild 执行信息
     * @param path 项目地址
     * @return 执行命令
     */
    private Process getOrder(PipelineBuild pipelineBuild,String path) throws ApplicationException, IOException {
        String buildOrder = pipelineBuild.getBuildOrder();
        if(PipelineUntil.isNoNull(pipelineBuild.getBuildAddress())){
            path = path +"/"+ pipelineBuild.getBuildAddress();
        }

        
        int type = pipelineBuild.getType();
        String order ;
        switch (type){
            case 21 -> {
                String mavenAddress = commonService.getScm(21);
                if (mavenAddress == null) {
                    throw new ApplicationException("不存在maven配置");
                }
                order =  mavenOrder(buildOrder,path);
                return PipelineUntil.process(mavenAddress, order);
            }
            case 22 -> {
                String nodeAddress = commonService.getScm(22);
                if (nodeAddress == null) {
                    throw new ApplicationException("不存在node配置");
                }
                return PipelineUntil.process(path, buildOrder);
            }
            default -> {return null;}
        }
    }

    /**
     * 拼装测试命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @return 命令
     */
    public String mavenOrder(String buildOrder,String path){
        String order;
        int systemType = PipelineUntil.findSystemType();
        order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        return order;
    }

    public String validOrder(String order){
        String[] split = order.split("\n");
        for (String s : split) {
            String substring = s.substring(0, 1);
            // if (substring)
        }
        return null;
    }

    private String[] error(int type){
        String[] strings;
        if (type == 21){
            strings = new String[]{
                    "BUILD FAILUREl","ERROR"
            };
            return strings;
        }
        strings = new String[]{

        };
        return strings;
    }



}
