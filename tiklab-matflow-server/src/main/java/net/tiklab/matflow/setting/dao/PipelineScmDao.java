package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.pipeline.definition.dao.PipelineDao;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.setting.entity.PipelineScmEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class PipelineScmDao {

    private static final Logger logger = LoggerFactory.getLogger(PipelineDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 添加配置
     * @param pipelineScmEntity 配置信息
     * @return 配置id
     */
    public String createPipelineScm(PipelineScmEntity pipelineScmEntity){
        pipelineScmEntity.setCreateTime(PipelineUntil.date(1));
        return jpaTemplate.save(pipelineScmEntity, String.class);
    }

    /**
     * 删除配置
     * @param proofId 配置id
     */
    public void deletePipelineScm(String proofId){
        jpaTemplate.delete(PipelineScmEntity.class,proofId);
    }

    /**
     * 更新配置
     * @param pipelineScmEntity 配置信息
     */
    public void updatePipelineScm(PipelineScmEntity pipelineScmEntity){
        jpaTemplate.update(pipelineScmEntity);
    }

    /**
     * 查询配置
     * @param proofId 配置id
     * @return 配置信息
     */
    public PipelineScmEntity findOnePipelineScm(String proofId){
        return jpaTemplate.findOne(PipelineScmEntity.class, proofId);
    }

    /**
     * 查询所有配置
     * @return 配置列表
     */
    public List<PipelineScmEntity> selectAllPipelineScm(){
        return jpaTemplate.findAll(PipelineScmEntity.class);
    }

    
    public List<PipelineScmEntity> selectAllPipelineScmList(List<String> idList){
        return jpaTemplate.findList(PipelineScmEntity.class,idList);
    }
}
