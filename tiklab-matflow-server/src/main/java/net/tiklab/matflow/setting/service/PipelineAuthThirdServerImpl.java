package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.setting.dao.PipelineAuthThirdDao;
import net.tiklab.matflow.setting.entity.PipelineAuthThirdEntity;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class PipelineAuthThirdServerImpl implements PipelineAuthThirdServer{


    @Autowired
    PipelineAuthThirdDao authThirdDao;

    @Autowired
    JoinTemplate joinTemplate;

    /**
     * 完善授权信息流水线授权
     * @param pipelineAuthThird 流水线授权
     * @return 流水线授权id
     */
    // @Override
    // public String findAuthThird(PipelineAuthThird pipelineAuthThird) {
    //     Map<String, Map<String, String>> userMap = CodeAuthorizeServiceImpl.userMap;
    //     Map<String, String> map = userMap.get(LoginContext.getLoginId());
    //     String accessToken = map.get("accessToken");
    //     String refreshToken = map.get("refreshToken");
    //     String authThirdId = map.get("authThirdId");
    //     pipelineAuthThird.setThirdId(authThirdId);
    //     pipelineAuthThird.setAccessToken(accessToken);
    //     pipelineAuthThird.setRefreshToken(refreshToken);
    //     return authThirdId;
    // }

    /**
     * 创建流水线授权
     * @param pipelineAuthThird 流水线授权
     * @return 流水线授权id
     */
    public String createAuthThird(PipelineAuthThird pipelineAuthThird) {
        PipelineAuthThirdEntity pipelineAuthThirdEntity = BeanMapper.map(pipelineAuthThird, PipelineAuthThirdEntity.class);
        return authThirdDao.createAuthThird(pipelineAuthThirdEntity);
    }


    /**
     * 删除流水线授权
     * @param authThirdId 流水线授权id
     */
    @Override
    public void deleteAuthThird(String authThirdId) {
        authThirdDao.deleteAuthThird(authThirdId);
    }

    /**
     * 更新授权信息
     * @param pipelineAuthThird 信息
     */
    @Override
    public void updateAuthThird(PipelineAuthThird pipelineAuthThird) {
        PipelineAuthThirdEntity authThirdEntity = BeanMapper.map(pipelineAuthThird, PipelineAuthThirdEntity.class);
        authThirdDao.updateAuthThird(authThirdEntity);
    }

    /**
     * 查询授权信息
     * @param authThirdId id
     * @return 信息集合
     */
    @Override
    public PipelineAuthThird findOneAuthThird(String authThirdId) {
        PipelineAuthThirdEntity oneAuthThird = authThirdDao.findOneAuthThird(authThirdId);
        PipelineAuthThird pipelineAuthThird = BeanMapper.map(oneAuthThird, PipelineAuthThird.class);
        joinTemplate.joinQuery(pipelineAuthThird);
        return pipelineAuthThird;
    }

    /**
     * 查询需要更新的授权信息
     * @param type 授权类型
     * @return 授权信息
     */
    @Override
    public List<PipelineAuthThird> findAllAuthThird(int type){
        List<PipelineAuthThird> arrayList = new ArrayList<>();
        List<PipelineAuthThird> allAuthThird = findAllAuthThird();
        if (allAuthThird == null){
            return null;
        }
        for (PipelineAuthThird authThird : allAuthThird) {
            if (authThird.getAuthType() != 2){
                continue;
            }
            arrayList.add(authThird);
        }
        return arrayList;
    }

    /**
     * 查询所有流水线授权
     * @return 流水线授权列表
     */
    @Override
    public List<PipelineAuthThird> findAllAuthThird() {
        List<PipelineAuthThirdEntity> allAuthThird = authThirdDao.findAllAuthThird();
        List<PipelineAuthThird> pipelineAuthThirds = BeanMapper.mapList(allAuthThird, PipelineAuthThird.class);
        joinTemplate.joinQuery(pipelineAuthThirds);
        return pipelineAuthThirds;
    }

    @Override
    public List<PipelineAuthThird> findAllAuthThirdList(List<String> idList) {
        List<PipelineAuthThirdEntity> allAuthThirdList = authThirdDao.findAllAuthThirdList(idList);
        return  BeanMapper.mapList(allAuthThirdList, PipelineAuthThird.class);
    }


}
