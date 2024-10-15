package io.tiklab.arbess.setting.service;

import io.tiklab.core.utils.UuidGenerator;
import io.tiklab.arbess.setting.model.HostGroup;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.setting.dao.AuthHostGroupDao;
import io.tiklab.arbess.setting.model.AuthHostGroup;
import io.tiklab.arbess.setting.model.AuthHostGroupDetails;
import io.tiklab.arbess.setting.model.AuthHostGroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        AuthHostGroup oneHostGroup = authHostGroupDao.findOneHostGroup(groupId);
        if (Objects.isNull(oneHostGroup)){
            return null;
        }
        List<AuthHostGroupDetails> hostGroupDetailsList = groupDetailsService.findHostGroupDetailsList(groupId);
        oneHostGroup.setDetailsList(hostGroupDetailsList);
        joinTemplate.joinQuery(oneHostGroup);
        return oneHostGroup;
    }

    @Override
    public List<HostGroup> findOneHostGroupByGroup(String groupId, Integer strategyNumber, String strategyType) {
        List<AuthHostGroupDetails> detailsList = groupDetailsService.findHostGroupDetailsList(groupId);
        joinTemplate.joinQuery(detailsList);

        if (strategyType.equals(PipelineFinal.DEFAULT)){
            return null;
        }

        int size = detailsList.size();
        if (size <= strategyNumber) {
            return null;
        }

        List<HostGroup> resultList = new ArrayList<>();

        for (int i = 0; i < detailsList.size(); i += strategyNumber) {
            List<AuthHostGroupDetails> sublist = detailsList.subList(i, Math.min(i + strategyNumber, detailsList.size()));
            String uuid = UuidGenerator.getRandomIdByUUID(12);
            resultList.add(new HostGroup(uuid,sublist));
        }
        return resultList;
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


    @Override
    public Integer findHostGroupNumber() {
        return authHostGroupDao.findHostGroupNumber();
    }

}
