package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.instance.model.PipelineDeployLog;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class DeployAchieve {


    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    //存放过程状态
    List<PipelineExecLog> pipelineExecLogList = new ArrayList<>();

    CommonAchieve commonAchieve = new CommonAchieve();

    /**
     * 部署到liunx
     * @param pipelineConfigure 配置信息
     * @param pipelineExecLog 日志
     * @return
     */
    public int liunx(PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog) {
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineDeployLog deployLog = pipelineExecLog.getDeployLog();
        String deployTargetAddress = pipelineConfigure.getPipelineDeploy().getDeployTargetAddress();
        String deployAddress = pipelineConfigure.getPipelineDeploy().getDeployAddress();
        pipelineExecLogList.add(pipelineExecLog);
        Proof proof = pipelineConfigureService.findDeployProof(pipelineConfigure);

        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        String s = "部署到服务器" + proof.getProofIp() + "。。。。。。。。";
        deployLog.setDeployRunLog(s);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+s);
        //文件地址
        String[] split = deployTargetAddress.split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";

        //发送文件名
        String zipName = commonAchieve.zipName(path,split[1]);
        path  = path + "\\" +zipName ;

        //发送文件位置
        deployAddress = deployAddress +"/"+ zipName;
        try {
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"开始发送文件:"+path);
            deployLog.setDeployRunLog(deployLog.getDeployRunLog()+"\n"+"开始发送文件:"+path);
            //发送文件
            commonAchieve.sshSftp(proof,deployAddress,path);
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"文件:"+zipName+"发送成功！");
            deployLog.setDeployRunLog(deployLog.getDeployRunLog()+"\n"+"文件:"+zipName+"发送成功！");
            //执行shell
            String shell = pipelineConfigure.getPipelineDeploy().getDeployShell();
            if (shell != null){
                String[] s1 = shell.split("\n");
                for (String value : s1) {
                    commonAchieve.sshOrder(proof, value, pipelineExecLog);
                }
            }

        } catch (JSchException | SftpException | IOException e) {
            deployState(pipelineExecLog,proof,e.toString(),beginTime);
            return 0;
        }
        //更新状态
        deployState(pipelineExecLog,proof,null,beginTime);
        return 1;
    }


    /**
     * docker部署
     * @param pipelineConfigure 配置信息
     * @param pipelineExecLog 日志
     * @return 部署状态
     */
    private int docker(PipelineConfigure pipelineConfigure,PipelineExecLog pipelineExecLog) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineDeploy pipelineDeploy = pipelineConfigure.getPipelineDeploy();
        Proof proof = pipelineConfigureService.findDeployProof(pipelineConfigure);
        PipelineDeployLog deployLog = pipelineExecLog.getDeployLog();
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
        String liunxAddress = pipelineConfigure.getPipelineDeploy().getDeployAddress();
        //发送文件位置
        String deployAddress = liunxAddress+ "/" +zipName ;
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"压缩包文件名为： "+zipName+"\n"+"解压文件名称："+fileName+"\n"+"部署到docker地址 ： " +deployAddress);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"发送文件中。。。。。");
        deployLog.setDeployRunLog("压缩包文件名为： "+zipName+"\n"+"解压文件名称："+fileName+"\n"+"部署到docker文件地址 ： " +deployAddress);
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
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"第"+i+"步 ："+ map.get(i));
                deployLog.setDeployRunLog(deployLog.getDeployRunLog()+"\n第"+i+"步 ："+ map.get(i));
                Map<String, String> log = commonAchieve.sshOrder(proof, map.get(i), pipelineExecLog);
                deployLog.setDeployRunLog(deployLog.getDeployRunLog()+"\n"+log.get("log"));
            }
        } catch (JSchException | SftpException |IOException   e) {
            deployState(pipelineExecLog,proof,e.toString(),beginTime);
            return  0;
        }
        deployState(pipelineExecLog,proof,null,beginTime);
        return 1;
    }


    /**
     * 更新部署状态
     * @param pipelineExecLog 日志
     * @param proof 凭证
     * @param e 异常
     * @param beginTime 开始时间
     */
    public  void deployState(PipelineExecLog pipelineExecLog,Proof proof,String e, long beginTime){
        PipelineDeployLog deployLog = pipelineExecLog.getDeployLog();
        deployLog.setDeployRunStatus(10);
        if (e != null){
            deployLog.setDeployRunStatus(1);
            pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n部署失败! \n"+e );
            deployLog.setDeployRunLog(deployLog.getDeployRunLog()+"\n部署失败! \n" + e);
            commonAchieve.error(pipelineExecLog,e,pipelineExecLog.getPipelineId());
        }
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        deployLog.setDeployRunTime(time);
        pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
        pipelineExecLog.setDeployLog(deployLog);
        pipelineExecLogService.updateLog(pipelineExecLog);
        pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+"\n"+"服务器部署:"+proof.getProofIp()+"成功!");
        pipelineExecLogList.add(pipelineExecLog);
    }
}
