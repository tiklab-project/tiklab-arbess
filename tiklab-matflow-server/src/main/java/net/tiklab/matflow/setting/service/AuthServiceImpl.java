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

@Service
@Exporter
public class AuthServiceImpl implements AuthService {
   
    
    @Autowired
    AuthDao authDao;

    @Autowired
    JoinTemplate joinTemplate;


    /**
     * 创建流水线授权
     * @param auth 流水线授权
     * @return 流水线授权id
     */
    public String createAuth(Auth auth) {
        AuthEntity authEntity = BeanMapper.map(auth, AuthEntity.class);
        return authDao.createAuth(authEntity);
    }


    /**
     * 删除流水线授权
     * @param authId 流水线授权id
     */
    @Override
    public void deleteAuth(String authId) {
        authDao.deleteAuth(authId);
    }

    /**
     * 更新授权信息
     * @param auth 信息
     */
    @Override
    public void updateAuth(Auth auth) {
        String authId = auth.getAuthId();
        Auth oneAuth = findOneAuth(authId);
        int authPublic = auth.getAuthPublic();
        int oneAuthAuth = oneAuth.getAuthPublic();
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

    /**
     * 查询授权信息
     * @param authId id
     * @return 信息集合
     */
    @Override
    public Auth findOneAuth(String authId) {
        AuthEntity oneAuth = authDao.findOneAuth(authId);
        Auth auth = BeanMapper.map(oneAuth, Auth.class);
        joinTemplate.joinQuery(auth);
        return auth;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
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
            if (authEntity.getUserId().equals(loginId) || authEntity.getAuthPublic() == 1){
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
