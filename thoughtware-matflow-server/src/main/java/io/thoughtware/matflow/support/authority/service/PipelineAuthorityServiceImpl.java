package io.thoughtware.matflow.support.authority.service;

import io.thoughtware.matflow.pipeline.definition.dao.PipelineDao;
import io.thoughtware.matflow.pipeline.definition.entity.PipelineEntity;
import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.definition.model.PipelineQuery;
import io.thoughtware.matflow.support.util.PipelineFinal;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.privilege.dmRole.service.DmRoleService;
import io.thoughtware.privilege.role.model.PatchUser;
import io.thoughtware.user.dmUser.model.DmUser;
import io.thoughtware.user.dmUser.model.DmUserQuery;
import io.thoughtware.user.dmUser.service.DmUserService;
import io.thoughtware.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PipelineAuthorityServiceImpl implements PipelineAuthorityService{

    @Autowired
    DmUserService dmUserService;

    @Autowired
    DmRoleService dmRoleService;

    @Autowired
    PipelineDao pipelineDao;

    @Override
    public void createDmUser(String pipelineId, List<PatchUser> userList){
        //拉入创建人
        if (Objects.isNull(userList)){
            dmRoleService.initDmRoles(pipelineId, LoginContext.getLoginId(), PipelineFinal.appName);
            // 拉入超级管理员
            if (!LoginContext.getLoginId().equals("111111")){
                dmRoleService.initDmRoles(pipelineId, "111111", PipelineFinal.appName);
            }
            return;
        }
        boolean admin = false;
        for (PatchUser patchUser : userList) {
            if (patchUser.getId().equals("111111")){
                admin = true;
            }
        }
        // 拉入超级管理员
        if (!admin){
            PatchUser patchUser = new PatchUser();
            patchUser.setId("111111");
            patchUser.setAdminRole(true);
            userList.add(patchUser);
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
        // 流水线为空
        if (Objects.isNull(allPipeline1) || allPipeline1.isEmpty()){
            return new String[]{};
        }

        List<String> list = new ArrayList<>();
        // 查询公共的流水线
        PipelineQuery pipelineQuery = new PipelineQuery();
        pipelineQuery.setPipelinePower(1);
        List<PipelineEntity> allPipeline = pipelineDao.findPipelineList(pipelineQuery);

        if (!Objects.isNull(allPipeline)){
            for (PipelineEntity pipelineEntity : allPipeline) {
                list.add(pipelineEntity.getId());
            }
        }
        // 流水线全部都为公共的
        if (allPipeline1.size() == allPipeline.size()){
            return list.toArray(new String[0]);
        }

        // 查询用户拥有的流水线
        DmUserQuery dmUserQuery = new DmUserQuery();
        dmUserQuery.setUserId(userId);
        List<DmUser> allDmUser = dmUserService.findDmUserList(dmUserQuery);

        if (Objects.isNull(allDmUser) || allDmUser.isEmpty()){
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
            return Collections.emptyList();
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
        PipelineQuery query = new PipelineQuery();
        query.setIdString(idString);
        List<PipelineEntity> pipelineEntities = pipelineDao.findPipelineList(query);
        return BeanMapper.mapList(pipelineEntities,Pipeline.class);
    }


    public void cloneDomainRole(String sourceDomainId,String cloneDomainId){
        dmRoleService.cloneDomainRole(sourceDomainId,cloneDomainId);
    }


}






























