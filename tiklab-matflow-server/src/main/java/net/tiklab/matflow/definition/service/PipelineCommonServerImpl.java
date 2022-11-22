package net.tiklab.matflow.definition.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineMassage;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecState;
import net.tiklab.matflow.execute.service.PipelineExecHistoryService;
import net.tiklab.matflow.orther.model.PipelineOpen;
import net.tiklab.matflow.orther.service.PipelineFinal;
import net.tiklab.matflow.orther.service.PipelineOpenService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.privilege.role.model.DmRole;
import net.tiklab.privilege.role.model.DmRoleQuery;
import net.tiklab.privilege.role.model.PatchUser;
import net.tiklab.privilege.role.service.DmRoleService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.DmUser;
import net.tiklab.user.user.model.User;
import net.tiklab.user.user.service.DmUserService;
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
public class PipelineCommonServerImpl implements PipelineCommonServer{

    @Autowired
    private DmUserService dmUserService;

    @Autowired
    private DmRoleService dmRoleService;

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private PipelineExecHistoryService historyService;

    @Autowired
    private PipelineOpenService openService;


    /**
     * 删除关联信息
     * @param pipeline 流水线
     */
    @Override
    public void deleteHistory(Pipeline pipeline){

        String pipelineId = pipeline.getPipelineId();
        //删除对应的历史
        historyService.deleteAllHistory(pipelineId);
        //删除对应文件
        String fileAddress = PipelineUntil.findFileAddress();
        PipelineUntil.deleteFile(new File(fileAddress+ pipeline.getPipelineName()));
    }

    /**
     * 流水线更改名称时更新源文件夹名称
     * @param newName 新的名称
     * @param lastName 旧的名称
     */
    @Override
    public void updatePipeline(String newName, String lastName) {
        //更改对应文件名
        String fileAddress = PipelineUntil.findFileAddress();
        File file = new File(fileAddress+lastName);
        if (file.exists()){
            boolean b = file.renameTo(new File(fileAddress + newName));
            if (!b){
                throw new ApplicationException("文件重命名失败");
            }
        }
    }

    //获取流水线状态
    @Override
    public List<PipelineMassage> findAllStatus(List<Pipeline> allPipeline){
        List<PipelineMassage> pipelineMassageList = new ArrayList<>();
        for (Pipeline pipeline : allPipeline) {
            joinTemplate.joinQuery(pipeline);
            PipelineMassage pipelineMassage = new PipelineMassage();
            //成功和构建时间
            PipelineExecHistory latelyHistory = historyService.findLatelyHistory(pipeline.getPipelineId());
            pipelineMassage.setPipelineId(pipeline.getPipelineId());
            pipelineMassage.setPipelineCollect(pipeline.getPipelineCollect());
            pipelineMassage.setPipelineName(pipeline.getPipelineName());
            pipelineMassage.setPipelineState(pipeline.getPipelineState());
            pipelineMassage.setCreateTime(pipeline.getPipelineCreateTime());
            pipelineMassage.setColor(pipeline.getColor());
            pipelineMassage.setUser(pipeline.getUser());
            pipelineMassage.setPipelinePower(pipeline.getPipelinePower());
            if (latelyHistory != null){
                pipelineMassage.setLastBuildTime(latelyHistory.getCreateTime());
                pipelineMassage.setBuildStatus(latelyHistory.getRunStatus());
                pipelineMassage.setExecUser(latelyHistory.getUser());
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
        if (allDmUser != null && !allDmUser.isEmpty()){
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
            dmUserService.createDmUser(dmUser);
            return;
        }

        for (PatchUser patchUser : userList) {
            user.setId(patchUser.getId());
            dmUser.setUser(user);
            dmUserService.createDmUser(dmUser);
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
        DmRoleQuery dmRoleQuery = new DmRoleQuery();
        dmRoleQuery.setDomainId(pipelineId);
        List<DmRole> dmRoleList = dmRoleService.findDmRoleList(dmRoleQuery);
        if (dmRoleList == null){
            return;
        }
        for (DmRole dmRole : dmRoleList) {
            dmRoleService.deleteDmRole(dmRole.getId());
        }

    }

    /**
     * 删除关联角色
     * @param pipelineId 流水线id
     */
    public void deleteDmRole(String pipelineId){
        List<DmRole> allDmRole = dmRoleService.findAllDmRole();
        if (allDmRole == null){
            return ;
        }
        for (DmRole dm : allDmRole) {
            if (!dm.getDomainId().equals(pipelineId)){
                continue;
            }
            dmRoleService.deleteDmRole(dm.getId());
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
        //添加最近打开
        String loginId = LoginContext.getLoginId();
        openService.updatePipelineOpen(loginId,pipelineId);
        return state;
    }


    /**
     * 查询最近打开的流水线
     * @return 流水线
     */
    @Override
    public List<PipelineOpen> findAllOpen(StringBuilder s) {
        List<PipelineOpen> allOpen = openService.findAllOpen(s) ;
        if (allOpen == null){
            return Collections.emptyList() ;
        }
        allOpen.sort(Comparator.comparing(PipelineOpen::getNumber).reversed()) ;
        int min = Math.min(5, allOpen.size()) ;
        return allOpen.subList(0,min);
    }


}




















































