package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineProduct;
import net.tiklab.matflow.definition.service.ProductAchieveService;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;

@Service
@Exporter
public class ProductAchieveServiceImpl implements ProductAchieveService {

    @Autowired
    ConfigCommonService commonService;

    /**
     * 推送制品代码
     * @param pipelineProcess 执行信息
     * @param product 配置信息
     * @return 执行状态
     */
    @Override
    public boolean product(PipelineProcess pipelineProcess, PipelineProduct product) {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineProcess.setBeginTime(beginTime);
        Pipeline pipeline = pipelineProcess.getPipeline();
        String fileAddress = PipelineUntil.findFileAddress();
        commonService.execHistory(pipelineProcess,"开始推送制品。。。。。。");
        try {
            Process process = getOrder(pipelineProcess,product,fileAddress+pipeline.getPipelineName());
            if (process == null){
                commonService.execHistory(pipelineProcess,"推送制品执行失败");
                return commonService.updateState(pipelineProcess,false);
            }
            int log = commonService.log(process.getInputStream(), process.getErrorStream(), pipelineProcess);
            if (log == 0){
                commonService.execHistory(pipelineProcess,"推送制品失败");
                return commonService.updateState(pipelineProcess,false);
            }
        } catch (IOException | ApplicationException e) {
            commonService.execHistory(pipelineProcess,"推送制品执行错误\n"+e.getMessage());
            return commonService.updateState(pipelineProcess,false);
        }
        commonService.execHistory(pipelineProcess,"推送制品完成");
        return commonService.updateState(pipelineProcess,true);
    }

    private Process getOrder(PipelineProcess pipelineProcess, PipelineProduct product, String path) throws ApplicationException, IOException {
        int type = product.getType();
        String order ;
        String execOrder =  "mvn deploy:deploy-file ";
        if (type == 51) {
            String mavenAddress = commonService.getScm(21);
            if (mavenAddress == null) {
                throw new ApplicationException("不存在maven配置");
            }

            // PipelineAuth pipelineAuth = product.getPipelineAuth();
            // if (pipelineAuth == null){
            //     order = mavenOrder(execOrder, path);
            //     commonService.execHistory(pipelineProcess,"执行推送制品命令："+order);
            //     return PipelineUntil.process(mavenAddress, order);
            // }
            //
            // execOrder = execOrder +
            //         " -DgroupId="+product.getGroupId() +
            //         " -DartifactId="+product.getArtifactId() +
            //         " -Dversion="+product.getVersion()+
            //         " -Dpackaging="+product.getFileType() +
            //         " -Dfile="+product.getFileAddress() +
            //         " -Durl="+pipelineAuth.getUrl() ;
            // if (pipelineAuth.getAuthType() == 1){
            //     execOrder = execOrder +
            //             " -Dserver.username="+pipelineAuth.getUsername()+
            //             " -Dserver.password="+pipelineAuth.getPassword();
            // }else {
            //     execOrder = execOrder +
            //             " -DrepositoryId="+pipelineAuth.getToken();
            // }
            order = mavenOrder(execOrder, path);
            commonService.execHistory(pipelineProcess,"执行推送制品命令："+order);
            return PipelineUntil.process(mavenAddress, order);
        }
        return null;
    }


    private String mavenOrder(String buildOrder,String path){
        String order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (PipelineUntil.findSystemType() == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        return order;
    }

}
