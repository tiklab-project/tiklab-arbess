package io.tiklab.matflow.setting.service;


import io.tiklab.beans.BeanMapper;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.setting.dao.AuthDao;
import io.tiklab.matflow.setting.entity.AuthMatFlowEntity;
import io.tiklab.matflow.setting.model.Auth;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * 流水线基本认证服务
 */
@Service
@Exporter
public class AuthServiceImpl implements AuthService {
   
    
    @Autowired
    AuthDao authDao;

    @Autowired
    JoinTemplate joinTemplate;

    public String createAuth(Auth auth) {
        AuthMatFlowEntity authMatFlowEntity = BeanMapper.map(auth, AuthMatFlowEntity.class);
        return authDao.createAuth(authMatFlowEntity);
    }


    @Override
    public void deleteAuth(String authId) {
        authDao.deleteAuth(authId);
    }

    @Override
    public void updateAuth(Auth auth) {
        String authId = auth.getAuthId();
        Auth oneAuth = findOneAuth(authId);
        int authPublic = auth.getAuthPublic();
        int oneAuthAuth = oneAuth.getAuthPublic();
        //判断是否切换类型
        if (authPublic == 2 && oneAuthAuth == 1){
            auth.setUsername("");
            auth.setPassword("");
        }
        if (authPublic == 1 && oneAuthAuth == 2){
            auth.setPrivateKey("");
        }
        AuthMatFlowEntity authMatFlowEntity = BeanMapper.map(auth, AuthMatFlowEntity.class);
        authDao.updateAuth(authMatFlowEntity);
    }

    @Override
    public Auth findOneAuth(String authId) {
        AuthMatFlowEntity oneAuth = authDao.findOneAuth(authId);
        Auth auth = BeanMapper.map(oneAuth, Auth.class);
        joinTemplate.joinQuery(auth);
        return auth;
    }

    @Override
    public List<Auth> findAllAuth() {
        List<AuthMatFlowEntity> allAuth = authDao.findAllAuth();
        if (allAuth == null){
            return null;
        }
        //获取公共的和用户私有的
        List<AuthMatFlowEntity> allAuthMatFlowEntity = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        for (AuthMatFlowEntity authMatFlowEntity : allAuth) {
            if (authMatFlowEntity.getUserId().equals(loginId)
                    || authMatFlowEntity.getAuthPublic() == 1){
                allAuthMatFlowEntity.add(authMatFlowEntity);
            }
        }
        List<Auth> auths = BeanMapper.mapList(allAuthMatFlowEntity, Auth.class);
        joinTemplate.joinQuery(auths);
        return auths;
    }

    @Override
    public List<Auth> findAllAuthList(List<String> idList) {
        List<AuthMatFlowEntity> allAuthList = authDao.findAllAuthList(idList);
        return  BeanMapper.mapList(allAuthList, Auth.class);
    }
   

    
}
