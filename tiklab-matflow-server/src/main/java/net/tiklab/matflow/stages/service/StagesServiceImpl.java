package net.tiklab.matflow.stages.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.stages.dao.StagesDao;
import net.tiklab.matflow.stages.entity.StagesEntity;
import net.tiklab.matflow.stages.model.Stages;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.model.TaskFacade;
import net.tiklab.matflow.task.task.service.TasksService;
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
public class StagesServiceImpl implements StagesService {

    @Autowired
    private StagesDao stagesDao;

    @Autowired
    TasksService tasksService;


    @Override
    public String createStagesOrTask(TaskFacade taskFacade){

        String stagesId = taskFacade.getStagesId();
        Pipeline pipeline = taskFacade.getPipeline();
        String pipelineId = pipeline.getId();

        int stage = taskFacade.getStages();

        Stages pipelineStages = new Stages(PipelineUtil.date(1));

        if (taskFacade.getTaskType() < 10){
            //判断是否存在代码源
            findTypeTasks(pipelineId);

            //创建根节点
            int initStage = initStage(pipelineId,1);
            pipelineStages.setCode(true);
            pipelineStages.setStagesSort(1);
            pipelineStages.setPipeline(pipeline);
            pipelineStages.setStagesName("阶段-"+initStage);
            String id = createStages(pipelineStages);

            //从节点
            Stages stages = new Stages();
            stages.setStagesSort(1);
            stages.setStageId(id);
            stages.setStagesName("源码");
            stagesId = createStages(stages);
            taskFacade.setStagesId(stagesId);

            return tasksService.createTasksOrTask(taskFacade);
        }

        //新任务
        if (!PipelineUtil.isNoNull(stagesId) && stage == 0){
            //创建主节点
            int initStage = initStage(pipelineId, taskFacade.getTaskSort());
            pipelineStages.setStagesSort(initStage);
            pipelineStages.setPipeline(pipeline);
            pipelineStages.setStagesName("阶段-" + initStage);
            String id = createStages(pipelineStages);

            //创建从节点
            Stages stages = new Stages();
            stages.setStageId(id);
            stages.setStagesSort(1);
            stages.setStagesName("并行阶段-"+1);

            stagesId = createStages(stages);

            taskFacade.setStagesId(stagesId);
            return tasksService.createTasksOrTask(taskFacade);
        }

        //并行任务
        if (!PipelineUtil.isNoNull(stagesId) && stage != 0){
            Stages mainStages = findMainStages(pipelineId,stage);
            if (mainStages == null){
                return null;
            }
            Stages stages = new Stages();
            stagesId = mainStages.getStagesId();
            stages.setStageId(stagesId);
            List<Stages> allMainStage = findOtherStage(stagesId);
            stages.setStagesSort(allMainStage.size()+1);
            stages.setStagesName("并行阶段-"+(allMainStage.size()+1));
            stagesId = createStages(stages);
            taskFacade.setStagesId(stagesId);
            return tasksService.createTasksOrTask(taskFacade);
        }

        //串行任务
        if (PipelineUtil.isNoNull(stagesId) && stage != 0){
            return tasksService.createTasksOrTask(taskFacade);
        }

        return null;
    }

    /**
     * 判断任务是否存在代码源
     * @param pipelineId 流水线id
     * @throws ApplicationException 代码源已存在
     */
    private void findTypeTasks(String pipelineId) throws ApplicationException {
        List<Stages> allStage = findAllMainStage(pipelineId);
        if ( allStage.size() == 0){
            return;
        }
        for (Stages stages : allStage) {
            if (stages.isCode()){
                throw new ApplicationException(50001,"代码源已存在，无法再次创建。");
            }
        }
    }

    @Override
    public List<Stages> findAllStagesOrTask(String pipelineId){
        //获取流水线主节点
        List<Stages> stagesMainStage = findAllMainStage(pipelineId);
        if (stagesMainStage.size() == 0){
            return null;
        }
        List<Stages> list = new ArrayList<>();
        for (Stages stages : stagesMainStage) {
            String stagesId = stages.getStagesId();
            //获取从节点
            List<Stages> allStagesStage = findOtherStage(stagesId);
            stages.setStagesList(allStagesStage);
            list.add(stages);
        }
        list.sort(Comparator.comparing(Stages::getStagesSort));
       return list;
    }


    @Override
    public void updateStageName(String stageId,String stagesName){
        Stages stages = findOneStages(stageId);
        stages.setStagesName(stagesName);
        updateStages(stages);
    }

    @Override
    public List<Stages> findAllMainStage(String pipelineId){
        List<Stages> allStage = findAllPipelineStages(pipelineId);
        if ( allStage.size() == 0){
            return Collections.emptyList();
        }
        List<Stages> list = new ArrayList<>();
        for (Stages stages : allStage) {
            if (stages.getPipeline().getId().equals(pipelineId)){
                list.add(stages);
            }
        }
        list.sort(Comparator.comparing(Stages::getStagesSort));
        return list;
    }

    @Override
    public Stages findMainStages(String pipelineId, int stages){
        List<Stages> allStage = findAllPipelineStages(pipelineId);
        if (allStage.size() == 0 ){
            return null;
        }
        for (Stages pipelineStages : allStage) {
            int stage = pipelineStages.getStagesSort();
            if (stage != stages || pipelineStages.getStageId() != null){
                continue;
            }
            return pipelineStages;
        }
        return null;
    }

    @Override
    public List<Stages> findOtherStage(String stagesId){
        List<Stages> allStages = findAllStages();
        if (allStages == null || allStages.size() == 0){
            return Collections.emptyList();
        }
        List<Stages> list = new ArrayList<>();
        for (Stages allStage : allStages) {
            String id = allStage.getStageId();
            if (id == null || !id.equals(stagesId)){
                continue;
            }
            //获取阶段配置及任务
            String stagesId1 = allStage.getStagesId();
            List<Object> allStagesConfig =
                    tasksService.findAllTasksOrTask(stagesId1,2);
            allStage.setTaskValues(allStagesConfig);
            list.add(allStage);
        }
        list.sort(Comparator.comparing(Stages::getStagesSort));
        return list;
    }

    /**
     * 更新主节点阶段顺序
     * @param pipelineId 流水线id
     * @param taskSort 顺序
     * @return 顺序
     */
    private Integer initStage(String pipelineId,int taskSort){
        List<Stages> allMainStage = findAllMainStage(pipelineId);
        if (allMainStage.size() == 0){
            return taskSort;
        }
        for (Stages stages : allMainStage) {
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
    private List<Stages> findAllPipelineStages(String pipelineId){
        List<Stages> allStages = findAllStages();
        if (allStages == null || allStages.size() == 0){
            return Collections.emptyList();
        }
        List<Stages> list = new ArrayList<>();
        for (Stages stages : allStages) {
            Pipeline pipeline = stages.getPipeline();
            if (pipeline == null || !pipeline.getId().equals(pipelineId)){
                continue;
            }
            list.add(stages);
        }
        list.sort(Comparator.comparing(Stages::getStagesSort));
        return list;
    }

    @Override
    public void deleteStagesOrTask(String taskId){
        //获取需要删除的任务信息
        Tasks tasksTask = tasksService.findOneTasks(taskId);
        String taskStagesId = tasksTask.getStagesId();
        //删除任务
        tasksService.deleteTasksOrTask(taskId);

        List<Tasks> tasks = tasksService.finAllPipOrStages(taskStagesId, 2);
        Stages stages1 = findOneStages(taskStagesId);
        //查询该阶段是否还存在任务
        if (tasks == null || tasks.size() == 0){
            deleteStages(taskStagesId);
            String mainStage = stages1.getStageId();
            List<Stages> otherStage = findOtherStage(mainStage);
            //主节点不存在其他从节点，删除主节点
            if (otherStage == null || otherStage.size() == 0){
                deleteStages(mainStage);
                return;
            }
            //存在则更新从节点顺序
            for (Stages stages : otherStage) {
                int sort = stages.getStagesSort();
                if (stages1.getStagesSort() < sort){
                    stages.setStagesSort(sort -1);
                    updateStages(stages);
                }
            }
        }
    }

    @Override
    public void updateStagesTask(TaskFacade taskFacade){
        tasksService.updateTasksTask(taskFacade);
    }

    @Override
    public void deleteAllStagesOrTask(String pipelineId){
       List<Stages> allStage = findAllPipelineStages(pipelineId);
       if (allStage.size() == 0){
           return;
       }
       for (Stages stages : allStage) {
           String stagesId = stages.getStagesId();
           tasksService.deleteAllTasksOrTask(stagesId,2);
           deleteStages(stagesId);
       }
   }

    @Override
    public List<String> validStagesMustField(String pipelineId){
        List<Stages> allStage = findAllMainStage(pipelineId);
        if (allStage.size() == 0){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (Stages stages : allStage) {
            String stagesId = stages.getStagesId();
            for (Stages pipelineStages : findOtherStage(stagesId)) {
                String id = pipelineStages.getStagesId();
                //获取任务详情
                List<String> stringList = tasksService.validTasksMustField(id, 2);
                list.addAll(stringList);
            }
        }
        return list;
    }

    @Override
    public String createStages(Stages stages) {
        StagesEntity stagesEntity = BeanMapper.map(stages, StagesEntity.class);
        return stagesDao.createStages(stagesEntity);
    }

    @Override
    public void updateStages(Stages stages) {
        StagesEntity stagesEntity = BeanMapper.map(stages, StagesEntity.class);
        stagesDao.updateStages(stagesEntity);
    }

    @Override
    public void deleteStages(String stageId) {
        stagesDao.deleteStages(stageId);
    }

    @Override
    public Stages findOneStages(String stageId) {
        StagesEntity oneStages = stagesDao.findOneStages(stageId);
        return BeanMapper.map(oneStages, Stages.class);
    }

    @Override
    public List<Stages> findAllStages() {
        List<StagesEntity> allStagesList = stagesDao.findAllStages();
        return BeanMapper.mapList(allStagesList, Stages.class);
    }

    @Override
    public List<Stages> findAllStagesList(List<String> idList) {
        List<StagesEntity> allStagesList = stagesDao.findAllStagesList(idList);
        return BeanMapper.mapList(allStagesList, Stages.class);
    }

}
