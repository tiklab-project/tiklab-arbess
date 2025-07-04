package io.tiklab.arbess.support.count.service;

import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.arbess.support.count.model.PipelineInstanceCount;
import io.tiklab.arbess.support.count.model.PipelineTestCount;
import io.tiklab.arbess.support.count.model.PipelineTestTestHuboCount;
import io.tiklab.arbess.task.codescan.model.SonarQubeScan;
import io.tiklab.arbess.task.codescan.model.SonarQubeScanQuery;
import io.tiklab.arbess.task.codescan.model.SourceFareScan;
import io.tiklab.arbess.task.codescan.model.SourceFareScanQuery;
import io.tiklab.arbess.task.codescan.service.SonarQubeScanService;
import io.tiklab.arbess.task.codescan.service.SourceFareScanService;
import io.tiklab.arbess.task.test.model.RelevanceTestOn;
import io.tiklab.arbess.task.test.model.RelevanceTestOnQuery;
import io.tiklab.arbess.task.test.service.RelevanceTestOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PipelineTestCountServiceImpl implements PipelineTestCountService{

    @Autowired
    SonarQubeScanService sonarQubeScanService;

    @Autowired
    SourceFareScanService sourceFareScanService;

    @Autowired
    RelevanceTestOnService relevanceTestOnService;

    @Autowired
    PipelineInstanceService pipelineInstanceService;


    @Override
    public PipelineTestCount findTestCount(String pipelineId) {

        PipelineTestCount pipelineTestCount = new PipelineTestCount();

        SonarQubeScanQuery sonarQubeScanQuery = new SonarQubeScanQuery();
        sonarQubeScanQuery.setPipelineId(pipelineId);
        List<SonarQubeScan> sonarQubeScanList = sonarQubeScanService.findSonarQubeScanList(sonarQubeScanQuery);
        pipelineTestCount.setSonarQubeNumber(sonarQubeScanList.size());

        SourceFareScanQuery sourceFareScanQuery = new SourceFareScanQuery();
        sourceFareScanQuery.setPipelineId(pipelineId);
        List<SourceFareScan> sourceFareScanList = sourceFareScanService.findSourceFareScanList(sourceFareScanQuery);
        pipelineTestCount.setSourceFareNumber(sourceFareScanList.size());

        RelevanceTestOnQuery relevanceTestOnQuery = new RelevanceTestOnQuery();
        relevanceTestOnQuery.setPipelineId(pipelineId);
        List<RelevanceTestOn> relevanceList = relevanceTestOnService.findRelevanceList(relevanceTestOnQuery);
        pipelineTestCount.setTestHuboNumber(relevanceList.size());

        return pipelineTestCount;


    }


    @Override
    public PipelineTestTestHuboCount findTestTestHuboCount(String pipelineId) {

        PipelineTestTestHuboCount pipelineTestCount = new PipelineTestTestHuboCount();

        RelevanceTestOnQuery relevanceTestOnQuery = new RelevanceTestOnQuery();
        relevanceTestOnQuery.setPipelineId(pipelineId);
        List<RelevanceTestOn> relevanceList = relevanceTestOnService.findRelevanceList(relevanceTestOnQuery);
        pipelineTestCount.setAllNumber(relevanceList.size());

        long success = relevanceList.stream().filter(relevance -> "success".equals(relevance.getExecStatus())).count();

        long fail = relevanceList.stream().filter(relevance -> "fail".equals(relevance.getExecStatus())).count();

        pipelineTestCount.setSuccessNumber((int) success);
        pipelineTestCount.setFailNumber((int) fail);
        return pipelineTestCount;


    }


    @Override
    public PipelineTestTestHuboCount findTestCodeScanCount(String pipelineId) {

        PipelineTestTestHuboCount pipelineTestCount = new PipelineTestTestHuboCount();

        SonarQubeScanQuery sonarQubeScanQuery = new SonarQubeScanQuery();
        sonarQubeScanQuery.setPipelineId(pipelineId);
        List<SonarQubeScan> sonarQubeScanList = sonarQubeScanService.findSonarQubeScanList(sonarQubeScanQuery);

        SourceFareScanQuery sourceFareScanQuery = new SourceFareScanQuery();
        sourceFareScanQuery.setPipelineId(pipelineId);
        List<SourceFareScan> sourceFareScanList = sourceFareScanService.findSourceFareScanList(sourceFareScanQuery);
        pipelineTestCount.setAllNumber(sonarQubeScanList.size()+sourceFareScanList.size());

        long sonarQubeSuccess = sonarQubeScanList.stream().filter(relevance -> "OK".equals(relevance.getStatus())).count();
        long sonarQubeFail = sonarQubeScanList.stream().filter(relevance -> "ERROR".equals(relevance.getStatus())).count();

        long sourceFareSuccess = sourceFareScanList.stream().filter(relevance ->"success" .equals(relevance.getStatus())).count();
        long sourceFareFail = sourceFareScanList.stream().filter(relevance -> "fail".equals(relevance.getStatus())).count();


        pipelineTestCount.setSuccessNumber((int) (sonarQubeSuccess+sourceFareSuccess));
        pipelineTestCount.setFailNumber((int) (sonarQubeFail+sourceFareFail));
        return pipelineTestCount;


    }





}
