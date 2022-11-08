package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineProduct;
import net.tiklab.matflow.definition.service.ProductAchieveService;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
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

        String fileAddress = product.getFileAddress();
        String path = PipelineUntil.getFile(pipeline.getPipelineName(),fileAddress);
        if (path == null){
            commonService.execHistory(pipelineProcess,"无法匹配到制品");
            return false;
        }

        commonService.execHistory(pipelineProcess,"开始推送制品。。。。。。");
        try {
            Process process = getOrder(pipelineProcess,product,path);
            if (process == null){
                commonService.execHistory(pipelineProcess,"推送制品执行失败");
                return false;
            }
            int log = commonService.log(process.getInputStream(), process.getErrorStream(), pipelineProcess,"UTF-8");

            if (log == 0){
                commonService.execHistory(pipelineProcess,"推送制品失败");
                return false;
            }
        } catch (IOException | ApplicationException e) {
            commonService.execHistory(pipelineProcess,"推送制品执行错误\n"+e.getMessage());
            return false;
        }
        commonService.execHistory(pipelineProcess,"推送制品完成");
        return true;
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
            PipelineAuthThird authThird = (PipelineAuthThird)product.getAuth();
            if (authThird == null){
                order = mavenOrder(execOrder, path);
                commonService.execHistory(pipelineProcess,"执行推送制品命令："+order);
                return PipelineUntil.process(mavenAddress, order);
            }

            execOrder = execOrder +
                    " -DgroupId="+product.getGroupId() +
                    " -DartifactId="+product.getArtifactId() +
                    " -Dversion="+product.getVersion()+
                    " -Dpackaging="+product.getFileType() +
                    " -Dfile="+path +
                    " -Durl="+authThird.getServerAddress() ;
            if (authThird.getAuthType() == 1){
                execOrder = execOrder +
                        " -Dserver.username="+authThird.getUsername()+
                        " -Dserver.password="+authThird.getPassword();
            }else {
                execOrder = execOrder +
                        " -DrepositoryId="+authThird.getPrivateKey();
            }
            order = mavenOrder(execOrder, path);
            commonService.execHistory(pipelineProcess,"执行推送制品命令："+order);
            return PipelineUntil.process(mavenAddress, order);
        }
        return null;
    }


    private String mavenOrder(String buildOrder,String path){
        String order = " ./" + buildOrder  ;
        if (PipelineUntil.findSystemType() == 1){
            order = " .\\" + buildOrder ;
        }
        return order;
    }

}
