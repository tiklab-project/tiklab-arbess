package io.tiklab.matflow.pipeline.overview.service;

import io.tiklab.matflow.pipeline.overview.model.PipelineOverview;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流水线统计服务
 */

@Service
public class PipelineOverviewServiceImpl implements PipelineOverviewService {

    @Autowired
    PipelineInstanceService historyService;


    /**
     * 流水线执行信息统计
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    @Override
    public PipelineOverview pipelineOverview(String pipelineId) {
        List<PipelineInstance> allHistory = historyService.findPipelineAllInstance(pipelineId);
        if (allHistory == null){
            return null;
        }
        PipelineOverview state = new PipelineOverview();
        for (PipelineInstance history : allHistory) {
            if (history.getRunStatus().equals(PipelineFinal.RUN_ERROR) ){
                state.setErrorNumber(state.getErrorNumber() + 1);
            }
            if (history.getRunStatus().equals(PipelineFinal.RUN_HALT)){
                state.setRemoveNumber(state.getRemoveNumber() + 1);
            }
            if (history.getRunStatus().equals(PipelineFinal.RUN_SUCCESS)){
                state.setSuccessNumber(state.getSuccessNumber() + 1);
            }
            state.setExecTime(state.getExecTime()+history.getRunTime());
            state.setNumber(state.getNumber()+1);
        }
        if (state.getNumber() != 0){
            state.setExecTime(state.getExecTime()/state.getNumber()+1);
        }

        if (state.getExecTime() == 0){
            state.setTime("0 秒");
            return state;
        }
        state.setTime(PipelineUtil.formatDateTime(state.getExecTime()));
        return state;
    }



}

























