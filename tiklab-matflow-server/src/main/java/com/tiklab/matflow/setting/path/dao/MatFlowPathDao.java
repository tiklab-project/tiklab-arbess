package com.tiklab.matflow.setting.path.dao;

import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.definition.dao.MatFlowDao;
import com.tiklab.matflow.setting.path.entity.MatFlowPathEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class MatFlowPathDao {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 添加配置
     * @param matFlowPathEntity 配置信息
     * @return 配置id
     */
    public String createMatFlowPath(MatFlowPathEntity matFlowPathEntity){
        return jpaTemplate.save(matFlowPathEntity, String.class);
    }

    /**
     * 删除配置
     * @param proofId 配置id
     */
    public void deleteMatFlowPath(String proofId){
        jpaTemplate.delete(MatFlowPathEntity.class,proofId);
    }

    /**
     * 更新配置
     * @param matFlowPathEntity 配置信息
     */
    public void updateMatFlowPath(MatFlowPathEntity matFlowPathEntity){
        jpaTemplate.update(matFlowPathEntity);
    }

    /**
     * 查询配置
     * @param proofId 配置id
     * @return 配置信息
     */
    public MatFlowPathEntity findOneMatFlowPath(String proofId){
        return jpaTemplate.findOne(MatFlowPathEntity.class, proofId);
    }

    /**
     * 查询所有配置
     * @return 配置列表
     */
    public List<MatFlowPathEntity> selectAllMatFlowPath(){

        return jpaTemplate.findAll(MatFlowPathEntity.class);
    }

    
    public List<MatFlowPathEntity> selectAllMatFlowPathList(List<String> idList){
        return jpaTemplate.findList(MatFlowPathEntity.class,idList);
    }
}
