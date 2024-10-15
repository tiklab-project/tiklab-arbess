package io.tiklab.arbess.setting.service;

import io.tiklab.arbess.setting.dao.AuthHostK8sDao;
import io.tiklab.arbess.setting.entity.AuthHostK8sEntity;
import io.tiklab.arbess.setting.model.AuthHostK8s;
import io.tiklab.arbess.setting.model.AuthHostK8sQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class AuthHostK8sServiceImpl implements AuthHostK8sService {

    @Autowired
    AuthHostK8sDao authHostK8sDao;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 创建流水线授权
     * @param authHostK8s 流水线授权
     * @return 流水线授权id
     */
    @Override
    public String createAuthHostK8s(AuthHostK8s authHostK8s) {
        AuthHostK8sEntity authHostK8sEntity = BeanMapper.map(authHostK8s, AuthHostK8sEntity.class);
        authHostK8sEntity.setCreateTime(PipelineUtil.date(1));
        authHostK8sEntity.setUserId(LoginContext.getLoginId());
        return authHostK8sDao.createAuthHostK8s(authHostK8sEntity);
    }

    /**
     * 删除流水线授权
     * @param authHostK8sId 流水线授权id
     */
    @Override
    public void deleteAuthHostK8s(String authHostK8sId) {
        authHostK8sDao.deleteAuthHostK8s(authHostK8sId);
    }

    /**
     * 更新授权信息
     * @param authHostK8s 信息
     */
    @Override
    public void updateAuthHostK8s(AuthHostK8s authHostK8s) {
        int authPublic = authHostK8s.getAuthPublic();
        String authId = authHostK8s.getHostId();
        AuthHostK8s oneAuth = findOneAuthHostK8s(authId);
        int hostType = oneAuth.getAuthPublic();
        if (authPublic == 1 && hostType == 2){
            authHostK8s.setPrivateKey("");
        }
        if (authPublic == 2 && hostType == 1){
            authHostK8s.setUsername("");
            authHostK8s.setPassword("");
        }

        AuthHostK8sEntity authHostK8sEntity = BeanMapper.map(authHostK8s, AuthHostK8sEntity.class);
        authHostK8sDao.updateAuthHostK8s(authHostK8sEntity);
    }

    /**
     * 查询授权信息
     * @param authHostK8sId id
     * @return 信息集合
     */
    @Override
    public AuthHostK8s findOneAuthHostK8s(String authHostK8sId) {
        AuthHostK8sEntity oneAuthHostK8s = authHostK8sDao.findOneAuthHostK8s(authHostK8sId);
        AuthHostK8s authHostK8s = BeanMapper.map(oneAuthHostK8s, AuthHostK8s.class);
        joinTemplate.joinQuery(authHostK8s);
        return authHostK8s;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<AuthHostK8s> findAllAuthHostK8s() {
        List<AuthHostK8sEntity> allAuthHostK8sEntity = authHostK8sDao.findAllAuthHostK8s();
        if (Objects.isNull(allAuthHostK8sEntity)){
            return Collections.emptyList();
        }
        List<AuthHostK8s> authHostK8ss = BeanMapper.mapList(allAuthHostK8sEntity, AuthHostK8s.class);
        joinTemplate.joinQuery(authHostK8ss);
        return authHostK8ss;
    }

    @Override
    public List<AuthHostK8s> findAllAuthHostK8sList(List<String> idList) {
        List<AuthHostK8sEntity> allAuthHostK8sList = authHostK8sDao.findAllAuthHostK8sList(idList);
        return BeanMapper.mapList(allAuthHostK8sList, AuthHostK8s.class);
    }

    @Override
    public List<AuthHostK8s> findAuthHostK8sList(AuthHostK8sQuery hostQuery) {
        List<AuthHostK8sEntity> allAuthHostK8sList = authHostK8sDao.findAuthHostK8sList(hostQuery);
        if (Objects.isNull(allAuthHostK8sList) || allAuthHostK8sList.isEmpty()){
            return Collections.emptyList();
        }
        return BeanMapper.mapList(allAuthHostK8sList, AuthHostK8s.class);
    }

    @Override
    public Pagination<AuthHostK8s> findAuthHostK8sPage(AuthHostK8sQuery hostQuery){

        Pagination<AuthHostK8sEntity> allAuthHostK8sPage = authHostK8sDao.findAuthHostK8sPage(hostQuery);

        List<AuthHostK8sEntity> dataList = allAuthHostK8sPage.getDataList();

        List<AuthHostK8s> authHostK8ss = BeanMapper.mapList(dataList, AuthHostK8s.class);
        joinTemplate.joinQuery(authHostK8ss);

        return PaginationBuilder.build(allAuthHostK8sPage,authHostK8ss);
    }


    @Override
    public Integer findHostNumber() {
        return authHostK8sDao.findHostNumber();
    }
    
}
