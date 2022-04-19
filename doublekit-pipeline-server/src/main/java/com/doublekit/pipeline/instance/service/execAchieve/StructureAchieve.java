package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.service.PipelineCodeService;
import com.doublekit.pipeline.example.service.PipelineStructureService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
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
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineStructureService pipelineStructureService;

    @Autowired
    PipelineCodeService pipelineCodeService;


    CommonAchieve commonAchieve = new CommonAchieve();

    // 构建
    private int structure(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList)  {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        PipelineStructure pipelineStructure = pipelineStructureService.findOneStructure(pipelineConfigure.getTaskId());
        String structureOrder = pipelineStructure.getStructureOrder();
        String structureAddress = pipelineStructure.getStructureAddress();
        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = structureOrder.split("\n");
        for (String s : split) {
            try {
                Process process = commonAchieve.process(path, s, structureAddress);
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog() + a);
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+a);
                //构建失败
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                Map<String, String> map = commonAchieve.log(inputStreamReader, pipelineExecHistory);
                pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+map.get("log"));
                if (map.get("state").equals("0")){
                    pipelineExecLog.setLogRunLog(pipelineExecLog.getLogRunLog()+map.get("log"));
                    commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                    commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"测试执行失败。",pipelineExecHistoryList);
                    return 0;
                }
                pipelineExecLog.setLogRunLog(a+map.get("log"));
            } catch (IOException e) {
                commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
                return 0;
            }
        }
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }
}
