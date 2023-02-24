package net.tiklab.matflow.pipeline.definition.service;

import net.tiklab.core.page.Pagination;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.model.PipelineMessageList;
import net.tiklab.matflow.pipeline.definition.model.PipelineOverview;
import net.tiklab.matflow.pipeline.instance.model.PipelineAllInstanceQuery;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import net.tiklab.matflow.home.model.PipelineFollow;
import net.tiklab.matflow.home.model.PipelineOpen;
import net.tiklab.matflow.home.service.PipelineFollowService;
import net.tiklab.matflow.home.service.PipelineOpenService;
import net.tiklab.matflow.support.until.PipelineFinal;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.privilege.role.model.PatchUser;
import net.tiklab.privilege.roleDmRole.service.DmRoleService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.dmUser.model.DmUser;
import net.tiklab.user.dmUser.service.DmUserService;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineRelationServiceImpl implements PipelineRelationService {

    @Autowired
    private DmUserService dmUserService;

    @Autowired
    private DmRoleService dmRoleService;

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private PipelineInstanceService historyService;

    @Autowired
    private PipelineOpenService openService;

    @Autowired
    private PipelineFollowService followService;

    /**
     * 删除关联信息
     * @param pipeline 流水线
     */
    @Override
    public void deleteHistory(Pipeline pipeline){

        String pipelineId = pipeline.getId();
        //删除对应的历史
        historyService.deleteAllHistory(pipelineId);
        //删除最近打开
        openService.deleteAllOpen(pipelineId);
        //删除对应文件
        String fileAddress = PipelineUntil.findFileAddress(pipelineId,1);
        PipelineUntil.deleteFile(new File(fileAddress));
        //删除对应日志
        String logAddress = PipelineUntil.findFileAddress(pipelineId,2);
        PipelineUntil.deleteFile(new File(logAddress+"/"+pipelineId));
    }


    /**
     * 更新流水线收藏状态
     * @param pipelineFollow 收藏信息
     */
    @Override
    public void updateFollow(PipelineFollow pipelineFollow){
        followService.updateFollow(pipelineFollow);
    }


    /**
     * 查询流水线最近构建信息
     * @param allPipeline 流水线
     * @return 构建信息
     */
    @Override
    public List<PipelineMessageList> findAllExecMessage(List<Pipeline> allPipeline){
        List<PipelineMessageList> pipelineMessageListList = new ArrayList<>();
        for (Pipeline pipeline : allPipeline) {
            joinTemplate.joinQuery(pipeline);
            PipelineMessageList pipelineMessageList = new PipelineMessageList();
            //成功和构建时间
            PipelineInstance latelyHistory = historyService.findLatelyHistory(pipeline.getId());
            pipelineMessageList.setId(pipeline.getId());
            pipelineMessageList.setCollect(pipeline.getCollect());
            pipelineMessageList.setName(pipeline.getName());
            pipelineMessageList.setState(pipeline.getState());
            pipelineMessageList.setColor(pipeline.getColor());
            pipelineMessageList.setUser(pipeline.getUser());
            pipelineMessageList.setPower(pipeline.getPower());
            pipelineMessageList.setType(pipeline.getType());
            if (latelyHistory != null){
                pipelineMessageList.setLastBuildTime(latelyHistory.getCreateTime());
                pipelineMessageList.setBuildStatus(latelyHistory.getRunStatus());
                pipelineMessageList.setExecUser(latelyHistory.getUser());
            }
            pipelineMessageListList.add(pipelineMessageList);
        }
        return pipelineMessageListList;
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
        if (allDmUser != null && !allDmUser.isEmpty()){
            for (DmUser dmUser : allDmUser) {
                User user = dmUser.getUser();
                if (user == null  || !user.getId().equals(userId)){
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
            j.append(pipeline.getId()).append("'");
        }
        if (s.toString().equals("")){
            return j;
        }
        if (j.toString().equals("")){
            return s;
        }
        return s.append(",").append(j);
    }

    /**
     * 获取拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
    @Override
    public List<User> findPipelineUser(String pipelineId) {
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        List<User> userList = new ArrayList<>();
        if (allDmUser == null){
            return null;
        }
        for (DmUser dmUser : allDmUser) {
            if (dmUser.getDomainId().equals(pipelineId)){
                userList.add(dmUser.getUser());
            }
        }
        return userList;
    }

    /**
     * 创建流水线关联用户
     * @param pipelineId 流水线id
     */
    public void createDmUser(String pipelineId,List<PatchUser> userList){
        //拉入创建人
        DmUser dmUser = new DmUser();
        dmUser.setDomainId(pipelineId);
        User user = new User();
        if (userList == null){
            user.setId(LoginContext.getLoginId());
            dmUser.setUser(user);
            dmUser.setType(1);
            dmRoleService.initDmRoles(pipelineId,LoginContext.getLoginId(),PipelineFinal.appName);
            return;
        }

        //多个用户
        for (PatchUser patchUser : userList) {
            dmUser.setType(0);
            user.setId(patchUser.getId());
            dmUser.setUser(user);
            if (patchUser.getId().equals(LoginContext.getLoginId())){
                dmUser.setType(1);
            }
        }

        //关联权限
        dmRoleService.initPatchDmRole(pipelineId,userList, PipelineFinal.appName);

    }

    /**
     * 更新项目域权限
     * @param pipelineId 流水线id
     */
    @Override
    public void deleteDmUser(String pipelineId){
        dmRoleService.deleteDmRoleByDomainId(pipelineId);
    }

    /**
     * 流水线执行信息统计
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    public PipelineOverview pipelineCensus(String pipelineId){
        List<PipelineInstance> allHistory = historyService.findAllHistory(pipelineId);
        if (allHistory == null){
            return null;
        }
        PipelineOverview state = new PipelineOverview();
        for (PipelineInstance history : allHistory) {
            if (history.getRunStatus() == 1){
                state.setErrorNumber(state.getErrorNumber() + 1);
            }
            if (history.getRunStatus() == 20){
                state.setRemoveNumber(state.getRemoveNumber() + 1);
            }
            if (history.getRunStatus() == 10){
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

    /**
     * 流水线统计信息
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    public PipelineOverview census(String pipelineId){
        //添加最近打开
        String loginId = LoginContext.getLoginId();
        openService.updatePipelineOpen(loginId,pipelineId);
        return pipelineCensus(pipelineId);
    }

    /**
     * 查询最近打开的流水线
     * @return 流水线
     */
    @Override
    public List<PipelineOpen> findAllOpen(StringBuilder s,int number) {
        List<PipelineOpen> allOpen = openService.findAllOpen(s) ;
        if (allOpen == null){
            return Collections.emptyList() ;
        }
        //根据打开次数降序排列
        allOpen.sort(Comparator.comparing(PipelineOpen::getNumber).reversed()) ;
        // 指定返回数量
        int min = Math.min(number, allOpen.size()) ;
        List<PipelineOpen> pipelineOpens = allOpen.subList(0, min);
        //统计信息
        for (PipelineOpen pipelineOpen : pipelineOpens) {
            Pipeline pipeline = pipelineOpen.getPipeline();
            PipelineOverview pipelineOverview = pipelineCensus(pipeline.getId());
            pipelineOpen.setPipelineExecState(pipelineOverview);
        }
        return pipelineOpens;
    }


    /**
     * 查询用户所有历史
     * @param pipelineHistoryQuery 流水线
     * @return 历史
     */
    @Override
    public Pagination<PipelineInstance> findUserAllHistory(PipelineAllInstanceQuery pipelineHistoryQuery){
        return  historyService.findUserAllHistory(pipelineHistoryQuery);

    }






}




















































