package com.tiklab.matflow.setting.envConfig.service;


import com.tiklab.beans.BeanMapper;
import com.tiklab.matflow.setting.envConfig.dao.MatFlowEnvConfigDao;
import com.tiklab.matflow.setting.envConfig.entity.MatFlowEnvConfigEntity;
import com.tiklab.matflow.setting.envConfig.model.MatFlowEnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatFlowEnvEnvConfigServiceImpl implements MatFlowEnvConfigService {


    @Autowired
    MatFlowEnvConfigDao matFlowEnvConfigDao;


    @Override
    public String createMatFlowEnvConfig(MatFlowEnvConfig matFlowEnvConfig) {
        MatFlowEnvConfigEntity matFlowEnvConfigEntity = BeanMapper.map(matFlowEnvConfig, MatFlowEnvConfigEntity.class);
        //判断凭证作用域
        return matFlowEnvConfigDao.createMatFlowEnvConfig(matFlowEnvConfigEntity);
        
    }

    //删除
    @Override
    public void deleteMatFlowEnvConfig(String matFlowConfigId) {
        MatFlowEnvConfig matFlowEnvConfig = findOneMatFlowEnvConfig(matFlowConfigId);
        matFlowEnvConfigDao.deleteMatFlowEnvConfig(matFlowConfigId);
    }


    //更新
    @Override
    public void updateMatFlowEnvConfig(MatFlowEnvConfig matFlowEnvConfig) {
        matFlowEnvConfigDao.updateMatFlowEnvConfig(BeanMapper.map(matFlowEnvConfig, MatFlowEnvConfigEntity.class));
    }

    //查询
    @Override
    public MatFlowEnvConfig findOneMatFlowEnvConfig(String matFlowConfigId) {
        MatFlowEnvConfigEntity matFlowEnvConfigEntity = matFlowEnvConfigDao.findOneMatFlowEnvConfig(matFlowConfigId);
        return BeanMapper.map(matFlowEnvConfigEntity, MatFlowEnvConfig.class);
    }

    //查询所有
    @Override
    public List<MatFlowEnvConfig> findAllMatFlowEnvConfig() {
        List<MatFlowEnvConfigEntity> matFlowEnvConfigEntityList = matFlowEnvConfigDao.selectAllMatFlowEnvConfig();
        return BeanMapper.mapList(matFlowEnvConfigEntityList, MatFlowEnvConfig.class);
    }

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    @Override
    public MatFlowEnvConfig findOneMatFlowEnvConfig(Integer type) {
        List<MatFlowEnvConfig> allMatFlowEnvConfig = findAllMatFlowEnvConfig();
        if (allMatFlowEnvConfig == null){
            return null;
        }

        for (MatFlowEnvConfig matFlowEnvConfig : allMatFlowEnvConfig) {
            if (type == matFlowEnvConfig.getConfigType()){
                return matFlowEnvConfig;
            }
        }
        return null;
    }
    
    @Override
    public List<MatFlowEnvConfig> selectMatFlowEnvConfigList(List<String> idList) {
        List<MatFlowEnvConfigEntity> matFlowEnvConfigEntityList = matFlowEnvConfigDao.selectAllMatFlowEnvConfigList(idList);
        return BeanMapper.mapList(matFlowEnvConfigEntityList, MatFlowEnvConfig.class);
    }

    
}
