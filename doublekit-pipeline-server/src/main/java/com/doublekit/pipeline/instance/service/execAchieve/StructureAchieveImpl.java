package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineStructure;
import com.doublekit.pipeline.execute.service.PipelineStructureService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineProcess;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;


public class StructureAchieveImpl {

    @Autowired
    PipelineStructureService pipelineStructureService;

    // 构建
    public int structure(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList)  {
        CommonAchieveImpl commonAchieveImpl = new CommonAchieveImpl();
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineConfigure pipelineConfigure = pipelineProcess.getPipelineConfigure();
        PipelineExecLog pipelineExecLog = commonAchieveImpl.initializeLog(pipelineExecHistory, pipelineConfigure);

        PipelineStructure pipelineStructure = pipelineStructureService.findOneStructure(pipelineConfigure.getTaskId());
        String structureOrder = pipelineStructure.getStructureOrder();
        String structureAddress = pipelineStructure.getStructureAddress();
        pipelineProcess.setPipelineExecLog(pipelineExecLog);
        //设置拉取地址
        String path = "D:\\clone\\"+pipelineConfigure.getPipeline().getPipelineName();
        try {
            String a = "------------------------------------" + " \n"
                    +"开始构建" + " \n"
                    +"执行 : \""  + structureOrder + "\"\n";

            Process process = commonAchieveImpl.process(path, structureOrder, structureAddress);

            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog() + a);
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+a);
            //构建失败
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            int state = commonAchieveImpl.log(inputStreamReader, pipelineProcess,pipelineExecHistoryList);
            process.destroy();
            commonAchieveImpl.updateTime(pipelineProcess,beginTime);
            if (state == 0){
                commonAchieveImpl.updateState(pipelineProcess,"构建失败。",pipelineExecHistoryList);
                return 0;
            }
        } catch (IOException e) {
            commonAchieveImpl.updateState(pipelineProcess,e.getMessage(),pipelineExecHistoryList);
            return 0;
        }
        commonAchieveImpl.updateState(pipelineProcess,null,pipelineExecHistoryList);
        return 1;
    }
}
