package net.tiklab.pipeline.execute.service.execAchieveImpl;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.execute.service.execAchieveService.ConfigCommonService;
import net.tiklab.pipeline.orther.service.PipelineFileService;
import net.tiklab.pipeline.definition.model.PipelineBuild;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.service.PipelineExecServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveService.BuildAchieveService;
import net.tiklab.pipeline.execute.model.PipelineProcess;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * 构建执行方法
 */

@Service
@Exporter
public class BuildAchieveServiceImpl implements BuildAchieveService {

    @Autowired
    PipelineFileService pipelineFileService;

    @Autowired
    ConfigCommonService configCommonService;

    private static final Logger logger = LoggerFactory.getLogger(BuildAchieveServiceImpl.class);

    // 构建
    public String build(PipelineProcess pipelineProcess,  PipelineBuild pipelineBuild)  {
        List<PipelineExecHistory> list = PipelineExecServiceImpl.pipelineExecHistoryList;
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineProcess.getPipeline();
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();

        //项目地址
        String path = pipelineFileService.getFileAddress()+ pipeline.getPipelineName();

        try {
            String a = "------------------------------------" + " \n"
                    +"开始构建" + " \n"
                    +"执行 : \""  + pipelineBuild.getBuildOrder() + "\"\n";

            //更新日志
            configCommonService.execHistory(list,pipeline.getPipelineId(),a,pipelineExecLog);

            //执行命令
            Process process = getOrder(pipelineBuild, path);
            if (process == null){
                configCommonService.updateTime(pipelineProcess,beginTime);
                configCommonService.updateState(pipelineProcess,false, list);
                return "构建命令执行错误" ;
            }

            //构建失败
            int state = configCommonService.log(process.getInputStream(), pipelineProcess, list);
            process.destroy();
            configCommonService.updateTime(pipelineProcess,beginTime);
            if (state == 0){
                configCommonService.updateState(pipelineProcess,false, list);
                return "构建失败";
            }
        } catch (IOException | ApplicationException e) {
            configCommonService.updateState(pipelineProcess,false, list);
            return e.getMessage() ;
        }
        configCommonService.updateState(pipelineProcess,true, list);
        return null;
    }

    /**
     * 执行build
     * @param pipelineBuild 执行信息
     * @param path 项目地址
     * @return 执行命令
     */
    private Process getOrder(PipelineBuild pipelineBuild,String path ) throws ApplicationException, IOException {
        String buildOrder = pipelineBuild.getBuildOrder();
        String buildAddress = pipelineBuild.getBuildAddress();
        
        int type = pipelineBuild.getType();
        String order ;
        switch (type){
            case 21 -> {
                String mavenAddress = configCommonService.getScm(21);
                if (mavenAddress == null) {
                    throw new ApplicationException("不存在maven配置");
                }
                order = mavenOrder(buildOrder, path, buildAddress);
                return configCommonService.process(mavenAddress, order);
            }
            case 22 -> {
                String nodeAddress = configCommonService.getScm(22);
                if (nodeAddress == null) {
                    throw new ApplicationException("不存在node配置");
                }
                order = nodeOrder(buildOrder, path, buildAddress);
                return configCommonService.process(nodeAddress, order);
            }
        }
        return null;
    }

    /**
     * 拼装maven命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @param buildAddress 模块地址
     * @return 命令
     */
    private String mavenOrder(String buildOrder,String path,String buildAddress){
        
        String order;
        int systemType = configCommonService.getSystemType();
        order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        if (!Objects.equals(buildAddress, "/")){
            order = order + buildAddress;
        }
        return order;
    }

    /**
     * 拼装node命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @param buildAddress 模块地址
     * @return 命令
     */
    private String nodeOrder(String buildOrder,String path,String buildAddress ){
        String order;
        int systemType = configCommonService.getSystemType();
        order = " ./" + buildOrder + " " + "-f" +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f" +path;
        }
        if (buildAddress != null){
            order = order + buildAddress;
        }
        return order;
    }
}
