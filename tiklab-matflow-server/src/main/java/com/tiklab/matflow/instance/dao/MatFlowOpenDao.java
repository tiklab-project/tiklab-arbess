package com.tiklab.matflow.instance.dao;


import com.tiklab.dal.jdbc.JdbcTemplate;
import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.instance.entity.MatFlowOpenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowOpenDao {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowOpenDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建次数
     * @param matFlowOpenEntity 次数
     * @return 次数id
     */
    public String createOpen(MatFlowOpenEntity matFlowOpenEntity){
        return jpaTemplate.save(matFlowOpenEntity, String.class);
    }

    /**
     * 删除次数
     * @param openId 次数id
     */
    public void deleteOpen(String openId){
        jpaTemplate.delete(MatFlowOpenEntity.class, openId);
    }

    /**
     * 更新次数
     * @param matFlowOpenEntity 更新信息
     */
    public void updateOpen(MatFlowOpenEntity matFlowOpenEntity){
        jpaTemplate.update(matFlowOpenEntity);
    }

    /**
     * 查询单个次数信息
     * @param openId 次数id
     * @return 次数信息
     */
    public MatFlowOpenEntity findOneOpen(String openId){
        return jpaTemplate.findOne(MatFlowOpenEntity.class,openId);
    }

    public List<MatFlowOpenEntity> findAllOpen(String userId, StringBuilder s){
        String sql = "select matflow_open.* from matflow_open ";
        sql = sql.concat(" where matflow_open.user_id   = '"+userId+"' "
                + " and matflow_open.matflow_id   "
                + " in (" + s +" ) ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowOpenEntity.class));
    }

    /**
     * 查询所有次数
     * @return 次数集合
     */
    public List<MatFlowOpenEntity> findAllOpen(){
        return jpaTemplate.findAll(MatFlowOpenEntity.class);
    }

    public List<MatFlowOpenEntity> findAllOpenList(List<String> idList){
        return jpaTemplate.findList(MatFlowOpenEntity.class,idList);
    }



}
