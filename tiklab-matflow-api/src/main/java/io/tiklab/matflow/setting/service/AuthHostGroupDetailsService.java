package io.tiklab.matflow.setting.service;

import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.setting.model.AuthHostGroupDetails;

import java.util.List;

/**
 * @author zcamy
 */
@JoinProvider(model = AuthHostGroupDetails.class)
public interface AuthHostGroupDetailsService {


    String creatHostGroupDetails(AuthHostGroupDetails hostGroupDetails) ;


    void updateHostGroupDetails(AuthHostGroupDetails hostGroupDetails) ;


    void deleteHostGroupDetails(String groupDetailsId) ;

    @FindOne
    AuthHostGroupDetails findOneHostGroupDetails(String groupDetailsId) ;

    @FindAll
    List<AuthHostGroupDetails> findAllHostGroupDetails() ;


    List<AuthHostGroupDetails> findHostGroupDetailsList(String groupId) ;


}
