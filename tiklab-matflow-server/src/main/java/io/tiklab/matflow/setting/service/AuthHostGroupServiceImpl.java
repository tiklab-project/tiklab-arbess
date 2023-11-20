package io.tiklab.matflow.setting.service;

import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.setting.dao.AuthHostGroupDao;
import io.tiklab.matflow.setting.model.AuthHostGroup;
import io.tiklab.matflow.setting.model.AuthHostGroupDetails;
import io.tiklab.matflow.setting.model.AuthHostGroupQuery;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * @author zcamy
 */
@Service
public class AuthHostGroupServiceImpl implements AuthHostGroupService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    AuthHostGroupDao authHostGroupDao;

    @Autowired
    AuthHostGroupDetailsService groupDetailsService;

    @Override
    public String creatHostGroup(AuthHostGroup hostGroup) {
        String date = PipelineUtil.date(1);
        hostGroup.setCreateTime(date);
        String hostGroupId = authHostGroupDao.creatHostGroup(hostGroup);

        List<AuthHostGroupDetails> detailsList = hostGroup.getDetailsList();
        if (Objects.isNull(detailsList) || detailsList.isEmpty()){
            return hostGroupId;
        }

        // 创建关联关系
        for (AuthHostGroupDetails hostGroupDetails : detailsList) {
            hostGroupDetails.setGroupId(hostGroupId);
            groupDetailsService.creatHostGroupDetails(hostGroupDetails);
        }

        return hostGroupId;
    }

    @Override
    public void updateHostGroup(AuthHostGroup hostGroup) {
        String groupId = hostGroup.getGroupId();
        // 删除旧的关联关系
        List<AuthHostGroupDetails> hostGroupDetailsList = groupDetailsService.findHostGroupDetailsList(groupId);
        for (AuthHostGroupDetails authHostGroupDetails : hostGroupDetailsList) {
            String id = authHostGroupDetails.getId();
            groupDetailsService.deleteHostGroupDetails(id);
        }

        // 创建新的关联关系
        List<AuthHostGroupDetails> detailsList = hostGroup.getDetailsList();
        for (AuthHostGroupDetails hostGroupDetails : detailsList) {
            hostGroupDetails.setGroupId(groupId);
            groupDetailsService.creatHostGroupDetails(hostGroupDetails);
        }
        authHostGroupDao.updateHostGroup(hostGroup);
    }

    @Override
    public void deleteHostGroup(String groupId) {
        List<AuthHostGroupDetails> hostGroupDetailsList = groupDetailsService.findHostGroupDetailsList(groupId);
        for (AuthHostGroupDetails authHostGroupDetails : hostGroupDetailsList) {
            String id = authHostGroupDetails.getId();
            groupDetailsService.deleteHostGroupDetails(id);
        }
        authHostGroupDao.deleteHostGroup(groupId);
    }

    @Override
    public AuthHostGroup findOneHostGroup(String groupId) {
        List<AuthHostGroupDetails> hostGroupDetailsList = groupDetailsService.findHostGroupDetailsList(groupId);
        AuthHostGroup oneHostGroup = authHostGroupDao.findOneHostGroup(groupId);
        oneHostGroup.setDetailsList(hostGroupDetailsList);
        joinTemplate.joinQuery(oneHostGroup);
        return oneHostGroup;
    }

    @Override
    public List<AuthHostGroup> findAllHostGroup() {
        return authHostGroupDao.findAllHostGroup();
    }

    @Override
    public List<AuthHostGroup> findHostGroupList(AuthHostGroupQuery groupQuery) {
        List<AuthHostGroup>   hostGroupList = authHostGroupDao.findHostGroupList(groupQuery);

        for (AuthHostGroup authHostGroup : hostGroupList) {
            String groupId = authHostGroup.getGroupId();
            List<AuthHostGroupDetails> hostGroupDetailsList = groupDetailsService.findHostGroupDetailsList(groupId);
            authHostGroup.setDetailsList(hostGroupDetailsList);
        }
        joinTemplate.joinQuery(hostGroupList);
        return hostGroupList;
    }

}