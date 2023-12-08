package io.thoughtware.matflow.setting.service;

import io.thoughtware.matflow.setting.model.AuthHostGroup;
import io.thoughtware.matflow.setting.model.AuthHostGroupQuery;

import java.util.List;

/**
 * @author zcamy
 */

public interface AuthHostGroupService {

     
     String creatHostGroup(AuthHostGroup hostGroup) ;

    
     void updateHostGroup(AuthHostGroup hostGroup) ;

    
     void deleteHostGroup(String groupId) ;

    
     AuthHostGroup findOneHostGroup(String groupId) ;

    
     List<AuthHostGroup> findAllHostGroup() ;

    
     List<AuthHostGroup> findHostGroupList(AuthHostGroupQuery groupQuery) ;
    
    
}
