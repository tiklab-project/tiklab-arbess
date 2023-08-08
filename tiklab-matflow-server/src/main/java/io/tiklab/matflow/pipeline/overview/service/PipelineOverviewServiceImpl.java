package io.tiklab.matflow.pipeline.overview.service;

import io.tiklab.matflow.pipeline.overview.dao.PipelineOverviewDao;
import io.tiklab.matflow.pipeline.overview.model.PipelineOverview;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 流水线统计服务
 */

@Service
public class PipelineOverviewServiceImpl implements PipelineOverviewService {


    @Autowired
    PipelineOverviewDao overviewDao;


    /**
     * 流水线执行信息统计
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    @Override
    public PipelineOverview pipelineOverview(String pipelineId) {

        PipelineOverview overview = new PipelineOverview();

        Integer errorNumber = overviewDao.findPipelineRunStateNumber(pipelineId, PipelineFinal.RUN_ERROR);
        Integer haltNumber = overviewDao.findPipelineRunStateNumber(pipelineId, PipelineFinal.RUN_HALT);
        Integer successNumber = overviewDao.findPipelineRunStateNumber(pipelineId, PipelineFinal.RUN_SUCCESS);

        int allNumber = errorNumber + haltNumber + successNumber;

        overview.setSuccessNumber(successNumber);
        overview.setErrorNumber(errorNumber);
        overview.setHaltNumber(haltNumber);
        overview.setAllNumber(allNumber);

        Integer runTime = overviewDao.findPipelineRunTime(pipelineId);

        if (Objects.isNull(runTime) || runTime == 0){
            overview.setTime("0 秒");
        }else {
            int i = runTime / allNumber;
            String s = PipelineUtil.formatDateTime(i);
            overview.setTime(s);
        }
        return overview;
    }



}

























