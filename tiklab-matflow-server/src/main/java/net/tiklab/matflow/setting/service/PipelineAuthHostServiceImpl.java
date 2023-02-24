package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.dao.PipelineAuthHostDao;
import net.tiklab.matflow.setting.entity.PipelineAuthHostEntity;
import net.tiklab.matflow.setting.model.PipelineAuthHost;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineAuthHostServiceImpl implements PipelineAuthHostService {

    @Autowired
    PipelineAuthHostDao authHostDao;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 创建流水线授权
     * @param pipelineAuthHost 流水线授权
     * @return 流水线授权id
     */
    @Override
    public String createAuthHost(PipelineAuthHost pipelineAuthHost) {
        PipelineAuthHostEntity pipelineAuthHostEntity = BeanMapper.map(pipelineAuthHost, PipelineAuthHostEntity.class);
        return authHostDao.createAuthHost(pipelineAuthHostEntity);
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
     * @param pipelineAuthHost 信息
     */
    @Override
    public void updateAuthHost(PipelineAuthHost pipelineAuthHost) {
        int authPublic = pipelineAuthHost.getAuthPublic();
        String authId = pipelineAuthHost.getHostId();
        PipelineAuthHost oneAuth = findOneAuthHost(authId);
        int hostType = oneAuth.getAuthPublic();
        if (authPublic == 1 && hostType == 2){
            pipelineAuthHost.setPrivateKey("");
        }
        if (authPublic == 2 && hostType == 1){
            pipelineAuthHost.setUsername("");
            pipelineAuthHost.setPassword("");
        }

        PipelineAuthHostEntity authHostEntity = BeanMapper.map(pipelineAuthHost, PipelineAuthHostEntity.class);
        authHostDao.updateAuthHost(authHostEntity);
    }

    /**
     * 查询授权信息
     * @param authHostId id
     * @return 信息集合
     */
    @Override
    public PipelineAuthHost findOneAuthHost(String authHostId) {
        PipelineAuthHostEntity oneAuthHost = authHostDao.findOneAuthHost(authHostId);
        PipelineAuthHost pipelineAuthHost = BeanMapper.map(oneAuthHost, PipelineAuthHost.class);
        joinTemplate.joinQuery(pipelineAuthHost);
        return pipelineAuthHost;
    }

    /**
     * 查询单个类型所有配置
     * @param type 类型
     * @return 配置
     */
    public List<PipelineAuthHost> findAllAuthHostList(int type) {
        List<PipelineAuthHost> allAuthHost = findAllAuthHost();
        if (allAuthHost == null){
            return null;
        }
        if (type == 0){
            return allAuthHost;
        }
        List<PipelineAuthHost> list = new ArrayList<>();
        for (PipelineAuthHost pipelineAuthHost : allAuthHost) {
            if (pipelineAuthHost.getType() == type){
                list.add(pipelineAuthHost);
            }
        }
        return list;
    }


    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineAuthHost> findAllAuthHost() {
        List<PipelineAuthHostEntity> allAuthHost = authHostDao.findAllAuthHost();
        if (allAuthHost == null){
            return null;
        }
        //获取公共的和用户私有的
        List<PipelineAuthHostEntity> allAuthHostEntity = new ArrayList<>();
        String loginId = LoginContext.getLoginId();
        for (PipelineAuthHostEntity authHostEntity : allAuthHost) {
            if (authHostEntity.getUserId().equals(loginId) || authHostEntity.getAuthPublic() ==1){
                allAuthHostEntity.add(authHostEntity);
            }
        }
        List<PipelineAuthHost> pipelineAuthHosts = BeanMapper.mapList(allAuthHostEntity, PipelineAuthHost.class);
        joinTemplate.joinQuery(pipelineAuthHosts);
        return pipelineAuthHosts;
    }

    @Override
    public List<PipelineAuthHost> findAllAuthHostList(List<String> idList) {
        List<PipelineAuthHostEntity> allAuthHostList = authHostDao.findAllAuthHostList(idList);
        return BeanMapper.mapList(allAuthHostList, PipelineAuthHost.class);
    }
    
}
