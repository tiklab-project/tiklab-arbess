package io.tiklab.arbess.setting.tool.dao;

import io.tiklab.arbess.setting.tool.entity.ScmEntity;
import io.tiklab.arbess.setting.tool.model.ScmQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.pipeline.definition.dao.PipelineDao;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ScmDao {

    private static final Logger logger = LoggerFactory.getLogger(PipelineDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 添加配置
     * @param scmEntity 配置信息
     * @return 配置id
     */
    public String createPipelineScm(ScmEntity scmEntity){
        scmEntity.setCreateTime(PipelineUtil.date(1));
        return jpaTemplate.save(scmEntity, String.class);
    }

    /**
     * 删除配置
     * @param proofId 配置id
     */
    public void deletePipelineScm(String proofId){
        jpaTemplate.delete(ScmEntity.class,proofId);
    }

    /**
     * 更新配置
     * @param scmEntity 配置信息
     */
    public void updatePipelineScm(ScmEntity scmEntity){
        jpaTemplate.update(scmEntity);
    }

    /**
     * 查询配置
     * @param proofId 配置id
     * @return 配置信息
     */
    public ScmEntity findOnePipelineScm(String proofId){
        return jpaTemplate.findOne(ScmEntity.class, proofId);
    }

    /**
     * 查询所有配置
     * @return 配置列表
     */
    public List<ScmEntity> findAllPipelineScm(){
        return jpaTemplate.findAll(ScmEntity.class);
    }


    public List<ScmEntity> findPipelineScmList(ScmQuery scmQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(ScmEntity.class)
                .eq("scmType", scmQuery.getScmType())
                .like("scmName", scmQuery.getName())
                .orders(scmQuery.getOrderParams())
                .get();

        return jpaTemplate.findList(queryCondition, ScmEntity.class);
    }


    public Pagination<ScmEntity> findPipelineScmPage(ScmQuery scmQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(ScmEntity.class)
                .eq("scmType", scmQuery.getScmType())
                .like("scmName", scmQuery.getName())
                .orders(scmQuery.getOrderParams())
                .pagination(scmQuery.getPageParam())
                .get();

        return jpaTemplate.findPage(queryCondition, ScmEntity.class);
    }

    
    public List<ScmEntity> findPipelineScmList(List<String> idList){
        return jpaTemplate.findList(ScmEntity.class,idList);
    }

    public Integer findScmNumber() {
        String sql = "SELECT COUNT(*) AS number FROM pip_setting_scm;";
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return ((Long) map.get("number")).intValue();
    }

}
