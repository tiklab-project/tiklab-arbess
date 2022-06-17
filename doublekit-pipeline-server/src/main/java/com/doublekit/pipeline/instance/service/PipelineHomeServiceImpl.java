package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecState;
import com.doublekit.pipeline.instance.model.PipelineFollow;
import com.doublekit.pipeline.instance.model.PipelineOpen;
import com.doublekit.rpc.annotation.Exporter;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Exporter
public class PipelineHomeServiceImpl implements PipelineHomeService{

    @Autowired
    PipelineOpenService pipelineOpenService;

    @Autowired
    PipelineFollowService pipelineFollowService;

    @Autowired
    PipelineService pipelineService;



    private static final Logger logger = LoggerFactory.getLogger(PipelineHomeServiceImpl.class);


    //查询用户最近打开的流水线
    @Override
    public List<PipelineOpen> findAllOpen(String userId){
        List<PipelineOpen> allOpen = pipelineOpenService.findAllOpen(userId);
        if (allOpen == null){
            return null;
        }
        allOpen.sort(Comparator.comparing(PipelineOpen::getNumber,Comparator.reverseOrder()));
        return allOpen;
    }

    //获取收藏列表
    @Override
    public List<PipelineStatus> findAllFollow(String userId){
        List<PipelineStatus> allStatus = pipelineService.findAllStatus(userId);
        if (allStatus == null){
            return null;
        }
        return pipelineFollowService.findAllFollow(userId,allStatus);
    }

    //更新收藏信息
    @Override
    public void updateFollow(PipelineFollow pipelineFollow){
        pipelineFollowService.updateFollow(pipelineFollow);
    }

    //获取用户流水线
    @Override
    public List<PipelineStatus> findUserPipeline(String userId){
        List<PipelineStatus> allStatus = pipelineService.findAllStatus(userId);
        if (allStatus == null){
            return null;
        }
        return pipelineFollowService.findUserPipeline(userId,allStatus);
    }

    @Override
    public List<PipelineExecState> runState(String userId){
        List<PipelineExecState> list = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int i = -7;
        List<PipelineExecHistory> allUserHistory = pipelineService.findAllUserHistory(userId);
        while (i <= 0){
            Date lastTime = DateUtils.addDays(new Date(), i);
            PipelineExecState pipelineExecState = new PipelineExecState();
            if (allUserHistory == null){
                return null;
            }
            for (PipelineExecHistory pipelineExecHistory : allUserHistory) {
                try {
                    Date time = formatter.parse(pipelineExecHistory.getCreateTime());
                    if (getTimeNumber(time,lastTime) != 0){
                      continue;
                    }
                    pipelineExecState.setTime(formatter.format(time));
                    switch (pipelineExecHistory.getRunStatus()) {
                        case 30 -> pipelineExecState.setSuccessNumber(pipelineExecState.getSuccessNumber() + 1);
                        case 1 -> pipelineExecState.setErrorNumber(pipelineExecState.getErrorNumber() + 1);
                    }
                } catch (ParseException e) {
                    logger.info("获取历史创建时间时日期转换异常。");
                    throw new RuntimeException(e);
                }
            }
            if (pipelineExecState.getTime()!=null && pipelineExecState.getErrorNumber()+pipelineExecState.getSuccessNumber()>0){
                list.add(pipelineExecState);
            }
            i++;
        }
        return list;
    }

    //获取天数差
    public  int getTimeNumber(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

}
