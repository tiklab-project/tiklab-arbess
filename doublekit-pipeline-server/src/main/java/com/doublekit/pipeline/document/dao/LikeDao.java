package com.doublekit.pipeline.document.dao;

import com.doublekit.common.page.Pagination;
import com.doublekit.dal.jpa.criterial.model.DeleteCondition;
import com.doublekit.pipeline.document.entity.LikeEntity;
import com.doublekit.pipeline.document.model.LikeQuery;
import com.doublekit.dal.jpa.JpaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LikeDao
 */
@Repository
public class LikeDao{

    private static Logger logger = LoggerFactory.getLogger(LikeDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param likeEntity
     * @return
     */
    public String createLike(LikeEntity likeEntity) {
        return jpaTemplate.save(likeEntity,String.class);
    }

    /**
     * 更新
     * @param likeEntity
     */
    public void updateLike(LikeEntity likeEntity){
        jpaTemplate.update(likeEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteLike(String id){
        jpaTemplate.delete(LikeEntity.class,id);
    }

    public void deleteLike(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public LikeEntity findLike(String id){
        return jpaTemplate.findOne(LikeEntity.class,id);
    }

    /**
    * findAllLike
    * @return
    */
    public List<LikeEntity> findAllLike() {
        return jpaTemplate.findAll(LikeEntity.class);
    }

    public List<LikeEntity> findLikeList(List<String> idList) {
        return jpaTemplate.findList(LikeEntity.class,idList);
    }

    public List<LikeEntity> findLikeList(LikeQuery likeQuery) {
        return jpaTemplate.findList(likeQuery, LikeEntity.class);
    }

    public Pagination<LikeEntity> findLikePage(LikeQuery likeQuery) {
        return jpaTemplate.findPage(likeQuery, LikeEntity.class);
    }
}