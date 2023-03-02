package net.tiklab.matflow.setting.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.dao.AuthDao;
import net.tiklab.matflow.setting.entity.AuthEntity;
import net.tiklab.matflow.setting.model.*;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
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
        List<AuthEntity> allAuth = authDao.findAllAuth();
        if (allAuth == null){
            return null;
        }
        //获取公共的和用户私有的
        List<AuthEntity> allAuthEntity = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        for (AuthEntity authEntity : allAuth) {
            if (authEntity.getUserId().equals(loginId)
                    || authEntity.getAuthPublic() == 1){
                allAuthEntity.add(authEntity);
            }
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
