package io.thoughtware.matflow.setting.service;

import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.matflow.setting.dao.AuthThirdDao;
import io.thoughtware.matflow.setting.entity.AuthThirdEntity;
import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.model.AuthThirdQuery;
import io.thoughtware.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

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
        if (aPublic == 2 && authPublic == 1){
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
    public List<AuthThird> findAllAuthServerList(String type) {
        List<AuthThird> allAuthServer = findAllAuthServer();
        if (allAuthServer == null){
            return null;
        }

        List<AuthThird> list = new ArrayList<>();

        if (Objects.isNull(type) || "all".equals(type)){
            for (AuthThird authThird : allAuthServer) {
                authThird.setType(authThird.getType());
                list.add(authThird);
            }
            return list;
        }

        for (AuthThird authServer : allAuthServer) {
            String type1 = authServer.getType();
            if (type.equals(type1)){
                list.add(authServer);
                continue;
            }
            authServer.setType(type);
            list.add(authServer);
        }
        return list;
    }


    @Override
    public List<AuthThird> findAuthServerList(AuthThirdQuery thirdQuery){
        String type = thirdQuery.getType();
        if (type.equals("all")){
            thirdQuery.setType(null);
        }
        List<AuthThirdEntity> authThirdEntityList = authServerDao.findAuthServerList(thirdQuery);
        List<AuthThird> authThirds = BeanMapper.mapList(authThirdEntityList, AuthThird.class);
        joinTemplate.joinQuery(authThirds);
        return authThirds;
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


    public List<AuthThird> findAllAuthServerList() {
        List<AuthThirdEntity> allAuthServer = authServerDao.findAllAuthServer();
        if (Objects.isNull(allAuthServer)){
            return Collections.emptyList();
        }
        List<AuthThird> authThirds = BeanMapper.mapList(allAuthServer, AuthThird.class);
        joinTemplate.joinQuery(authThirds);
        return authThirds;
    }

    @Override
    public List<AuthThird> findAllAuthServerList(List<String> idList) {
        List<AuthThirdEntity> allAuthServerList = authServerDao.findAllAuthServerList(idList);
        return  BeanMapper.mapList(allAuthServerList, AuthThird.class);
    }

    @Override
    public Integer findAuthServerNumber() {
        return authServerDao.findAuthServerNumber();
    }


}
