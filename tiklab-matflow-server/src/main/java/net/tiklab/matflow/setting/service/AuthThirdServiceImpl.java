package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.model.AuthThird;
import net.tiklab.matflow.task.code.service.TaskCodeThirdServiceImpl;
import net.tiklab.matflow.setting.dao.AuthThirdDao;
import net.tiklab.matflow.setting.entity.AuthThirdEntity;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class AuthThirdServiceImpl implements AuthThirdService {


    @Autowired
    AuthThirdDao authServerDao;

    @Autowired
    JoinTemplate joinTemplate;


    /**
     * 创建流水线授权
     * @param authThird 流水线授权
     * @return 流水线授权id
     */
    public String createAuthServer(AuthThird authThird) {
        Map<String, AuthThird> userMap = TaskCodeThirdServiceImpl.userMap;
        AuthThird authServer = userMap.get(LoginContext.getLoginId());
        if (authServer != null){
            authThird.setUsername(authServer.getUsername());
            authThird.setRefreshToken(authServer.getRefreshToken());
            authThird.setAccessToken(authServer.getAccessToken());
            userMap.remove(LoginContext.getLoginId());
        }
        AuthThirdEntity authThirdEntity = BeanMapper.map(authThird, AuthThirdEntity.class);
        return authServerDao.createAuthServer(authThirdEntity);
    }

    /**
     * 删除流水线授权
     * @param authServerId 流水线授权id
     */
    @Override
    public void deleteAuthServer(String authServerId) {
        authServerDao.deleteAuthServer(authServerId);
    }

    /**
     * 更新授权信息
     * @param authThird 信息
     */
    @Override
    public void updateAuthServer(AuthThird authThird) {
        int authPublic = authThird.getAuthPublic();
        String serverId = authThird.getServerId();
        AuthThird oneAuthServer = findOneAuthServer(serverId);
        int aPublic = oneAuthServer.getAuthPublic();
        if (authPublic == 1 && aPublic == 2){
            authThird.setPrivateKey("");
        }
        if (authPublic == 2 && aPublic == 1){
            authThird.setUsername("");
            authThird.setPassword("");
        }
        AuthThirdEntity authServerEntity = BeanMapper.map(authThird, AuthThirdEntity.class);
        authServerDao.updateAuthServer(authServerEntity);
    }

    /**
     * 查询授权信息
     * @param authServerId id
     * @return 信息集合
     */
    @Override
    public AuthThird findOneAuthServer(String authServerId) {
        AuthThirdEntity oneAuthServer = authServerDao.findOneAuthServer(authServerId);
        AuthThird authThird = BeanMapper.map(oneAuthServer, AuthThird.class);
        joinTemplate.joinQuery(authThird);
        return authThird;
    }

    /**
     * 获取不同授权类型的源码认证
     * @param type 类型  1. gitee 2. GitHub 3.sonar 4.nexus
     * @return 认证信息
     */
    public List<AuthThird> findAllAuthServerList(int type) {
        List<AuthThird> allAuthServer = findAllAuthServer();
        if (allAuthServer == null){
            return null;
        }
        if (type == 0){
            return allAuthServer;
        }
        List<AuthThird> list = new ArrayList<>();
        for (AuthThird authServer : allAuthServer) {
            if (authServer.getType() != type ){
                continue;
            }
            list.add(authServer);
        }
        return list;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<AuthThird> findAllAuthServer() {
        List<AuthThirdEntity> allAuthServer = authServerDao.findAllAuthServer();
        if (allAuthServer == null){
            return null;
        }
        //获取公共的和用户私有的
        List<AuthThirdEntity> allAuthServerEntity = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        for (AuthThirdEntity thirdEntity : allAuthServer) {
            if (thirdEntity.getUserId().equals(loginId) || thirdEntity.getAuthPublic() == 1){
                allAuthServerEntity.add(thirdEntity);
            }
        }
        List<AuthThird> authThirds = BeanMapper.mapList(allAuthServerEntity, AuthThird.class);
        joinTemplate.joinQuery(authThirds);
        return authThirds;
    }

    @Override
    public List<AuthThird> findAllAuthServerList(List<String> idList) {
        List<AuthThirdEntity> allAuthServerList = authServerDao.findAllAuthServerList(idList);
        return  BeanMapper.mapList(allAuthServerList, AuthThird.class);
    }


}
