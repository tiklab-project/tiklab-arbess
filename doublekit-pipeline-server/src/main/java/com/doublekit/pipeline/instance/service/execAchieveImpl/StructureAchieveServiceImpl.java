package com.doublekit.pipeline.instance.service.execAchieveImpl;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineCommonService;
import com.doublekit.pipeline.execute.model.PipelineStructure;
import com.doublekit.pipeline.execute.service.PipelineStructureService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineProcess;
import com.doublekit.pipeline.instance.service.execAchieveService.StructureAchieveService;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class StructureAchieveServiceImpl implements StructureAchieveService {

    @Autowired
    PipelineStructureService pipelineStructureService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveServiceImpl;

    @Autowired
    PipelineCommonService pipelineCommonService;

    //@Value("${}")
    private static final Logger logger = LoggerFactory.getLogger(StructureAchieveServiceImpl.class);
    // 构建
    public int structure(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList)  {

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //初始化日志
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineConfigure pipelineConfigure = pipelineProcess.getPipelineConfigure();
        PipelineExecLog pipelineExecLog = commonAchieveServiceImpl.initializeLog(pipelineExecHistory, pipelineConfigure);

        PipelineStructure pipelineStructure = pipelineStructureService.findOneStructure(pipelineConfigure.getTaskId());
        String structureOrder = pipelineStructure.getStructureOrder();
        String structureAddress = pipelineStructure.getStructureAddress();
        pipelineProcess.setPipelineExecLog(pipelineExecLog);

        //设置拉取地址
        String path = pipelineCommonService.getFileAddress()+pipelineConfigure.getPipeline().getPipelineName();

        try {
            String a = "------------------------------------" + " \n"
                    +"开始构建" + " \n"
                    +"执行 : \""  + structureOrder + "\"\n";

            Process process = commonAchieveServiceImpl.process(path, structureOrder, structureAddress);

            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog() + a);
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+a);
            //构建失败
            //InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream() ,  Charset.forName("GBK"));
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream() ,  StandardCharsets.UTF_8);
            int state = commonAchieveServiceImpl.log(inputStreamReader, pipelineProcess,pipelineExecHistoryList);
            process.destroy();
            commonAchieveServiceImpl.updateTime(pipelineProcess,beginTime);
            if (state == 0){
                commonAchieveServiceImpl.updateState(pipelineProcess,"构建失败。",pipelineExecHistoryList);
                return 0;
            }
        } catch (IOException e) {
            commonAchieveServiceImpl.updateState(pipelineProcess,e.getMessage(),pipelineExecHistoryList);
            return 0;
        }
        commonAchieveServiceImpl.updateState(pipelineProcess,null,pipelineExecHistoryList);
        return 1;
    }
}
