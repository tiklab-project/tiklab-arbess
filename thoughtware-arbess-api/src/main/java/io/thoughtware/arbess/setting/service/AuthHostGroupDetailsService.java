package io.thoughtware.arbess.setting.service;

import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.arbess.setting.model.AuthHostGroupDetails;

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
