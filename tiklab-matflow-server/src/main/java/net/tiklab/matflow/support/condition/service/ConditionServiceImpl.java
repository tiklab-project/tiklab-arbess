package net.tiklab.matflow.support.condition.service;

import net.tiklab.matflow.support.condition.dao.ConditionDao;
import net.tiklab.matflow.support.condition.model.Condition;
import net.tiklab.matflow.support.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConditionServiceImpl implements ConditionService {

    @Autowired
    ConditionDao conditionDao;

    /**
     * 创建条件
     * @param condition 条件
     * @return 条件id
     */
    public String createCond(Condition condition){
        condition.setCreateTime(PipelineUtil.date(1));
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
    public void updateCond(Condition condition){
        String condId = condition.getCondId();
        Condition oneCond = findOneCond(condId);
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
    public Condition findOneCond(String condId){
       return conditionDao.findOneCond(condId);
    }

    /**
     * 查询任务条件
     * @param taskId 任务id
     * @return 条件集合
     */
    public List<Condition> findAllTaskCond(String taskId){
        List<Condition> allCond = conditionDao.findAllCond();
        if (allCond.size() == 0){
            return null;
        }
        List<Condition> list = new ArrayList<>();
        for (Condition condition : allCond) {
            String conditionTaskId = condition.getTaskId();
            if (conditionTaskId == null || !conditionTaskId.equals(taskId)){
                continue;
            }
            list.add(condition);
        }
        return list;
    }

}

















































