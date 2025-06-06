package io.tiklab.arbess.setting.hostgroup.service;

import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.setting.hostgroup.dao.AuthHostGroupDetailsDao;
import io.tiklab.arbess.setting.hostgroup.model.AuthHostGroupDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zcamy
 */
@Service
public class AuthHostGroupDetailsServiceImpl implements AuthHostGroupDetailsService {

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    AuthHostGroupDetailsDao authHostGroupDetailsDao;

    @Override
    public String creatHostGroupDetails(AuthHostGroupDetails hostGroupDetails) {
        return authHostGroupDetailsDao.creatHostGroupDetails(hostGroupDetails);
    }

    @Override
    public void updateHostGroupDetails(AuthHostGroupDetails hostGroupDetails) {
        authHostGroupDetailsDao.updateHostGroupDetails(hostGroupDetails);
    }

    @Override
    public void deleteHostGroupDetails(String groupDetailsId) {
        authHostGroupDetailsDao.deleteHostGroupDetails(groupDetailsId);
    }

    @Override
    public AuthHostGroupDetails findOneHostGroupDetails(String groupDetailsId) {
        AuthHostGroupDetails  authHostGroupDetails = authHostGroupDetailsDao.findOneHostGroupDetails(groupDetailsId);
        joinTemplate.joinQuery(authHostGroupDetails);
        return authHostGroupDetails;
    }

    @Override
    public List<AuthHostGroupDetails> findAllHostGroupDetails() {
        return authHostGroupDetailsDao.findAllHostGroupDetails();
    }

    @Override
    public List<AuthHostGroupDetails> findHostGroupDetailsList(String groupId) {
        List<AuthHostGroupDetails> hostGroupDetailsList =  authHostGroupDetailsDao.findHostGroupDetailsList(groupId);
        joinTemplate.joinQuery(hostGroupDetailsList);
        return hostGroupDetailsList;
    }
    
    
    
    
    
}
