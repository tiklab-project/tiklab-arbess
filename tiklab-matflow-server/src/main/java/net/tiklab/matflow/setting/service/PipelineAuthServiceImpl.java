package net.tiklab.matflow.setting.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.setting.dao.PipelineAuthDao;
import net.tiklab.matflow.setting.entity.PipelineAuthEntity;
import net.tiklab.matflow.setting.model.PipelineAuth;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Exporter
public class PipelineAuthServiceImpl implements  PipelineAuthService{

    @Autowired
    PipelineAuthDao AuthDao;

    /**
     * 创建流水线认证
     * @param pipelineAuth 流水线认证
     * @return 流水线认证id
     */
    @Override
    public String createAuth(PipelineAuth pipelineAuth) {
        PipelineAuthEntity pipelineAuthEntity = BeanMapper.map(pipelineAuth, PipelineAuthEntity.class);
        return AuthDao.createAuth(pipelineAuthEntity);
    }

    /**
     * 删除流水线认证
     * @param AuthId 流水线认证id
     */
    @Override
    public void deleteAuth(String AuthId) {
        AuthDao.deleteAuth(AuthId);
    }

    /**
     * 更新认证信息
     * @param pipelineAuth 信息
     */
    @Override
    public void updateAuth(PipelineAuth pipelineAuth) {
        PipelineAuthEntity AuthEntity = BeanMapper.map(pipelineAuth, PipelineAuthEntity.class);
        AuthDao.updateAuth(AuthEntity);
    }

    /**
     * 查询认证信息
     * @param AuthId id
     * @return 信息集合
     */
    @Override
    public PipelineAuth findOneAuth(String AuthId) {
        PipelineAuthEntity oneAuth = AuthDao.findOneAuth(AuthId);
        return BeanMapper.map(oneAuth, PipelineAuth.class);
    }

    /**
     * 根据类型查询认证信息
     * @param type 类型
     * @return 认证
     */
    @Override
    public PipelineAuth findOneAuth(int type){
        List<PipelineAuth> allAuth = findAllAuth();
        if (allAuth == null){
            return null;
        }
        for (PipelineAuth pipelineAuth : allAuth) {
            if (pipelineAuth.getType() == type){
                return pipelineAuth;
            }
        }
        return null;
    }

    /**
     * 查询所有流水线认证
     * @return 流水线认证列表
     */
    @Override
    public List<PipelineAuth> findAllAuth() {
        List<PipelineAuthEntity> allAuth = AuthDao.findAllAuth();
        return BeanMapper.mapList(allAuth, PipelineAuth.class);
    }

    @Override
    public List<PipelineAuth> findAllAuthList(List<String> idList) {
        List<PipelineAuthEntity> allAuthList = AuthDao.findAllAuthList(idList);
        return BeanMapper.mapList(allAuthList, PipelineAuth.class);
    }
}
