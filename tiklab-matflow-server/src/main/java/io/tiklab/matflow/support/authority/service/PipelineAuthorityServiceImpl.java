package io.tiklab.matflow.support.authority.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import io.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.model.PipelineQuery;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
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
        User user = new User();
        if (userList == null){
            user.setId(LoginContext.getLoginId());

            dmRoleService.initDmRoles(pipelineId, LoginContext.getLoginId(), PipelineFinal.appName);
            return;
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
        if (Objects.isNull(allPipeline1) || allPipeline1.size() == 0){
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





}






























