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
import java.nio.charset.Charset;
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

    @Autowired
    CommonAchieve commonAchieve;


    public String structureStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList){
      return structure(pipelineConfigure,pipelineExecHistory,pipelineExecHistoryList);
    }

    // 构建
    private String structure(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList)  {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setTaskAlias(pipelineConfigure.getTaskAlias());
        PipelineStructure pipelineStructure = pipelineStructureService.findOneStructure(pipelineConfigure.getTaskId());
        String structureOrder = pipelineStructure.getStructureOrder();
        String structureAddress = pipelineStructure.getStructureAddress();
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        //设置拉取地址
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        String[] split = structureOrder.split("\n");
        for (String s : split) {
            try {
                Process process = commonAchieve.process(path, s, structureAddress);
                String a = "执行 : " + " ' " + s + " ' " + "\n";
                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog() + a);
                pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+a);
                //构建失败
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
                Map<String, String> map = commonAchieve.log(inputStreamReader, pipelineExecHistory,pipelineExecHistoryList);
                pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+map.get("log"));
                if (map.get("state").equals("0")){
                    pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+map.get("log"));
                    commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                    return "测试执行失败。";
                }
                pipelineExecLog.setRunLog(a+map.get("log"));
            } catch (IOException e) {
                commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                return e.toString();
            }
        }
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return null;
    }
}
