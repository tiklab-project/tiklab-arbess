package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineBuild;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * 构建执行方法
 */

@Service
@Exporter
public class BuildAchieveServiceImpl implements BuildAchieveService {

    @Autowired
    ConfigCommonService commonService;


    // 构建
    public boolean build(PipelineProcess pipelineProcess,  PipelineBuild pipelineBuild)  {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineProcess.getPipeline();
        pipelineProcess.setBeginTime(beginTime);

        //项目地址
        String path = PipelineUntil.findFileAddress()+ pipeline.getPipelineName();

        try {
            String a = "------------------------------------" + " \n"
                    +"开始构建" + " \n"
                    +"项目地址：" +path + " \n"
                    +"执行 : \""  + pipelineBuild.getBuildOrder() + "\"\n";

            //更新日志
            commonService.execHistory(pipelineProcess,a);

            //执行命令
            Process process = getOrder(pipelineBuild, path);
            if (process == null){
                commonService.execHistory(pipelineProcess,"构建命令执行错误");
                return false;
            }

            //构建失败
            int state = commonService.log(process.getInputStream(), process.getErrorStream(),pipelineProcess,null);
            process.destroy();
            if (state == 0){
                commonService.execHistory(pipelineProcess,"构建失败");
                return false;
            }
        } catch (IOException | ApplicationException e) {
            commonService.execHistory(pipelineProcess,e.getMessage());
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
        String buildAddress = "/"+ pipelineBuild.getBuildAddress();
        
        int type = pipelineBuild.getType();
        String order ;
        switch (type){
            case 21 -> {
                String mavenAddress = commonService.getScm(21);
                if (mavenAddress == null) {
                    throw new ApplicationException("不存在maven配置");
                }
                order =  PipelineUntil.mavenOrder(buildOrder,path + buildAddress);
                return PipelineUntil.process(mavenAddress, order);
            }
            case 22 -> {
                String nodeAddress = commonService.getScm(22);
                if (nodeAddress == null) {
                    throw new ApplicationException("不存在node配置");
                }
                return PipelineUntil.process(path+ buildAddress, buildOrder);
            }
            default -> {return null;}
        }
    }

}
