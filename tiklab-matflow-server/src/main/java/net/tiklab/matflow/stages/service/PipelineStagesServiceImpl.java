package net.tiklab.matflow.stages.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.stages.dao.PipelineStagesDao;
import net.tiklab.matflow.stages.entity.PipelineStagesEntity;
import net.tiklab.matflow.stages.model.PipelineStages;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.model.PipelineTasks;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.service.PipelineTasksService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 流水线阶段服务
 */
@Service
@Exporter
public class PipelineStagesServiceImpl implements PipelineStagesService {

    @Autowired
    private PipelineStagesDao stagesDao;

    @Autowired
    PipelineTasksService tasksService;


    @Override
    public String createStagesOrTask(Tasks tasks){

        String stagesId = tasks.getStagesId();
        String pipelineId = tasks.getPipeline().getId();

        int stage = tasks.getStages();

        PipelineStages pipelineStages = new PipelineStages(PipelineUtil.date(1),pipelineId);

        if (tasks.getTaskType() < 10){
            //判断是否存在代码源
            findTypeTasks(pipelineId);

            //创建根节点
            int initStage = initStage(pipelineId,1);
            pipelineStages.setCode(true);
            pipelineStages.setMainStage("true");
            pipelineStages.setStagesSort(1);
            pipelineStages.setStagesName("阶段-"+initStage);
            String id = createStages(pipelineStages);

            //从节点
            PipelineStages stages = new PipelineStages(id);
            stages.setStagesSort(1);
            stages.setStagesName("源码");
            stagesId = createStages(stages);
            tasks.setStagesId(stagesId);

            return tasksService.createTasksOrTask(tasks);
        }

        //新任务
        if (!PipelineUtil.isNoNull(stagesId) && stage == 0){
            //创建主节点
            pipelineStages.setMainStage("true");
            int initStage = initStage(pipelineId,tasks.getTaskSort());
            pipelineStages.setStagesSort(initStage);
            pipelineStages.setStagesName("阶段-"+initStage);
            String id = createStages(pipelineStages);

            //创建从节点
            PipelineStages stages = new PipelineStages();
            stages.setMainStage(id);
            stages.setStagesSort(1);
            stages.setStagesName("并行阶段-"+1);

            stagesId = createStages(stages);

            tasks.setStagesId(stagesId);
            return tasksService.createTasksOrTask(tasks);
        }

        //并行任务
        if (!PipelineUtil.isNoNull(stagesId) && stage != 0){
            PipelineStages mainStages = findMainStages(pipelineId,stage);
            if (mainStages == null){
                return null;
            }
            PipelineStages stages = new PipelineStages();
            stagesId = mainStages.getStagesId();
            stages.setMainStage(stagesId);
            List<PipelineStages> allMainStage = findOtherStage(stagesId);
            stages.setStagesSort(allMainStage.size()+1);
            stages.setStagesName("并行阶段-"+(allMainStage.size()+1));
            stagesId = createStages(stages);
            tasks.setStagesId(stagesId);
            return tasksService.createTasksOrTask(tasks);
        }

        //串行任务
        if (PipelineUtil.isNoNull(stagesId) && stage != 0){
            return tasksService.createTasksOrTask(tasks);
        }

        return null;
    }

    /**
     * 判断任务是否存在代码源
     * @param pipelineId 流水线id
     * @throws ApplicationException 代码源已存在
     */
    private void findTypeTasks(String pipelineId) throws ApplicationException {
        List<PipelineStages> allStage = findAllMainStage(pipelineId);
        if ( allStage.size() == 0){
            return;
        }
        for (PipelineStages stages : allStage) {
            if (stages.isCode()){
                throw new ApplicationException(50001,"代码源已存在，无法再次创建。");
            }
        }
    }

    @Override
    public List<PipelineStages> findAllStagesOrTask(String pipelineId){
        //获取流水线主节点
        List<PipelineStages> stagesMainStage = findAllMainStage(pipelineId);
        if (stagesMainStage.size() == 0){
            return null;
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages stages : stagesMainStage) {
            String stagesId = stages.getStagesId();
            //获取从节点
            List<PipelineStages> allStagesStage = findOtherStage(stagesId);
            stages.setStagesList(allStagesStage);
            list.add(stages);
        }
        list.sort(Comparator.comparing(PipelineStages::getStagesSort));
       return list;
    }


    @Override
    public void updateStageName(String stageId,String stagesName){
        PipelineStages stages = findOneStages(stageId);
        stages.setStagesName(stagesName);
        updateStages(stages);
    }

    @Override
    public List<PipelineStages> findAllMainStage(String pipelineId){
        List<PipelineStages> allStage = findAllPipelineStages(pipelineId);
        if ( allStage.size() == 0){
            return Collections.emptyList();
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages pipelineStages : allStage) {
            if (pipelineStages.getMainStage().equals("true")){
                list.add(pipelineStages);
            }
        }
        list.sort(Comparator.comparing(PipelineStages::getStagesSort));
        return list;
    }

    @Override
    public PipelineStages findMainStages(String pipelineId,int stages){
        List<PipelineStages> allStage = findAllPipelineStages(pipelineId);
        if (allStage.size() == 0 ){
            return null;
        }
        for (PipelineStages pipelineStages : allStage) {
            int stage = pipelineStages.getStagesSort();
            String mainStage = pipelineStages.getMainStage();
            if (stage != stages || !mainStage.equals("true")){
                continue;
            }
            return pipelineStages;
        }
        return null;
    }

    @Override
    public List<PipelineStages> findOtherStage(String stagesId){
        List<PipelineStages> allStages = findAllStages();
        if (allStages == null || allStages.size() == 0){
            return Collections.emptyList();
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages allStage : allStages) {
            String id = allStage.getMainStage();
            if (!id.equals(stagesId)){
                continue;
            }
            //获取阶段配置及任务
            String stagesId1 = allStage.getStagesId();
            List<Object> allStagesConfig =
                    tasksService.findAllTasksOrTask(stagesId1,2);
            allStage.setTaskValues(allStagesConfig);
            list.add(allStage);
        }
        list.sort(Comparator.comparing(PipelineStages::getStagesSort));
        return list;
    }

    /**
     * 更新主节点阶段顺序
     * @param pipelineId 流水线id
     * @param taskSort 顺序
     * @return 顺序
     */
    private Integer initStage(String pipelineId,int taskSort){
        List<PipelineStages> allMainStage = findAllMainStage(pipelineId);
        if (allMainStage.size() == 0){
            return taskSort;
        }
        for (PipelineStages stages : allMainStage) {
            int stage = stages.getStagesSort();
            if (stage < taskSort){
                continue;
            }
            stages.setStagesSort(stage+1);
            updateStages(stages);
        }
        return taskSort;
    }

    /**
     * 查询流水线所有阶段
     * @param pipelineId 流水线id
     * @return 阶段集合
     */
    private List<PipelineStages> findAllPipelineStages(String pipelineId){
        List<PipelineStages> allStages = findAllStages();
        if (allStages == null || allStages.size() == 0){
            return Collections.emptyList();
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages stages : allStages) {
            Pipeline pipeline = stages.getPipeline();
            if (pipeline == null || !pipeline.getId().equals(pipelineId)){
                continue;
            }
            list.add(stages);
        }
        list.sort(Comparator.comparing(PipelineStages::getStagesSort));
        return list;
    }

    @Override
    public void deleteStagesOrTask(String taskId){
        //获取需要删除的任务信息
        PipelineTasks tasksTask = tasksService.findOneTasks(taskId);
        String taskStagesId = tasksTask.getStagesId();
        //删除任务
        tasksService.deleteTasksOrTask(taskId);

        List<PipelineTasks> pipelineTasks = tasksService.finAllPipOrStageTasks(taskStagesId, 2);
        PipelineStages stages1 = findOneStages(taskStagesId);
        //查询该阶段是否还存在任务
        if (pipelineTasks == null || pipelineTasks.size() == 0){
            deleteStages(taskStagesId);
            String mainStage = stages1.getMainStage();
            List<PipelineStages> otherStage = findOtherStage(mainStage);
            //主节点不存在其他从节点，删除主节点
            if (otherStage == null || otherStage.size() == 0){
                deleteStages(mainStage);
                return;
            }
            //存在则更新从节点顺序
            for (PipelineStages stages : otherStage) {
                int sort = stages.getStagesSort();
                if (stages1.getStagesSort() < sort){
                    stages.setStagesSort(sort -1);
                    updateStages(stages);
                }
            }
        }
    }

    @Override
    public void updateStagesTask(Tasks tasks){
        tasksService.updateTasksTask(tasks);
    }

    @Override
    public void deleteAllStagesOrTask(String pipelineId){
       List<PipelineStages> allStage = findAllPipelineStages(pipelineId);
       if (allStage.size() == 0){
           return;
       }
       for (PipelineStages stages : allStage) {
           String stagesId = stages.getStagesId();
           tasksService.deleteAllTasksOrTask(stagesId,2);
           deleteStages(stagesId);
       }
   }

    @Override
    public List<String> validStagesMustField(String pipelineId){
        List<PipelineStages> allStage = findAllMainStage(pipelineId);
        if (allStage.size() == 0){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (PipelineStages stages : allStage) {
            String stagesId = stages.getStagesId();
            for (PipelineStages pipelineStages : findOtherStage(stagesId)) {
                String id = pipelineStages.getStagesId();
                //获取任务详情
                List<String> stringList = tasksService.validTasksMustField(id, 2);
                list.addAll(stringList);
            }
        }
        return list;
    }

    @Override
    public String createStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        return stagesDao.createStages(stagesEntity);
    }

    @Override
    public void updateStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        stagesDao.updateStages(stagesEntity);
    }

    @Override
    public void deleteStages(String stageId) {
        stagesDao.deleteStages(stageId);
    }

    @Override
    public PipelineStages findOneStages(String stageId) {
        PipelineStagesEntity oneStages = stagesDao.findOneStages(stageId);
        return BeanMapper.map(oneStages, PipelineStages.class);
    }

    @Override
    public List<PipelineStages> findAllStages() {
        List<PipelineStagesEntity> allStagesList = stagesDao.findAllStages();
        return BeanMapper.mapList(allStagesList, PipelineStages.class);
    }

    @Override
    public List<PipelineStages> findAllStagesList(List<String> idList) {
        List<PipelineStagesEntity> allStagesList = stagesDao.findAllStagesList(idList);
        return BeanMapper.mapList(allStagesList, PipelineStages.class);
    }

}
