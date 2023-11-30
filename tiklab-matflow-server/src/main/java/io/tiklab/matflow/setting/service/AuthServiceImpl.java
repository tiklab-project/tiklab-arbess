package io.tiklab.matflow.setting.service;


import io.tiklab.beans.BeanMapper;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.setting.dao.AuthDao;
import io.tiklab.matflow.setting.entity.AuthEntity;
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
        AuthEntity authEntity = BeanMapper.map(auth, AuthEntity.class);
        return authDao.createAuth(authEntity);
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
        AuthEntity authEntity = BeanMapper.map(auth, AuthEntity.class);
        authDao.updateAuth(authEntity);
    }

    @Override
    public Auth findOneAuth(String authId) {
        AuthEntity oneAuth = authDao.findOneAuth(authId);
        Auth auth = BeanMapper.map(oneAuth, Auth.class);
        joinTemplate.joinQuery(auth);
        return auth;
    }

    @Override
    public List<Auth> findAllAuth() {
        List<AuthEntity> allAuthEntity = authDao.findAllAuth();
        if (allAuthEntity == null){
            return null;
        }
        List<Auth> auths = BeanMapper.mapList(allAuthEntity, Auth.class);
        joinTemplate.joinQuery(auths);
        return auths;
    }

    @Override
    public List<Auth> findAllAuthList(List<String> idList) {
        List<AuthEntity> allAuthList = authDao.findAllAuthList(idList);
        return  BeanMapper.mapList(allAuthList, Auth.class);
    }
   

    
}
