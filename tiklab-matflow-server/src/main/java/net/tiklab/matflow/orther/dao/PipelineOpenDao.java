package net.tiklab.matflow.orther.dao;


import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.orther.entity.PipelineOpenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineOpenDao {

    private static final Logger logger = LoggerFactory.getLogger(PipelineOpenDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建次数
     * @param pipelineOpenEntity 次数
     * @return 次数id
     */
    public String createOpen(PipelineOpenEntity pipelineOpenEntity){
        return jpaTemplate.save(pipelineOpenEntity, String.class);
    }

    /**
     * 删除次数
     * @param openId 次数id
     */
    public void deleteOpen(String openId){
        jpaTemplate.delete(PipelineOpenEntity.class, openId);
    }

    /**
     * 更新次数
     * @param pipelineOpenEntity 更新信息
     */
    public void updateOpen(PipelineOpenEntity pipelineOpenEntity){
        jpaTemplate.update(pipelineOpenEntity);
    }

    /**
     * 查询单个次数信息
     * @param openId 次数id
     * @return 次数信息
     */
    public PipelineOpenEntity findOneOpen(String openId){
        return jpaTemplate.findOne(PipelineOpenEntity.class,openId);
    }

    public List<PipelineOpenEntity> findAllOpen(String userId, StringBuilder s){
        String sql = "select pip_pipeline_other_open.* from pip_pipeline_other_open ";
        sql = sql.concat(" where pip_pipeline_other_open.user_id   = '"+userId+"' "
                + " and pip_pipeline_other_open.pipeline_id   "
                + " in (" + s +" ) ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineOpenEntity.class));
    }

    /**
     * 查询所有次数
     * @return 次数集合
     */
    public List<PipelineOpenEntity> findAllOpen(){
        return jpaTemplate.findAll(PipelineOpenEntity.class);
    }

    public List<PipelineOpenEntity> findAllOpenList(List<String> idList){
        return jpaTemplate.findList(PipelineOpenEntity.class,idList);
    }



}
