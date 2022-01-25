package com.doublekit.pipeline.category.dao;

import com.doublekit.common.page.Pagination;
import com.doublekit.dal.jpa.criterial.QueryBuilders;
import com.doublekit.dal.jpa.criterial.model.DeleteCondition;
import com.doublekit.dal.jpa.criterial.model.QueryCondition;
import com.doublekit.pipeline.category.entity.CategoryEntity;
import com.doublekit.pipeline.category.model.CategoryQuery;
import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.document.entity.DocumentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * CategoryDao
 */
@Repository
public class CategoryDao{

    private static Logger logger = LoggerFactory.getLogger(CategoryDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param categoryEntity
     * @return
     */
    public String createCategory(CategoryEntity categoryEntity) {
        return jpaTemplate.save(categoryEntity,String.class);
    }

    /**
     * 更新
     * @param categoryEntity
     */
    public void updateCategory(CategoryEntity categoryEntity){
        jpaTemplate.update(categoryEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteCategory(String id){
        jpaTemplate.delete(CategoryEntity.class,id);
    }

    public void deleteCategory(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public CategoryEntity findCategory(String id){
        return jpaTemplate.findOne(CategoryEntity.class,id);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public List<Object> findCategoryDocument(String id){
        List<Object> objects = new ArrayList<>();

        QueryCondition queryCondition = QueryBuilders.createQuery(CategoryEntity.class,"ce").eq("ce.parentCategoryId",id).get();
        List<CategoryEntity> categoryList = jpaTemplate.findList(queryCondition,CategoryEntity.class);

        if(categoryList.size() > 0) {
            objects.addAll(categoryList);
        }
        queryCondition = QueryBuilders.createQuery(DocumentEntity.class,"de").eq("de.categoryId",id).get();
        List<DocumentEntity> documentList = jpaTemplate.findList(queryCondition,DocumentEntity.class);

        if(documentList.size() >0){
            objects.addAll(documentList);
        }

        return objects;
    }
    /**
    * findAllCategory
    * @return
    */
    public List<CategoryEntity> findAllCategory() {
        return jpaTemplate.findAll(CategoryEntity.class);
    }

    public List<CategoryEntity> findCategoryList(List<String> idList) {
        return jpaTemplate.findList(CategoryEntity.class,idList);
    }

    public List<CategoryEntity> findCategoryList(CategoryQuery categoryQuery) {
        return jpaTemplate.findList(categoryQuery, CategoryEntity.class);
    }

    public Pagination<CategoryEntity> findCategoryPage(CategoryQuery categoryQuery) {
        return jpaTemplate.findPage(categoryQuery, CategoryEntity.class);
    }
}