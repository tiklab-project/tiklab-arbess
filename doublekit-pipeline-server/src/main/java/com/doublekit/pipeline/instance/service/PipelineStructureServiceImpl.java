package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Exporter
public class PipelineStructureServiceImpl implements PipelineStructureService {

    @Autowired
    GitCloneService gitCloneService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    ProofService proofService;


    @Override
    public void structure(PipelineHistory pipelineHistory) throws Exception {

        PipelineHistory history = new PipelineHistory();



    }
}
