package com.tiklab.matflow.setting.envConfig.dao;

import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.definition.dao.MatFlowDao;
import com.tiklab.matflow.setting.envConfig.entity.MatFlowEnvConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class MatFlowEnvConfigDao {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 添加配置
     * @param matFlowEnvConfigEntity 配置信息
     * @return 配置id
     */
    public String createMatFlowEnvConfig(MatFlowEnvConfigEntity matFlowEnvConfigEntity){
        return jpaTemplate.save(matFlowEnvConfigEntity, String.class);
    }

    /**
     * 删除配置
     * @param proofId 配置id
     */
    public void deleteMatFlowEnvConfig(String proofId){
        jpaTemplate.delete(MatFlowEnvConfigEntity.class,proofId);
    }

    /**
     * 更新配置
     * @param matFlowEnvConfigEntity 配置信息
     */
    public void updateMatFlowEnvConfig(MatFlowEnvConfigEntity matFlowEnvConfigEntity){
        jpaTemplate.update(matFlowEnvConfigEntity);
    }

    /**
     * 查询配置
     * @param proofId 配置id
     * @return 配置信息
     */
    public MatFlowEnvConfigEntity findOneMatFlowEnvConfig(String proofId){
        return jpaTemplate.findOne(MatFlowEnvConfigEntity.class, proofId);
    }

    /**
     * 查询所有配置
     * @return 配置列表
     */
    public List<MatFlowEnvConfigEntity> selectAllMatFlowEnvConfig(){

        return jpaTemplate.findAll(MatFlowEnvConfigEntity.class);
    }

    
    public List<MatFlowEnvConfigEntity> selectAllMatFlowEnvConfigList(List<String> idList){
        return jpaTemplate.findList(MatFlowEnvConfigEntity.class,idList);
    }
}
