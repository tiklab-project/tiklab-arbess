package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.achieve.server.CodeThirdServiceImpl;
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
        Map<String, PipelineAuthThird> userMap = CodeThirdServiceImpl.userMap;
        PipelineAuthThird authServer = userMap.get(LoginContext.getLoginId());
        if (authServer != null){
            pipelineAuthThird.setUsername(authServer.getUsername());
            pipelineAuthThird.setRefreshToken(authServer.getRefreshToken());
            pipelineAuthThird.setAccessToken(authServer.getAccessToken());
            userMap.remove(LoginContext.getLoginId());
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
        int authPublic = pipelineAuthThird.getAuthPublic();
        String serverId = pipelineAuthThird.getServerId();
        PipelineAuthThird oneAuthServer = findOneAuthServer(serverId);
        int aPublic = oneAuthServer.getAuthPublic();
        if (authPublic == 1 && aPublic == 2){
            pipelineAuthThird.setPrivateKey("");
        }
        if (authPublic == 2 && aPublic == 1){
            pipelineAuthThird.setUsername("");
            pipelineAuthThird.setPassword("");
        }
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
     * 获取不同授权类型的源码认证
     * @param type 类型  1. gitee 2. GitHub 3.sonar 4.nexus
     * @return 认证信息
     */
    public List<PipelineAuthThird> findAllAuthServerList(int type) {
        List<PipelineAuthThird> allAuthServer = findAllAuthServer();
        if (allAuthServer == null){
            return null;
        }
        if (type == 0){
            return allAuthServer;
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
        if (allAuthServer == null){
            return null;
        }
        //获取公共的和用户私有的
        List<PipelineAuthThirdEntity> allAuthServerEntity = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        for (PipelineAuthThirdEntity thirdEntity : allAuthServer) {
            if (thirdEntity.getUserId().equals(loginId) || thirdEntity.getAuthPublic() == 1){
                allAuthServerEntity.add(thirdEntity);
            }
        }
        List<PipelineAuthThird> pipelineAuthThirds = BeanMapper.mapList(allAuthServerEntity, PipelineAuthThird.class);
        joinTemplate.joinQuery(pipelineAuthThirds);
        return pipelineAuthThirds;
    }

    @Override
    public List<PipelineAuthThird> findAllAuthServerList(List<String> idList) {
        List<PipelineAuthThirdEntity> allAuthServerList = authServerDao.findAllAuthServerList(idList);
        return  BeanMapper.mapList(allAuthServerList, PipelineAuthThird.class);
    }


}
