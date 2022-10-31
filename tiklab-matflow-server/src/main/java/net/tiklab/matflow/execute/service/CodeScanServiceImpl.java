package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineCodeScan;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;

@Service
@Exporter
public class CodeScanServiceImpl implements CodeScanService {


    @Autowired
    ConfigCommonService commonService;

    /**
     * 源码管理
     * @param pipelineProcess 执行信息
     * @param pipelineCodeScan 配置信息
     * @return 执行状态
     */
    @Override
    public boolean codeScan(PipelineProcess pipelineProcess, PipelineCodeScan pipelineCodeScan) {

        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineProcess.setBeginTime(beginTime);
        Pipeline pipeline = pipelineProcess.getPipeline();
        String fileAddress = PipelineUntil.findFileAddress();

        commonService.execHistory(pipelineProcess,"流水线开始执行");
        String order=" mvn sonar:sonar ";
        try {
//            Process process = PipelineUntil.process(fileAddress+pipeline.getPipelineName(), order);
            Process process = PipelineUntil.process("D:\\idea\\tiklab\\tiklab-matflow", order);
            commonService.log(process.getInputStream(),process.getErrorStream(),pipelineProcess);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        commonService.execHistory(pipelineProcess,"代码扫描完成");
        commonService.updateState(pipelineProcess,true);
        return true;
    }




}

















































