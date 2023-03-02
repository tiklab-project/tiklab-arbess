package net.tiklab.matflow.support.authority.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import net.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.support.util.PipelineFinal;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.privilege.role.model.PatchUser;
import net.tiklab.privilege.roleDmRole.service.DmRoleService;
import net.tiklab.user.dmUser.model.DmUser;
import net.tiklab.user.dmUser.service.DmUserService;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PipelineAuthorityServiceImpl implements PipelineAuthorityService{

    @Autowired
    private DmUserService dmUserService;

    @Autowired
    private DmRoleService dmRoleService;

    @Autowired
    private PipelineDao pipelineDao;

    @Override
    public void createDmUser(String pipelineId, List<PatchUser> userList){
        //拉入创建人
        DmUser dmUser = new DmUser();
        dmUser.setDomainId(pipelineId);
        User user = new User();
        if (userList == null){
            user.setId(LoginContext.getLoginId());
            dmUser.setUser(user);
            dmUser.setType(1);
            dmRoleService.initDmRoles(pipelineId,LoginContext.getLoginId(), PipelineFinal.appName);
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

    @Override
    public void deleteDmUser(String pipelineId){
        dmRoleService.deleteDmRoleByDomainId(pipelineId);
    }


    @Override
    public StringBuilder findUserPipelineIdString(String userId){
        List<DmUser> allDmUser = dmUserService.findAllDmUser();
        StringBuilder s = new StringBuilder();
        //获取用户拥有权限的流水线，拼装成字符串
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

        //获取用户拥有的流水线，拼装成字符串
        List<PipelineEntity> allPipeline = pipelineDao.findAllPublicPipeline();
        List<Pipeline> pipelineList = BeanMapper.mapList(allPipeline, Pipeline.class);
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

        //返回拼装后的结果
        if (!PipelineUtil.isNoNull(s.toString())){
            return j;
        }
        if (!PipelineUtil.isNoNull(j.toString())){
            return s;
        }
        return s.append(",").append(j);

    }


    /**
     * 获取拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
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


    public List<Pipeline> findUserPipeline(String userId) {
        StringBuilder idString = findUserPipelineIdString(userId);
        List<PipelineEntity> pipelineEntities = pipelineDao.findUserPipeline(idString);
        return BeanMapper.mapList(pipelineEntities,Pipeline.class);

    }



}






























