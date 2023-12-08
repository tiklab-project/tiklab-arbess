package io.thoughtware.matflow.setting.service;

import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;
import io.thoughtware.matflow.setting.model.AuthHostGroupDetails;

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
