package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.example.service.PipelineDeployService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Exporter
public class DeployAchieve {


    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineDeployService pipelineDeployService;

    CommonAchieve commonAchieve = new CommonAchieve();

    /**
     * 部署到liunx
     * @param pipelineConfigure 配置信息
     * @param pipelineExecHistory 日志
     * @return
     */
    public int liunx(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory ,List<PipelineExecHistory> pipelineExecHistoryList) {
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        PipelineDeploy pipelineDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
        String deployTargetAddress = pipelineDeploy.getDeployTargetAddress();
        String deployAddress = pipelineDeploy.getDeployAddress();
        pipelineExecHistoryList.add(pipelineExecHistory);
        Proof proof = pipelineDeploy.getProof();
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        String s = "部署到服务器" + proof.getProofIp() + "。。。。。。。。";
        pipelineExecLog.setLogRunLog(s);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+s);
        //文件地址
        String[] split = deployTargetAddress.split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";

        //发送文件名
        String zipName = commonAchieve.zipName(path,split[1]);
        path  = path + "\\" +zipName ;

        //发送文件位置
        deployAddress = deployAddress +"/"+ zipName;
        try {
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"开始发送文件:"+path);
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"开始发送文件:"+path);
            //发送文件
            commonAchieve.sshSftp(proof,deployAddress,path);
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"文件:"+zipName+"发送成功！");
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"文件:"+zipName+"发送成功！");
            //执行shell
            String shell = pipelineDeploy.getDeployShell();
            if (shell != null){
                String[] s1 = shell.split("\n");
                for (String value : s1) {
                    commonAchieve.sshOrder(proof, value, pipelineExecHistory);
                }
            }
        } catch (JSchException | SftpException | IOException e) {
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
            return 0;
        }
        //更新状态
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }


    /**
     * docker部署
     * @param pipelineConfigure 配置信息
     * @param pipelineExecHistory 日志
     * @return 部署状态
     */
    public int docker(PipelineConfigure pipelineConfigure,PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList) {
        //开始运行时间
        PipelineDeploy pipelineDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        Proof proof = pipelineDeploy.getProof();
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        //模块名
        String[] split = pipelineDeploy.getDeployTargetAddress().split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";
        //文件名称
        String zipName = commonAchieve.zipName(path,split[1]);
        //本机文件地址
        String fileAddress  = path + "\\" +zipName ;
        String  fileName = null;
        if (zipName != null) {
            String[] split1 = zipName.split("."+split[1]);
            String[] split2 = split1[0].split("-distribution");
            fileName = split2[0];
        }
        String liunxAddress = pipelineDeploy.getDeployAddress();
        //发送文件位置
        String deployAddress = liunxAddress+ "/" +zipName ;
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"压缩包文件名为： "+zipName+"\n"+"解压文件名称："+fileName+"\n"+"部署到docker地址 ： " +deployAddress);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"发送文件中。。。。。");
        pipelineExecLog.setLogRunLog("压缩包文件名为： "+zipName+"\n"+"解压文件名称："+fileName+"\n"+"部署到docker文件地址 ： " +deployAddress);
        try {
            commonAchieve.sshSftp(proof,deployAddress,fileAddress);
            HashMap<Integer, String> map = new HashMap<>();
            map.put(1,"rm -rf "+" "+liunxAddress+ "/" +fileName);
            map.put(2,"unzip"+" "+deployAddress);
            map.put(3,"docker stop $(docker ps -a | grep '"+pipeline.getPipelineName()+"' | awk '{print $1 }')");
            map.put(4,"docker rm $(docker ps -a | grep '"+pipeline.getPipelineName()+"' | awk '{print $1 }')");
            map.put(5,"docker image rm"+" "+pipeline.getPipelineName());
            map.put(6,"find"+" "+liunxAddress+"/"+fileName+" "+ "-name '*.sh' | xargs dos2unix");
            map.put(7,"cd"+" "+fileName+";"+"docker image build -t"+" "+pipeline.getPipelineName()+"  .");
            map.put(8,"docker run -itd -p"+" "+pipelineDeploy.getMappingPort()+":"+pipelineDeploy.getDockerPort()+" "+pipeline.getPipelineName());
            for (int i = 1; i <= 8; i++) {
                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"第"+i+"步 ："+ map.get(i));
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n第"+i+"步 ："+ map.get(i));
                Map<String, String> log = commonAchieve.sshOrder(proof, map.get(i), pipelineExecHistory);
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+log.get("log"));
            }
        } catch (JSchException | SftpException |IOException   e) {
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
            return  0;
        }
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }
}
