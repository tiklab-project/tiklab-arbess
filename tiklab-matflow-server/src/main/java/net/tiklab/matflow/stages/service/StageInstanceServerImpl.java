package net.tiklab.matflow.stages.service;

import net.tiklab.matflow.stages.model.StageInstance;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 阶段执行实例服务
 */
@Service
public class StageInstanceServerImpl implements StageInstanceServer{

    @Override
    public String createStageInstance(StageInstance stageInstance) {
        return null;
    }

    @Override
    public void deleteAllStageInstance(String instanceId) {

    }

    @Override
    public void deleteStageInstance(String stageId) {

    }

    @Override
    public void updateStageInstance(StageInstance stageInstance) {

    }

    @Override
    public StageInstance findOneStageInstance(String stageInstanceId) {
        return null;
    }

    @Override
    public List<StageInstance> findAllInstanceStageInstance(String instanceId) {
        return null;
    }

    @Override
    public List<StageInstance> findAllStageInstance(String stageId) {
        return null;
    }
}
