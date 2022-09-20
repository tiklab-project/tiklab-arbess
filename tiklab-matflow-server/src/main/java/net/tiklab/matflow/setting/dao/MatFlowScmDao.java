package net.tiklab.matflow.setting.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.dao.MatFlowDao;
import net.tiklab.matflow.setting.entity.MatFlowScmEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class MatFlowScmDao {

    private static final Logger logger = LoggerFactory.getLogger(MatFlowDao.class);

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 添加配置
     * @param matFlowScmEntity 配置信息
     * @return 配置id
     */
    public String createMatFlowScm(MatFlowScmEntity matFlowScmEntity){
        return jpaTemplate.save(matFlowScmEntity, String.class);
    }

    /**
     * 删除配置
     * @param proofId 配置id
     */
    public void deleteMatFlowScm(String proofId){
        jpaTemplate.delete(MatFlowScmEntity.class,proofId);
    }

    /**
     * 更新配置
     * @param matFlowScmEntity 配置信息
     */
    public void updateMatFlowScm(MatFlowScmEntity matFlowScmEntity){
        jpaTemplate.update(matFlowScmEntity);
    }

    /**
     * 查询配置
     * @param proofId 配置id
     * @return 配置信息
     */
    public MatFlowScmEntity findOneMatFlowScm(String proofId){
        return jpaTemplate.findOne(MatFlowScmEntity.class, proofId);
    }

    /**
     * 查询所有配置
     * @return 配置列表
     */
    public List<MatFlowScmEntity> selectAllMatFlowScm(){

        return jpaTemplate.findAll(MatFlowScmEntity.class);
    }

    
    public List<MatFlowScmEntity> selectAllMatFlowScmList(List<String> idList){
        return jpaTemplate.findList(MatFlowScmEntity.class,idList);
    }
}
