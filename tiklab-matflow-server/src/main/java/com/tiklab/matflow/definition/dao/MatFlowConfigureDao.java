package com.tiklab.matflow.definition.dao;



import com.tiklab.dal.jdbc.JdbcTemplate;
import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.definition.entity.MatFlowConfigureEntity;
import com.tiklab.matflow.instance.entity.MatFlowExecHistoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MatFlowConfigureDao
 */

@Repository
public class MatFlowConfigureDao {

    @Autowired
    JpaTemplate jpaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowConfigureDao.class);

    /**
     * 添加流水线配置信息
     * @param matFlowConfigureEntity 配置信息
     * @return 配置信息id
     */
    public String createConfigure(MatFlowConfigureEntity matFlowConfigureEntity){
        return jpaTemplate.save(matFlowConfigureEntity,String.class);
    }

    /**
     * 删除流水线配置
     * @param id 配置id
     */
    public void deleteConfigure(String id){
        jpaTemplate.delete(MatFlowConfigureEntity.class,id);
    }

    /**
     * 更新流水线配置
     * @param matFlowConfigureEntity 配置信息
     */
    public void updateConfigure(MatFlowConfigureEntity matFlowConfigureEntity){
        jpaTemplate.update(matFlowConfigureEntity);
    }

    /**
     * 查询配置信息
     * @param configureId 查询id
     * @return 配置信息
     */
    public MatFlowConfigureEntity findOneConfigure(String configureId){
        return jpaTemplate.findOne(MatFlowConfigureEntity.class, configureId);
    }

    /**
     * 根据流水线id获取配置
     * @param matFlowId 流水线id
     * @return 配置信息
     */
    public List<MatFlowConfigureEntity> findAllConfigure(String matFlowId){
        String sql = "select matflow_configure.* from matflow_configure";
        sql = sql.concat(" where matflow_configure.matflow_id = '"+ matFlowId+"' " );
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowConfigureEntity.class));
    }


    /**
     * 查询所有配置信息
     * @return 配置信息集合
     */
    public List<MatFlowConfigureEntity> findAllConfigure(){
        return jpaTemplate.findAll(MatFlowConfigureEntity.class);
    }

    public List<MatFlowConfigureEntity> findAllConfigureList(List<String> idList){
        return jpaTemplate.findList(MatFlowConfigureEntity.class,idList);
    }
}
