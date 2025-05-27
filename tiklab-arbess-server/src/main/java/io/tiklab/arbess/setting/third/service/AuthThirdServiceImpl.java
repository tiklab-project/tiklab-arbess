package io.tiklab.arbess.setting.third.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.setting.third.dao.AuthThirdDao;
import io.tiklab.arbess.setting.third.entity.AuthThirdEntity;
import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.third.model.AuthThirdQuery;
import io.tiklab.rpc.annotation.Exporter;
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

    @Override
    public List<AuthThird> findAuthServerList(AuthThirdQuery thirdQuery){
        List<AuthThirdEntity> authThirdEntityList = authServerDao.findAuthServerList(thirdQuery);
        List<AuthThird> authThirds = BeanMapper.mapList(authThirdEntityList, AuthThird.class);
        joinTemplate.joinQuery(authThirds);
        return authThirds;
    }

    @Override
    public Pagination<AuthThird> findAuthServerPage(AuthThirdQuery thirdQuery){
        Pagination<AuthThirdEntity> authThirdEntityPage = authServerDao.findAuthServerPage(thirdQuery);

        List<AuthThirdEntity> dataList = authThirdEntityPage.getDataList();
        if (Objects.isNull(dataList)){
            return PaginationBuilder.build(authThirdEntityPage,Collections.emptyList());
        }
        List<AuthThird> authThirds = BeanMapper.mapList(dataList, AuthThird.class);
        joinTemplate.joinQuery(authThirds);
        return PaginationBuilder.build(authThirdEntityPage,authThirds);
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<AuthThird> findAllAuthServer() {
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
