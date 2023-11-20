package io.tiklab.matflow.setting.service;

import io.tiklab.matflow.setting.model.AuthHostGroup;
import io.tiklab.matflow.setting.model.AuthHostGroupQuery;

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
