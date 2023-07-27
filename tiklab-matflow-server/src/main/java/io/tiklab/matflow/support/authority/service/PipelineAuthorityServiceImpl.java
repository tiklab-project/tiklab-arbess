package io.tiklab.matflow.support.authority.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.privilege.role.model.PatchUser;
import io.tiklab.user.dmUser.model.DmUser;
import io.tiklab.user.dmUser.model.DmUserQuery;
import io.tiklab.user.dmUser.service.DmUserService;
import io.tiklab.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            dmRoleService.initDmRoles(pipelineId, LoginContext.getLoginId(), PipelineFinal.appName);
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
    public String[] findUserPipelineIdString(String userId){

        List<PipelineEntity> allPipeline1 = pipelineDao.findAllPipeline();
        if (Objects.isNull(allPipeline1) || allPipeline1.size() == 0){
            return new String[]{};
        }
        List<String> list = new ArrayList<>();

        List<PipelineEntity> allPipeline = pipelineDao.findAllPublicPipeline();
        if (!Objects.isNull(allPipeline)){
            for (PipelineEntity pipelineEntity : allPipeline) {
                list.add(pipelineEntity.getId());
            }
        }
        if (allPipeline1.size() == allPipeline.size()){
            return list.toArray(new String[0]);
        }

        DmUserQuery dmUserQuery = new DmUserQuery();
        dmUserQuery.setUserId(userId);
        List<DmUser> allDmUser = dmUserService.findDmUserList(dmUserQuery);
        if (Objects.isNull(allDmUser) || allDmUser.size() == 0){
            return list.toArray(new String[0]);
        }

        for (DmUser dmUser : allDmUser) {
            User user = dmUser.getUser();
            if (Objects.isNull(user)){
                continue;
            }
            list.add(dmUser.getDomainId());
        }

        return list.toArray(new String[0]);
    }


    /**
     * 获取拥有此流水线的用户
     * @param pipelineId 流水线id
     * @return 用户信息
     */
    @Override
    public List<User> findPipelineUser(String pipelineId) {

        DmUserQuery dmUserQuery = new DmUserQuery();
        dmUserQuery.setDomainId(pipelineId);
        List<DmUser> allDmUser =  dmUserService.findDmUserList(dmUserQuery);
        if (allDmUser == null){
            return null;
        }

        List<User> userList = new ArrayList<>();
        for (DmUser dmUser : allDmUser) {
            userList.add(dmUser.getUser());
        }
        return userList;
    }


    @Override
    public List<Pipeline> findUserPipeline(String userId) {
        String[] idString = findUserPipelineIdString(userId);
        List<PipelineEntity> pipelineEntities = pipelineDao.findUserPipeline(idString);
        return BeanMapper.mapList(pipelineEntities,Pipeline.class);

    }





}






























