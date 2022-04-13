package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.service.PipelineStructureService;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineStructureLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class StructureAchieve {

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineStructureService pipelineStructureService;


    CommonAchieve commonAchieve = new CommonAchieve();

    // 构建
    private int structure(PipelineConfigure pipelineConfigure, PipelineExecLog pipelineExecLog,List<PipelineExecLog> pipelineExecLogList)  {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineStructureLog structureLog = pipelineExecLog.getStructureLog();
        PipelineStructure pipelineStructure = pipelineStructureService.findOneStructure(pipelineConfigure.getPipelineStructure().getStructureId());
        String structureOrder = pipelineStructure.getStructureOrder();
        String structureAddress = pipelineStructure.getStructureAddress();
        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = structureOrder.split("\n");
        for (String s : split) {
            try {
                Process process = commonAchieve.process(path, s, structureAddress);
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog() + a);
                structureLog.setStructureRunLog(structureLog.getStructureRunLog()+a);
                //构建失败
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                Map<String, String> map = commonAchieve.log(inputStreamReader, pipelineExecLog);
                structureLog.setStructureRunLog(structureLog.getStructureRunLog()+map.get("log"));
                if (map.get("state").equals("0")){
                    structureState(pipelineExecLog,beginTime,"构建失败。。。。",pipelineExecLogList);
                    commonAchieve.error(pipelineExecLog,"构建失败。。。。", pipelineConfigure.getPipeline().getPipelineId());
                    return 0;
                }
            } catch (IOException e) {
                structureState(pipelineExecLog,beginTime,e.toString(),pipelineExecLogList);
                commonAchieve.error(pipelineExecLog,"构建异常："+ e, pipelineConfigure.getPipeline().getPipelineId());
                return 0;
            }
        }
        structureState(pipelineExecLog,beginTime,null,pipelineExecLogList);
        return 1;
    }

    //构建错误
    private void structureState(PipelineExecLog pipelineExecLog,long beginTime,String e,List<PipelineExecLog> pipelineExecLogList){
        PipelineStructureLog structureLog = pipelineExecLog.getStructureLog();
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        if (e == null){
            structureLog.setStructureRunStatus(10);
        }else {
            structureLog.setStructureRunStatus(1);
            structureLog.setStructureRunLog(structureLog.getStructureRunLog()+"\n"+e);
        }
        structureLog.setStructureRunTime(time);
        pipelineExecLog.setStructureLog(structureLog);
        pipelineExecLog.setLogRunTime(pipelineExecLog.getLogRunTime()+time);
        pipelineExecLogService.updateLog(pipelineExecLog);
        pipelineExecLogList.add(pipelineExecLog);


    }
}
