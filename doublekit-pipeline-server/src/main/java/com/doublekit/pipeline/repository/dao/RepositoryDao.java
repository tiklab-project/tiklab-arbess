package com.doublekit.pipeline.repository.dao;

import com.doublekit.common.page.Pagination;
import com.doublekit.dal.jpa.criterial.model.DeleteCondition;
import com.doublekit.pipeline.common.CurrentRegUser;
import com.doublekit.pipeline.repository.entity.RepositoryEntity;
import com.doublekit.pipeline.repository.model.RepositoryQuery;
import com.doublekit.dal.jpa.JpaTemplate;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositoryDao
 */
@Repository
public class RepositoryDao{

    private static Logger logger = LoggerFactory.getLogger(RepositoryDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param repositoryEntity
     * @return
     */
    public String createRepository(RepositoryEntity repositoryEntity) {
        return jpaTemplate.save(repositoryEntity,String.class);
    }

    /**
     * 更新
     * @param repositoryEntity
     */
    public void updateRepository(RepositoryEntity repositoryEntity){
        jpaTemplate.update(repositoryEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRepository(String id){
        jpaTemplate.delete(RepositoryEntity.class,id);
    }

    public void deleteRepository(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RepositoryEntity findRepository(String id){
        return jpaTemplate.findOne(RepositoryEntity.class,id);
    }

    /**
    * findAllRepository
    * @return
    */
    public List<RepositoryEntity> findAllRepository() {
        return jpaTemplate.findAll(RepositoryEntity.class);
    }

    public List<RepositoryEntity> findRepositoryList(List<String> idList) {
        return jpaTemplate.findList(RepositoryEntity.class,idList);
    }

    public List<RepositoryEntity> findRepositoryList(RepositoryQuery repositoryQuery) {
        return jpaTemplate.findList(repositoryQuery, RepositoryEntity.class);
    }

    public Pagination<RepositoryEntity> findRepositoryPage(RepositoryQuery repositoryQuery) {
        String userId = CurrentRegUser.getInstace().findCreatUser();
        String sql = "select DISTINCT w.id,w.name,w.type_id,w.master,w.description,w.limits from wiki_repository w left join orc_dm_user d on w.id = d.domain_id    ";
        sql = sql.concat("where w.limits = '1' and d.user_id = ? or w.limits = '0'");
        Pagination<RepositoryEntity> repositoryEntityList =
                this.jpaTemplate.getJdbcTemplate().findPage(sql,new Object[]{userId},repositoryQuery.getPageParam(),new BeanPropertyRowMapper(RepositoryEntity.class));
//                (sql, new String[]{userId}, new BeanPropertyRowMapper(RepositoryEntity.class));;
        return repositoryEntityList;
    }
}