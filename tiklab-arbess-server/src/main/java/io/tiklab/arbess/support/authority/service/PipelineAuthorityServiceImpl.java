package io.tiklab.arbess.support.authority.service;

import io.tiklab.arbess.pipeline.definition.dao.PipelineDao;
import io.tiklab.arbess.pipeline.definition.entity.PipelineEntity;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.model.PipelineQuery;
import io.tiklab.message.message.service.MessageDmNoticeService;
import io.tiklab.privilege.role.model.RoleUser;
import io.tiklab.privilege.role.service.RoleUserService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.privilege.role.model.PatchUser;
import io.tiklab.user.dmUser.model.DmUser;
import io.tiklab.user.dmUser.model.DmUserQuery;
import io.tiklab.user.dmUser.service.DmUserService;
import io.tiklab.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PipelineAuthorityServiceImpl implements PipelineAuthorityService {

    @Autowired
    DmUserService dmUserService;

    @Autowired
    DmRoleService dmRoleService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    PipelineDao pipelineDao;

    @Autowired
    MessageDmNoticeService messageDmNoticeService;

    @Override
    public void createDmUser(String pipelineId,String createUserId, List<PatchUser> userList){

        // 获取系统超级管理员
        RoleUser roleAdmin = roleUserService.findUserRoleAdmin();
        String id = roleAdmin.getUser().getId();

        if (Objects.isNull(userList) || userList.isEmpty()){
            dmRoleService.initDmRoles(pipelineId, createUserId,2);
            if (createUserId.equals(id)){
                return;
            }
            dmRoleService.initDmRoles(pipelineId, id,1);
        }else {
            // 判断系统管理员是否在其中
            List<PatchUser> list = userList.stream()
                    .filter(patchUser -> patchUser.getUserId().equals(id))
                    .toList();
            if (list.isEmpty()){
                PatchUser patchUser = new PatchUser(id);
                userList.add(patchUser);
            }
            //关联权限
            dmRoleService.initPatchDmRole(pipelineId,userList);
        }
    }

    @Override
    public void deleteDmUser(String pipelineId){
        dmRoleService.deleteDmRoleByDomainId(pipelineId);
    }

    @Override
    public String[] findUserPipelineIdString(String userId){

        List<String> list = new ArrayList<>();

        // 查询公共的流水线
        PipelineQuery pipelineQuery = new PipelineQuery();
        pipelineQuery.setPipelinePower(1);
        List<PipelineEntity> allPipeline = pipelineDao.findPipelineList(pipelineQuery);

        if (!Objects.isNull(allPipeline) && !allPipeline.isEmpty()){
            allPipeline.forEach(p -> list.add(p.getId()));
        }

        // 查询用户拥有的流水线
        DmUserQuery dmUserQuery = new DmUserQuery();
        dmUserQuery.setUserId(userId);
        List<DmUser> allDmUser = dmUserService.findDmUserListNoQuery(dmUserQuery);

        if (Objects.isNull(allDmUser) || allDmUser.isEmpty()){
            return list.toArray(String[]::new);
        }

        List<String> strings1 = allDmUser.stream().map(DmUser::getDomainId).toList();
        list.addAll(strings1);
        List<String> strings = list.stream().distinct().toList();

        return strings.toArray(String[]::new);

    }


    @Override
    public List<User> findPipelineUser(String pipelineId) {

        DmUserQuery dmUserQuery = new DmUserQuery();
        dmUserQuery.setDomainId(pipelineId);
        List<DmUser> allDmUser =  dmUserService.findDmUserList(dmUserQuery);
        if (Objects.isNull(allDmUser)){
            return Collections.emptyList();
        }

        return allDmUser.stream().map(DmUser::getUser)
                .filter(user -> !Objects.isNull(user))
                .toList();
    }


    @Override
    public List<Pipeline> findUserPipeline(String userId) {
        String[] idString = findUserPipelineIdString(userId);
        PipelineQuery query = new PipelineQuery();
        query.setIdString(idString);
        List<PipelineEntity> pipelineEntities = pipelineDao.findPipelineList(query);
        return BeanMapper.mapList(pipelineEntities,Pipeline.class);
    }

    @Override
    public void cloneDomainRole(String sourceDomainId,String cloneDomainId){
        dmRoleService.cloneDomainRole(sourceDomainId,cloneDomainId);
    }


}






























