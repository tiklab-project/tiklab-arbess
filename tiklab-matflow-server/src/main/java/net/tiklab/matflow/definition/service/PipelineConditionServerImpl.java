package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.dao.PipelineConditionDao;
import net.tiklab.matflow.definition.model.PipelineCondition;
import net.tiklab.matflow.orther.until.PipelineUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PipelineConditionServerImpl implements PipelineConditionServer {

    @Autowired
    PipelineConditionDao conditionDao;

    /**
     * 创建条件
     * @param condition 条件
     * @return 条件id
     */
    public String createCond(PipelineCondition condition){
        condition.setCreateTime(PipelineUntil.date(1));
        return conditionDao.createCond(condition);
    }

    /**
     * 删除条件
     * @param condId 条件id
     */
    public void deleteCond(String condId){
        conditionDao.deleteCond(condId);
    }

    /**
     * 更新条件
     * @param condition 条件信息
     */
    public void updateCond(PipelineCondition condition){
        String condId = condition.getCondId();
        PipelineCondition oneCond = findOneCond(condId);
        if (condition.getCondType() == 0){
            condition.setCondType(oneCond.getCondType());
        }
        conditionDao.updateCond(condition);
    }

    /**
     * 查询单个条件
     * @param condId 条件id
     * @return 条件信息
     */
    public PipelineCondition findOneCond(String condId){
       return conditionDao.findOneCond(condId);
    }

    /**
     * 查询任务条件
     * @param taskId 任务id
     * @return 条件集合
     */
    public List<PipelineCondition> findAllTaskCond(String taskId){
        List<PipelineCondition> allCond = conditionDao.findAllCond();
        if (allCond.size() == 0){
            return null;
        }
        List<PipelineCondition> list = new ArrayList<>();
        for (PipelineCondition condition : allCond) {
            String conditionTaskId = condition.getTaskId();
            if (conditionTaskId == null || !conditionTaskId.equals(taskId)){
                continue;
            }
            list.add(condition);
        }
        return list;
    }

}

















































