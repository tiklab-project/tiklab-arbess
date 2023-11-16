package io.tiklab.matflow.setting.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.setting.dao.AuthHostDao;
import io.tiklab.matflow.setting.entity.AuthHostEntity;
import io.tiklab.matflow.setting.model.AuthHost;
import io.tiklab.matflow.setting.model.AuthHostQuery;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class AuthHostServiceImpl implements AuthHostService {

    @Autowired
    AuthHostDao authHostDao;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 创建流水线授权
     * @param authHost 流水线授权
     * @return 流水线授权id
     */
    @Override
    public String createAuthHost(AuthHost authHost) {
        AuthHostEntity authHostEntity = BeanMapper.map(authHost, AuthHostEntity.class);
        authHostEntity.setCreateTime(PipelineUtil.date(1));
        authHostEntity.setUserId(LoginContext.getLoginId());
        return authHostDao.createAuthHost(authHostEntity);
    }

    /**
     * 删除流水线授权
     * @param authHostId 流水线授权id
     */
    @Override
    public void deleteAuthHost(String authHostId) {
        authHostDao.deleteAuthHost(authHostId);
    }

    /**
     * 更新授权信息
     * @param authHost 信息
     */
    @Override
    public void updateAuthHost(AuthHost authHost) {
        int authPublic = authHost.getAuthPublic();
        String authId = authHost.getHostId();
        AuthHost oneAuth = findOneAuthHost(authId);
        int hostType = oneAuth.getAuthPublic();
        if (authPublic == 1 && hostType == 2){
            authHost.setPrivateKey("");
        }
        if (authPublic == 2 && hostType == 1){
            authHost.setUsername("");
            authHost.setPassword("");
        }

        AuthHostEntity authHostEntity = BeanMapper.map(authHost, AuthHostEntity.class);
        authHostDao.updateAuthHost(authHostEntity);
    }

    /**
     * 查询授权信息
     * @param authHostId id
     * @return 信息集合
     */
    @Override
    public AuthHost findOneAuthHost(String authHostId) {
        AuthHostEntity oneAuthHost = authHostDao.findOneAuthHost(authHostId);
        AuthHost authHost = BeanMapper.map(oneAuthHost, AuthHost.class);
        joinTemplate.joinQuery(authHost);
        return authHost;
    }

    /**
     * 查询单个类型所有配置
     * @param type 类型
     * @return 配置
     */
    public List<AuthHost> findAllAuthHostList(String type) {
        List<AuthHost> allAuthHost = findAllAuthHost();
        if (allAuthHost == null){
            return null;
        }
        List<AuthHost> list = new ArrayList<>();
        if (Objects.isNull(type) || type.equals("all")){
            for (AuthHost authHost : allAuthHost) {
                String oneType = findOneType(authHost.getType());
                authHost.setType(oneType);
                list.add(authHost);
            }
            return list;
        }

        for (AuthHost authHost : allAuthHost) {
            String type1 = authHost.getType();
            if (type.equals(type1)){
                list.add(authHost);
                continue;
            }
            boolean b = findType(type, type1);
            if (b){
                authHost.setType(type);
                list.add(authHost);
            }
        }
        return list;
    }

    private String findOneType(String type){
        if (type.equals("31")) {
            return "common";
        }
        return type;
    }

    private boolean findType(String type,String taskType){
        switch (type){
            case "common"  ->{
                if (taskType.equals("31")|| taskType.equals("common")){
                    return true;
                }
            }
            case "aliyun" ,"tencent" ->{
                if (taskType.equals(type)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<AuthHost> findAllAuthHost() {
        List<AuthHostEntity> allAuthHost = authHostDao.findAllAuthHost();
        if (allAuthHost == null){
            return null;
        }
        //获取公共的和用户私有的
        List<AuthHostEntity> allAuthHostEntity = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        for (AuthHostEntity authHostEntity : allAuthHost) {
            if (authHostEntity.getUserId().equals(loginId) || authHostEntity.getAuthPublic() ==1){
                allAuthHostEntity.add(authHostEntity);
            }
        }
        List<AuthHost> authHosts = BeanMapper.mapList(allAuthHostEntity, AuthHost.class);
        joinTemplate.joinQuery(authHosts);
        return authHosts;
    }

    @Override
    public List<AuthHost> findAllAuthHostList(List<String> idList) {
        List<AuthHostEntity> allAuthHostList = authHostDao.findAllAuthHostList(idList);
        return BeanMapper.mapList(allAuthHostList, AuthHost.class);
    }

    @Override
    public Pagination<AuthHost> findAuthHostPage(AuthHostQuery hostQuery){

        Pagination<AuthHostEntity> allAuthHostPage = authHostDao.findAuthHostPage(hostQuery);

        List<AuthHostEntity> dataList = allAuthHostPage.getDataList();

        List<AuthHost> authHosts = BeanMapper.mapList(dataList, AuthHost.class);

        return PaginationBuilder.build(allAuthHostPage,authHosts);
    }
    
}
