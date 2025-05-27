package io.tiklab.arbess.setting.hostgroup.service;

import io.tiklab.arbess.setting.hostgroup.model.AuthHostGroup;
import io.tiklab.arbess.setting.hostgroup.model.AuthHostGroupQuery;
import io.tiklab.arbess.setting.hostgroup.model.HostGroup;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindOne;

import java.util.List;

/**
 * @author zcamy
 */

public interface AuthHostGroupService {

     /**
      * 创建主机组
      * @param hostGroup hostGroup
      * @return id
      */
     String creatHostGroup(AuthHostGroup hostGroup) ;

     /**
      * 更新主机组
      * @param hostGroup hostGroup
      */
     void updateHostGroup(AuthHostGroup hostGroup) ;

     /**
      * 删除主机组
      * @param groupId groupId
      */
     void deleteHostGroup(String groupId) ;

     /**
      * 查找主机组
      * @param groupId groupId
      * @return hostGroup
      */
     @FindOne
     AuthHostGroup findOneHostGroup(String groupId) ;

     /**
      * 查找主机组
      * @param groupId groupId
      * @param strategyNumber strategyNumber
      * @param strategyType strategyType
      * @return hostGroup
      */
     List<HostGroup> findOneHostGroupByGroup(String groupId, Integer strategyNumber, String strategyType);

     /**
      * 查找主机组
      * @return hostGroup
      */
     @FindAll
     List<AuthHostGroup> findAllHostGroup() ;

     /**
      * 查找主机组
      * @param groupQuery groupQuery
      * @return hostGroup
      */
     List<AuthHostGroup> findHostGroupList(AuthHostGroupQuery groupQuery) ;

     /**
      * 查找主机组数量
      * @return count
      */
     Integer findHostGroupNumber();
    
    
}
