package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.PipelineStatus;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.model.*;
import com.doublekit.rpc.annotation.Exporter;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Exporter
public class PipelineHomeServiceImpl implements PipelineHomeService{

    @Autowired
    PipelineOpenService pipelineOpenService;

    @Autowired
    PipelineFollowService pipelineFollowService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineActionService pipelineActionService;


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

    //近七天构建状态
    @Override
    public List<PipelineExecState> runState(String userId){
        List<PipelineExecState> list = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int i = -6;
        List<PipelineExecHistory> allUserHistory = pipelineService.findAllUserHistory(userId);
        if (allUserHistory == null){
            return null;
        }
        while (i <= 0){
            Date lastTime = DateUtils.addDays(new Date(), i);
            PipelineExecState pipelineExecState = new PipelineExecState();
            pipelineExecState.setTime(formatter.format(lastTime));
            for (PipelineExecHistory pipelineExecHistory : allUserHistory) {
                try {
                    Date time = formatter.parse(pipelineExecHistory.getCreateTime());
                    if (!formatter.format(lastTime).equals(formatter.format(time))){
                      continue;
                    }
                    switch (pipelineExecHistory.getRunStatus()) {
                        case 30 -> pipelineExecState.setSuccessNumber(pipelineExecState.getSuccessNumber() + 1);
                        case 20 -> pipelineExecState.setRemoveNumber(pipelineExecState.getRemoveNumber() + 1);
                        case 1 -> pipelineExecState.setErrorNumber(pipelineExecState.getErrorNumber() + 1);
                    }
                } catch (ParseException e) {
                    logger.info("获取历史创建时间时日期转换异常。");
                    throw new RuntimeException(e);
                }
            }

            if (pipelineExecState.getTime() != null){
                pipelineExecState.setTime(pipelineExecState.getTime().substring(5));
                list.add(pipelineExecState);
            }
            i++;
        }
        return list;
    }

    //用户动态
   public List<PipelineAction> findAllAction(String userId){
       List<PipelineStatus> userPipeline = findUserPipeline(userId);
       List<PipelineAction> allActive = pipelineActionService.findAllUserActive();
       if (userPipeline == null || allActive ==null){
           return null;
       }
       List<PipelineAction> list = new ArrayList<>();
       for (PipelineAction pipelineAction : allActive) {
           if (pipelineAction.getUser().getId().equals(userId)){
               list.add(pipelineAction);
           }
       }
       for (PipelineStatus pipelineStatus : userPipeline) {
           for (PipelineAction pipelineAction : allActive) {
               if (pipelineAction.getPipeline() != null || pipelineAction.getPipeline().getPipelineId().equals(pipelineStatus.getPipelineId())){
                   continue;
               }
               list.add(pipelineAction);
           }
       }
       list.sort(Comparator.comparing(PipelineAction::getCreateTime,Comparator.reverseOrder()));
       return list;
   }

















}
