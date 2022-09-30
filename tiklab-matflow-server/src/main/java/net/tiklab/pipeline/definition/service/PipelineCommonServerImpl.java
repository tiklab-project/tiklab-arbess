package net.tiklab.pipeline.definition.service;

import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.definition.model.PipelineMassage;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.service.PipelineExecHistoryService;
import net.tiklab.pipeline.orther.service.PipelineFileService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.DmUser;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Exporter
public class PipelineCommonServerImpl implements PipelineCommonServer{

    @Autowired
    PipelineFileService pipelineFileService;

    @Autowired
    PipelineConfigService pipelineConfigService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;


    /**
     * 删除关联信息
     * @param pipeline 流水线
     */
    @Override
    public void delete(Pipeline pipeline){

        String pipelineId = pipeline.getPipelineId();
        //删除对应的流水线配置
        pipelineConfigService.deleteConfig(pipelineId);
        //删除对应的历史
        pipelineExecHistoryService.deleteHistory(pipelineId);

        //删除对应文件
        String fileAddress = pipelineFileService.getFileAddress();
        pipelineFileService.deleteFile(new File(fileAddress+ pipeline.getPipelineName()));

    }

    /**
     * 流水线更改名称时更新源文件名称
     * @param newName 新的名称
     * @param lastName 旧的名称
     * @return 更新状态
     */
    @Override
    public int updatePipeline(String newName, String lastName) {
        //更改对应文件名
        String fileAddress = pipelineFileService.getFileAddress();
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
            PipelineExecHistory latelyHistory = pipelineExecHistoryService.findLatelyHistory(pipeline.getPipelineId());
            PipelineExecHistory latelySuccess = pipelineExecHistoryService.findLatelySuccess(pipeline.getPipelineId());

            pipelineMassage.setPipelineId(pipeline.getPipelineId());
            pipelineMassage.setPipelineCollect(pipeline.getPipelineCollect());
            pipelineMassage.setPipelineName(pipeline.getPipelineName());
            pipelineMassage.setPipelineState(pipeline.getPipelineState());
            pipelineMassage.setCreateTime(pipeline.getPipelineCreateTime());
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
     * 获取近七天的历史
     * @param userId 用户id
     * @return 历史
     */
    public  List<PipelineExecHistory> findRecentStatus(String userId,StringBuilder s){
        //获取7天前的时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date lastTime = DateUtils.addDays(new Date(), -7);
        Date nowTime = DateUtils.addDays(new Date(), 1);
        //StringBuilder s = findUserPipelineId(userId,allDmUser,userPipeline);
        if (s.toString().equals("")){
            return null;
        }
        return pipelineExecHistoryService.findAllUserHistory(formatter.format(lastTime),formatter.format(nowTime),s);
    }

    //获取用户7天内的所有历史
    public  List<PipelineExecHistory> findAllUserHistory(StringBuilder s){
        //获取7天前的时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date lastTime = DateUtils.addDays(new Date(), -7);
        Date nowTime = DateUtils.addDays(new Date(), 1);
        if (s.toString().equals("")){
            return null;
        }
        return pipelineExecHistoryService.findAllUserHistory(formatter.format(lastTime),formatter.format(nowTime),s);
    }

    /**
     * 拼接数据库查询条件
     * @param userId 用户id
     * @return 所有流水线id
     */
    @Override
    public StringBuilder findUserPipelineId(String userId, List<DmUser> allDmUser,List<Pipeline> pipelineList){
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

}




















































