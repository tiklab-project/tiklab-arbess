package io.tiklab.matflow.support.condition.service;

import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.support.condition.dao.ConditionDao;
import io.tiklab.matflow.support.condition.model.Condition;
import io.tiklab.matflow.support.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ConditionServiceImpl implements ConditionService {

    @Autowired
    ConditionDao conditionDao;

    @Autowired
    private VariableService variableService;

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
     * 效验条件
     * @param pipelineId 流水线id
     * @param taskId 配置id
     * @return 状态 true:条件满足 false:条件不满足
     */
    public Boolean variableCondition(String pipelineId,String taskId){
        List<Condition> allTaskCond = findAllTaskCond(taskId);
        if (allTaskCond == null || allTaskCond.size() == 0 ){
            return true;
        }
        for (Condition condition : allTaskCond) {
            String condKey = "${"+condition.getCondKey()+"}";
            String condValue = condition.getCondValue();
            int type = condition.getCondType();
            String key = variableService.replaceVariable(pipelineId, taskId, condKey);
            if (type == 1 && !key.equals(condValue)){
                return false;
            }
            if (type == 2 && key.equals(condValue)){
                return false;
            }
        }
        return true;
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
        list.sort(Comparator.comparing(Condition::getCreateTime).reversed());
        return list;
    }

}

















































