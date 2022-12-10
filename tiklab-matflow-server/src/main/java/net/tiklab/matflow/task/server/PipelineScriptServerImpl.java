package net.tiklab.matflow.task.server;

import net.tiklab.beans.BeanMapper;
import net.tiklab.matflow.task.dao.PipelineScriptDao;
import net.tiklab.matflow.task.entity.PipelineScriptEntity;
import net.tiklab.matflow.task.model.PipelineScript;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class PipelineScriptServerImpl implements PipelineScriptServer {
    
    
    @Autowired
    private PipelineScriptDao pipelineScriptDao;

    //创建
    @Override
    public String createScript(PipelineScript pipelineScript) {
        PipelineScriptEntity pipelineScriptEntity = BeanMapper.map(pipelineScript, PipelineScriptEntity.class);
        return pipelineScriptDao.createScript(pipelineScriptEntity);
    }

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    public PipelineScript findScript(String configId){
        List<PipelineScript> allScript = findAllScript();
        if (allScript == null){
            return null;
        }
        for (PipelineScript script : allScript) {
            if (script.getConfigId().equals(configId)){
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
        List<PipelineScript> allScript = findAllScript();
        if (allScript == null){
            return ;
        }
        for (PipelineScript script : allScript) {
            if (script.getConfigId().equals(configId)){
                deleteScript(script.getScriptId());
            }
        }
    }

    //删除
    @Override
    public void deleteScript(String scriptId) {
        pipelineScriptDao.deleteScript(scriptId);
    }

    //更新
    @Override
    public void updateScript(PipelineScript pipelineScript) {
        PipelineScriptEntity pipelineScriptEntity = BeanMapper.map(pipelineScript, PipelineScriptEntity.class);
        pipelineScriptDao.updateScript(pipelineScriptEntity);
    }

    //查询单个
    @Override
    public PipelineScript findOneScript(String scriptId) {
        PipelineScriptEntity scriptEntity = pipelineScriptDao.findOneScript(scriptId);
        return BeanMapper.map(scriptEntity,PipelineScript.class);
    }

    //查询所有
    @Override
    public List<PipelineScript> findAllScript() {
        return BeanMapper.mapList(pipelineScriptDao.findAllScript(), PipelineScript.class);
    }

    @Override
    public List<PipelineScript> findAllScriptList(List<String> idList) {
        return BeanMapper.mapList(pipelineScriptDao.findAllScriptList(idList), PipelineScript.class);
    }
}
