package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.execute.service.CodeAuthorizeServiceImpl;
import net.tiklab.matflow.setting.dao.PipelineAuthThirdDao;
import net.tiklab.matflow.setting.entity.PipelineAuthThirdEntity;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class PipelineAuthThirdServerImpl implements PipelineAuthThirdServer {


    @Autowired
    PipelineAuthThirdDao authServerDao;

    @Autowired
    JoinTemplate joinTemplate;


    /**
     * 创建流水线授权
     * @param pipelineAuthThird 流水线授权
     * @return 流水线授权id
     */
    public String createAuthServer(PipelineAuthThird pipelineAuthThird) {
        int authType = pipelineAuthThird.getAuthType();
        if (authType == 0){
            Map<String, PipelineAuthThird> userMap = CodeAuthorizeServiceImpl.userMap;
            String loginId = LoginContext.getLoginId();
            PipelineAuthThird authServer = userMap.get(loginId);
            pipelineAuthThird.setUsername(authServer.getUsername());
            pipelineAuthThird.setAccessToken(authServer.getAccessToken());
            pipelineAuthThird.setRefreshToken(authServer.getRefreshToken());
        }

        PipelineAuthThirdEntity pipelineAuthThirdEntity = BeanMapper.map(pipelineAuthThird, PipelineAuthThirdEntity.class);
        return authServerDao.createAuthServer(pipelineAuthThirdEntity);
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
     * @param pipelineAuthThird 信息
     */
    @Override
    public void updateAuthServer(PipelineAuthThird pipelineAuthThird) {
        PipelineAuthThirdEntity authServerEntity = BeanMapper.map(pipelineAuthThird, PipelineAuthThirdEntity.class);
        authServerDao.updateAuthServer(authServerEntity);
    }

    /**
     * 查询授权信息
     * @param authServerId id
     * @return 信息集合
     */
    @Override
    public PipelineAuthThird findOneAuthServer(String authServerId) {
        PipelineAuthThirdEntity oneAuthServer = authServerDao.findOneAuthServer(authServerId);
        PipelineAuthThird pipelineAuthThird = BeanMapper.map(oneAuthServer, PipelineAuthThird.class);
        joinTemplate.joinQuery(pipelineAuthThird);
        return pipelineAuthThird;
    }

    /**
     * 查询说有需要更新的授权信息
     * @param type 类型
     * @return 授权信息
     */
    @Override
    public List<PipelineAuthThird> findAllAuthServer(int type) {
        List<PipelineAuthThird> allAuthServer = findAllAuthServer();
        if (allAuthServer == null){
            return null;
        }
        List<PipelineAuthThird> list = new ArrayList<>();
        for (PipelineAuthThird authServer : allAuthServer) {
            if (authServer.getAuthType() == type){
                list.add(authServer);
            }
        }
        return list;
    }

    /**
     * 获取不同授权类型的源码认证
     * @param type 类型  1. gitee 2. github 3.sonar 4.nexus
     * @return 认证信息
     */
    public List<PipelineAuthThird> findAllAuthServerList(int type) {
        List<PipelineAuthThird> allAuthServer = findAllAuthServer();
        if (allAuthServer == null){
            return null;
        }
        List<PipelineAuthThird> list = new ArrayList<>();
        for (PipelineAuthThird authServer : allAuthServer) {
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
    public List<PipelineAuthThird> findAllAuthServer() {
        List<PipelineAuthThirdEntity> allAuthServer = authServerDao.findAllAuthServer();
        List<PipelineAuthThird> pipelineAuthThirds = BeanMapper.mapList(allAuthServer, PipelineAuthThird.class);
        joinTemplate.joinQuery(pipelineAuthThirds);
        return pipelineAuthThirds;
    }

    @Override
    public List<PipelineAuthThird> findAllAuthServerList(List<String> idList) {
        List<PipelineAuthThirdEntity> allAuthServerList = authServerDao.findAllAuthServerList(idList);
        return  BeanMapper.mapList(allAuthServerList, PipelineAuthThird.class);
    }


}
