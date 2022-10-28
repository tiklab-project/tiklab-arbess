package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineMassage;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecState;
import net.tiklab.matflow.execute.service.PipelineExecHistoryService;
import net.tiklab.matflow.orther.service.PipelineFileService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.DmUser;
import net.tiklab.user.user.service.DmUserService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Exporter
public class PipelineCommonServerImpl implements PipelineCommonServer{

    @Autowired
    DmUserService dmUserService;

    @Autowired
    PipelineExecHistoryService historyService;


    /**
     * 删除关联信息
     * @param pipeline 流水线
     */
    @Override
    public void delete(Pipeline pipeline){
        String pipelineId = pipeline.getPipelineId();
        //删除对应的历史
        historyService.deleteHistory(pipelineId);
        //删除对应文件
        String fileAddress = PipelineUntil.findFileAddress();
        PipelineUntil.deleteFile(new File(fileAddress+ pipeline.getPipelineName()));

    }

    /**
     * 流水线更改名称时更新源文件夹名称
     * @param newName 新的名称
     * @param lastName 旧的名称
     * @return 更新状态
     */
    @Override
    public int updatePipeline(String newName, String lastName) {
        //更改对应文件名
        String fileAddress = PipelineUntil.findFileAddress();
        File file = new File(fileAddress+lastName);
        if (file.exists()){
            boolean b = file.renameTo(new File( fileAddress+newName));
        }
        return 1;
    }

    //获取流水线状态
    @Override
    public List<PipelineMassage> findAllStatus(List<Pipeline> allPipeline){
        List<PipelineMassage> pipelineMassageList = new ArrayList<>();
        for (Pipeline pipeline : allPipeline) {
            PipelineMassage pipelineMassage = new PipelineMassage();
            //成功和构建时间
            PipelineExecHistory latelyHistory = historyService.findLatelyHistory(pipeline.getPipelineId());
            PipelineExecHistory latelySuccess = historyService.findLatelySuccess(pipeline.getPipelineId());

            pipelineMassage.setPipelineId(pipeline.getPipelineId());
            pipelineMassage.setPipelineCollect(pipeline.getPipelineCollect());
            pipelineMassage.setPipelineName(pipeline.getPipelineName());
            pipelineMassage.setPipelineState(pipeline.getPipelineState());
            pipelineMassage.setCreateTime(pipeline.getPipelineCreateTime());
            pipelineMassage.setColor(pipeline.getColor());
            if (latelyHistory != null){
                pipelineMassage.setLastBuildTime(latelyHistory.getCreateTime());
                pipelineMassage.setBuildStatus(latelyHistory.getRunStatus());
            }
            if (latelySuccess != null){
                pipelineMassage.setLastSuccessTime(latelySuccess.getCreateTime());
            }
            pipelineMassageList.add(pipelineMassage);
        }
        return pipelineMassageList;
    }

    /**
     * 拼接数据库查询条件
     * @param userId 用户id
     * @return 所有流水线id
     */
    @Override
    public StringBuilder findUserPipelineId(String userId,List<Pipeline> pipelineList){
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        //获取项目域条件
        StringBuilder s = new StringBuilder();
        if (allDmUser != null && allDmUser.size() != 0){
            for (DmUser dmUser : allDmUser) {
                if (!dmUser.getUser().getId().equals(userId)){
                    continue;
                }
                if (s.toString().equals("") ) {
                    s.append("'");
                } else {
                    s.append(",'");
                }
                s.append(dmUser.getDomainId()).append("'");
            }
        }
        //获取流水线id
        StringBuilder j = new StringBuilder();
        if (s.toString().equals("") && pipelineList == null){
            return null;
        }
        for (Pipeline pipeline : pipelineList) {
            if (j.toString().equals("") ) {
                j.append("'");
            } else {
                j.append(",'");
            }
            j.append(pipeline.getPipelineId()).append("'");
        }
        if (s.toString().equals("")){
            return j;
        }
        return s.append(",").append(j);
    }

    /**
     * 获取拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
    @Override
    public List<DmUser> findPipelineUser(String pipelineId) {
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        if (allDmUser == null){
            return null;
        }
        List<DmUser> dmUsers = new ArrayList<>();
        for (DmUser dmUser : allDmUser) {
            if (dmUser.getDomainId().equals(pipelineId)){
                dmUsers.add(dmUser);
            }
        }
        return dmUsers;
    }

    /**
     * 更新项目域权限
     * @param pipelineId 流水线id
     * @param dmUser 权限域
     * @param b 类型
     */
    @Override
    public void updateDmUser(String pipelineId,DmUser dmUser,boolean b){

        if (b){
            dmUserService.createDmUser(dmUser);
            return;
        }

        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        if (allDmUser == null){
            return ;
        }
        for (DmUser dm : allDmUser) {
            if (!dm.getDomainId().equals(pipelineId)){
                continue;
            }
            dmUserService.deleteDmUser(dm.getId());
        }
    }

    /**
     * 流水线执行信息统计
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    public PipelineExecState pipelineCensus(String pipelineId){
        List<PipelineExecHistory> allHistory = historyService.findAllHistory(pipelineId);
        if (allHistory == null){
            return null;
        }
        PipelineExecState state = new PipelineExecState();
        for (PipelineExecHistory history : allHistory) {
            if (history.getRunStatus() == 1){
                state.setErrorNumber(state.getErrorNumber() + 1);
            }
            if (history.getRunStatus() == 20){
                state.setRemoveNumber(state.getRemoveNumber() + 1);
            }
            if (history.getRunStatus() == 30){
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
        state.setTime(PipelineUntil.formatDateTime(state.getExecTime()));
        return state;
    }

   
    public Map<String,Integer> findBuildState(List<Pipeline> list){
        Map<String, Integer> map = new HashMap<>();
        int success = 0,error = 0,hilt = 0;
        if (list == null){
            map.put("成功", success);
            map.put("失败", error);
            map.put("停止", hilt);
            return map;
        }
        for (Pipeline pipeline : list) {
            List<PipelineExecHistory> allHistory = historyService.findAllHistory(pipeline.getPipelineId());
            if (allHistory == null){
               continue;
            }

            for (PipelineExecHistory history : allHistory) {
                int status = history.getStatus();
                switch (status) {
                    case 30 -> success = success + 1;
                    case 10 -> hilt = hilt + 1;
                    case 1 -> error = error + 1;
                }
            }
        }

        map.put("成功", success);
        map.put("失败", error);
        map.put("停止", hilt);
        return map;
    }


}




















































