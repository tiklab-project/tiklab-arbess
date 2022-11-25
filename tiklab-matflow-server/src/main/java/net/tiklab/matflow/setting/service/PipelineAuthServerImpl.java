package net.tiklab.matflow.setting.service;


import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.dao.PipelineAuthDao;
import net.tiklab.matflow.setting.entity.PipelineAuthEntity;
import net.tiklab.matflow.setting.model.*;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineAuthServerImpl implements  PipelineAuthServer {
   
    
    @Autowired
    PipelineAuthDao authDao;

    @Autowired
    JoinTemplate joinTemplate;


    /**
     * 创建流水线授权
     * @param pipelineAuth 流水线授权
     * @return 流水线授权id
     */
    public String createAuth(PipelineAuth pipelineAuth) {
        PipelineAuthEntity pipelineAuthEntity = BeanMapper.map(pipelineAuth, PipelineAuthEntity.class);
        return authDao.createAuth(pipelineAuthEntity);
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
     * @param pipelineAuth 信息
     */
    @Override
    public void updateAuth(PipelineAuth pipelineAuth) {
        String authId = pipelineAuth.getAuthId();
        PipelineAuth oneAuth = findOneAuth(authId);
        int authPublic = pipelineAuth.getAuthPublic();
        int oneAuthAuth = oneAuth.getAuthPublic();
        if (authPublic == 2 && oneAuthAuth == 1){
            pipelineAuth.setUsername("");
            pipelineAuth.setPassword("");
        }
        if (authPublic == 1 && oneAuthAuth == 2){
            pipelineAuth.setPrivateKey("");
        }
        PipelineAuthEntity authEntity = BeanMapper.map(pipelineAuth, PipelineAuthEntity.class);
        authDao.updateAuth(authEntity);
    }

    /**
     * 查询授权信息
     * @param authId id
     * @return 信息集合
     */
    @Override
    public PipelineAuth findOneAuth(String authId) {
        PipelineAuthEntity oneAuth = authDao.findOneAuth(authId);
        PipelineAuth pipelineAuth = BeanMapper.map(oneAuth, PipelineAuth.class);
        joinTemplate.joinQuery(pipelineAuth);
        return pipelineAuth;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineAuth> findAllAuth() {
        List<PipelineAuthEntity> allAuth = authDao.findAllAuth();
        if (allAuth == null){
            return null;
        }
        //获取公共的和用户私有的
        List<PipelineAuthEntity> allAuthEntity = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        for (PipelineAuthEntity authEntity : allAuth) {
            if (authEntity.getUserId().equals(loginId) || authEntity.getAuthPublic() == 1){
                allAuthEntity.add(authEntity);
            }
        }
        List<PipelineAuth> pipelineAuths = BeanMapper.mapList(allAuthEntity, PipelineAuth.class);
        joinTemplate.joinQuery(pipelineAuths);
        return pipelineAuths;
    }

    @Override
    public List<PipelineAuth> findAllAuthList(List<String> idList) {
        List<PipelineAuthEntity> allAuthList = authDao.findAllAuthList(idList);
        return  BeanMapper.mapList(allAuthList, PipelineAuth.class);
    }
   

    
}
