package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineStructure;
import com.doublekit.pipeline.execute.service.PipelineStructureService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class StructureAchieve {

    @Autowired
    PipelineStructureService pipelineStructureService;

    @Autowired
    CommonAchieve commonAchieve;


    public int structureStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList){
      return structure(pipelineConfigure,pipelineExecHistory,pipelineExecHistoryList);
    }

    // 构建
    private int structure(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList)  {
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志
        PipelineExecLog pipelineExecLog = commonAchieve.initializeLog(pipelineExecHistory, pipelineConfigure);

        PipelineStructure pipelineStructure = pipelineStructureService.findOneStructure(pipelineConfigure.getTaskId());
        String structureOrder = pipelineStructure.getStructureOrder();
        String structureAddress = pipelineStructure.getStructureAddress();
        //设置拉取地址
        String path = "D:\\clone\\"+pipelineConfigure.getPipeline().getPipelineName();
        try {
            String a = "执行 : " + " ' " + structureOrder + " ' " + "\n";
            Process process = commonAchieve.process(path, structureOrder, structureAddress);
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog() + a);
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+a);
            //构建失败
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            int state = commonAchieve.log(inputStreamReader, pipelineExecHistory,pipelineExecHistoryList,pipelineExecLog);
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            if (state == 0){
                commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"构建失败。",pipelineExecHistoryList);
                return 0;
            }
        } catch (IOException e) {
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.getMessage(),pipelineExecHistoryList);
            return 0;
        }
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }
}
