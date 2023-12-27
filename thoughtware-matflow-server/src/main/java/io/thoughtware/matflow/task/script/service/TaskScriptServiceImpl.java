package io.thoughtware.matflow.task.script.service;

import io.thoughtware.matflow.task.script.model.TaskScript;
import io.thoughtware.matflow.task.script.dao.TaskScriptDao;
import io.thoughtware.matflow.task.script.entity.TaskScriptEntity;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class TaskScriptServiceImpl implements TaskScriptService {
    
    
    @Autowired
    private TaskScriptDao taskScriptDao;

    //创建
    @Override
    public String createScript(TaskScript taskScript) {
        TaskScriptEntity taskScriptEntity = BeanMapper.map(taskScript, TaskScriptEntity.class);
        return taskScriptDao.createScript(taskScriptEntity);
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    public TaskScript findScript(String configId){
        List<TaskScript> allScript = findAllScript();
        if (allScript == null){
            return null;
        }
        for (TaskScript script : allScript) {
            if (script.getTaskId().equals(configId)){
                return script;
            }
        }
        return null;
    }

    /**
     * 删除任务
     * @param configId 配置id
     */
    public void deleteOneScript(String configId){
        List<TaskScript> allScript = findAllScript();
        if (allScript == null){
            return ;
        }
        for (TaskScript script : allScript) {
            if (script.getTaskId().equals(configId)){
                deleteScript(script.getTaskId());
            }
        }
    }

    //删除
    @Override
    public void deleteScript(String scriptId) {
        taskScriptDao.deleteScript(scriptId);
    }

    //更新
    @Override
    public void updateScript(TaskScript taskScript) {
        TaskScriptEntity taskScriptEntity = BeanMapper.map(taskScript, TaskScriptEntity.class);
        taskScriptDao.updateScript(taskScriptEntity);
    }

    //查询单个
    @Override
    public TaskScript findOneScript(String scriptId) {
        TaskScriptEntity scriptEntity = taskScriptDao.findOneScript(scriptId);
        return BeanMapper.map(scriptEntity, TaskScript.class);
    }

    //查询所有
    @Override
    public List<TaskScript> findAllScript() {
        return BeanMapper.mapList(taskScriptDao.findAllScript(), TaskScript.class);
    }

    @Override
    public List<TaskScript> findAllScriptList(List<String> idList) {
        return BeanMapper.mapList(taskScriptDao.findAllScriptList(idList), TaskScript.class);
    }
}
