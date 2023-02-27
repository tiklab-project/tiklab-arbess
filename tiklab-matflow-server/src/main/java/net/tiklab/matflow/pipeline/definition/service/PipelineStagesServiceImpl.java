package net.tiklab.matflow.pipeline.definition.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.dao.PipelineStagesDao;
import net.tiklab.matflow.pipeline.definition.entity.PipelineStagesEntity;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.pipeline.definition.model.PipelineStages;
import net.tiklab.matflow.pipeline.definition.model.StagesTask;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class PipelineStagesServiceImpl implements PipelineStagesService {


    @Autowired
    private PipelineStagesDao stagesDao;

    @Autowired
    private StagesTaskService stagesTaskServer;


    /**
     * 创建阶段及关联任务
     * @param config 阶段信息
     * @return 阶段id
     */
    @Override
    public String createStagesTask(Tasks config){

        String stagesId = config.getStagesId();
        String pipelineId = config.getPipeline().getId();
        int taskType = config.getTaskType();
        int taskSort = config.getTaskSort();
        int stage = config.getStages();

        PipelineStages pipelineStages = new PipelineStages(PipelineUtil.date(1),pipelineId);

        //判断新任务是否为代码源
        if (taskType < 10){

            //判断是否存在代码源
            findTypeTasks(pipelineId);

            //创建根节点
            int initStage = initStage(pipelineId,1);
            pipelineStages.setCode(true);
            pipelineStages.setMainStage("true");
            pipelineStages.setTaskSort(1);
            pipelineStages.setTaskStage(initStage);
            pipelineStages.setName("阶段-"+initStage);
            String id = createStages(pipelineStages);
            //从节点
            PipelineStages stages = new PipelineStages(id);
            stages.setTaskSort(1);
            stages.setName("源码");
            stagesId = createStages(stages);
            config.setStagesId(stagesId);

            return stagesTaskServer.createStagesTasksTask(config);
        }

        //新任务
        if (!PipelineUtil.isNoNull(stagesId) && stage == 0){
            //创建主节点
            pipelineStages.setMainStage("true");
            int initStage = initStage(pipelineId,taskSort);
            pipelineStages.setTaskStage(initStage);
            pipelineStages.setTaskSort(1);
            pipelineStages.setName("阶段-"+initStage);
            String id = createStages(pipelineStages);

            //创建从节点
            PipelineStages stages = new PipelineStages();
            stages.setMainStage(id);
            stages.setTaskSort(1);
            stages.setName("并行阶段-"+1);

            stagesId = createStages(stages);

            config.setStagesId(stagesId);
            return stagesTaskServer.createStagesTasksTask(config);
        }

        //串行任务
        if (PipelineUtil.isNoNull(stagesId) && stage != 0){
            return stagesTaskServer.createStagesTasksTask(config);
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
            List<PipelineStages> allMainStage = findAllMainStage(stagesId);
            stages.setTaskSort(allMainStage.size()+1);
            stages.setTaskStage(stage);
            stages.setName("并行阶段-"+(allMainStage.size()+1));
            stagesId = createStages(stages);
            config.setStagesId(stagesId);
            return stagesTaskServer.createStagesTasksTask(config);
        }

        return null;
    }

    /**
     * 判断是否存在代码源
     * @param pipelineId 流水线id
     * @throws ApplicationException 代码源已存在
     */
    private void findTypeTasks(String pipelineId) throws ApplicationException {
        List<PipelineStages> allStage = findAllStagesMainStage(pipelineId);
        if ( allStage.size() == 0){
            return;
        }
        for (PipelineStages stages : allStage) {
            if (stages.isCode()){
                throw new ApplicationException(50001,"代码源已存在，无法再次创建。");
            }
        }
    }

    /**
     * 查询所有阶段任务
     * @param pipelineId 流水线id
     * @return 任务
     */
    @Override
    public List<PipelineStages> findAllStagesStageTask(String pipelineId){
        List<PipelineStages> stagesMainStage = findAllStagesMainStage(pipelineId);
        if (stagesMainStage.size() == 0){
            return null;
        }
        List<PipelineStages> list = new ArrayList<>();
        for (PipelineStages stages : stagesMainStage) {
            String stagesId = stages.getStagesId();
            List<PipelineStages> allStagesStage = findAllMainStage(stagesId);
            stages.setStagesList(allStagesStage);
            list.add(stages);
        }
        list.sort(Comparator.comparing(PipelineStages::getTaskStage));
       return list;
    }

    /**
     * 更新主阶段名称
     * @param stageId 阶段id
     * @param stagesName 名称
     */
    @Override
    public void updateStageName(String stageId,String stagesName){
        PipelineStages stages = findOneStages(stageId);
        stages.setName(stagesName);
        updateStages(stages);
    }

    /**
     * 获取所有阶段的根节点
     * @param pipelineId 流水线id
     * @return 主分支
     */
    @Override
    public List<PipelineStages> findAllStagesMainStage(String pipelineId){
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
        list.sort(Comparator.comparing(PipelineStages::getTaskStage));
        return list;
    }

    /**
     * 获取指定阶段的根节点
     * @param pipelineId 流水线id
     * @param stages 阶段
     * @return 根节点
     */
    @Override
    public PipelineStages findMainStages(String pipelineId,int stages){
        List<PipelineStages> allStage = findAllPipelineStages(pipelineId);
        if (allStage.size() == 0 ){
            return null;
        }
        for (PipelineStages pipelineStages : allStage) {
            int stage = pipelineStages.getTaskStage();
            String mainStage = pipelineStages.getMainStage();
            if (stage != stages || !mainStage.equals("true")){
                continue;
            }
            return pipelineStages;
        }
        return null;
    }

    /**
     * 根据根节点查询所有从节点
     * @param stagesId 根节点id
     * @return 从节点
     */
    @Override
    public List<PipelineStages> findAllMainStage(String stagesId){
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
            List<Object> allStagesConfig = stagesTaskServer.findAllStagesTasksTask(allStage.getStagesId());
            allStage.setTaskValues(allStagesConfig);
            list.add(allStage);
        }
        list.sort(Comparator.comparing(PipelineStages::getTaskSort));
        return list;
    }

    /**
     * 更新主节点阶段顺序
     * @param pipelineId 流水线id
     * @param taskSort 顺序
     * @return 顺序
     */
    private Integer initStage(String pipelineId,int taskSort){
        List<PipelineStages> allMainStage = findAllStagesMainStage(pipelineId);
        if (allMainStage.size() == 0){
            return taskSort;
        }
        for (PipelineStages stages : allMainStage) {
            int stage = stages.getTaskStage();
            if (stage < taskSort){
                continue;
            }
            stages.setTaskStage(stage+1);
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
        list.sort(Comparator.comparing(PipelineStages::getTaskStage));
        return list;
    }

    /**
     * 删除阶段任务
     * @param configId 配置id
     */
    @Override
    public void deleteStagesTask(String configId){

        StagesTask stagesTask = stagesTaskServer.findOneStagesTask(configId);

        boolean b = stagesTaskServer.deleteStagesTasksTask(configId);

        String stagesId = stagesTask.getStagesId();

        // List<StagesTask> allStagesTasks = stagesTaskServer.findAllStagesTasks(stagesId);

        //删除配置与任务后阶段为空时删除阶段
        // if (allStagesTasks == null || allStagesTasks.size() == 0){
        if (!b){
            //获取
            PipelineStages oneStages = findOneStages(stagesId);

            deleteStages(stagesId);

            //主分支id
            String mainId = oneStages.getMainStage();

            PipelineStages mainStages = findOneStages(mainId);

            int stage = mainStages.getTaskStage();

            List<PipelineStages> allMainStage = findAllMainStage(mainId);
            //判断主分支下是否还存在从分支
            if (allMainStage.size() == 0){
                PipelineStages pipelineStages = findOneStages(mainId);
                int sort = pipelineStages.getTaskSort();
                String id = pipelineStages.getPipeline().getId();
                deleteStages(mainId);
                List<PipelineStages> allStagesMainStage = findAllStagesMainStage(id);
                //判断是否还存在主分支
                if (allStagesMainStage.size() == 0){
                    return;
                }
                //更新其他主分支顺序
                for (PipelineStages stages : allStagesMainStage) {
                    int sort1 = stages.getTaskSort();
                    int stage1 = stages.getTaskStage();
                    if (sort1 > sort && sort != 0){
                        stages.setTaskSort(sort1-1);
                    }
                    if (stage1 > stage && stage != 0 ){
                        stages.setTaskStage(stage1-1);
                    }
                    updateStages(stages);
                }
            }else {
                int sort = oneStages.getTaskSort();
                for (PipelineStages stages : allMainStage) {
                    if (sort > stages.getTaskSort()){
                        continue;
                    }
                    stages.setTaskSort(stages.getTaskSort()-1);
                    updateStages(stages);
                }
            }
        }



    }

    /**
     * 更新配置及任务
     * @param config 配置id
     */
    @Override
    public void updateStagesTask(Tasks config){
        stagesTaskServer.updateStagesTasksTask(config);
    }

    /**
     *  删除流水线所有阶段
     * @param pipelineId 流水线id
     */
    @Override
    public void deleteAllStagesTask(String pipelineId){
       List<PipelineStages> allStage = findAllPipelineStages(pipelineId);
       if (allStage.size() == 0){
           return;
       }
       for (PipelineStages stages : allStage) {
           String stagesId = stages.getStagesId();
           List<StagesTask> allStagesTasks = stagesTaskServer.findAllStagesTasks(stagesId);
           if (allStagesTasks.size() != 0){
               deleteStages(stagesId);
               stagesTaskServer.deleteAllStagesTasksTask(stagesId);
           }
       }
   }

    /**
     * 根据阶段id查询所有任务配置
     * @param stagesId 阶段id
     * @return 任务配置
     */
    @Override
    public List<StagesTask> findAllStagesTask(String stagesId){
       return stagesTaskServer.findAllStagesTasks(stagesId);
    }

    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    @Override
    public List<String> validAllConfig(String pipelineId){
        List<PipelineStages> allStage = findAllStagesMainStage(pipelineId);
        if (allStage.size() == 0){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (PipelineStages stages : allStage) {
            String stagesId = stages.getStagesId();
            for (PipelineStages pipelineStages : findAllMainStage(stagesId)) {
                String id = pipelineStages.getStagesId();
                stagesTaskServer.validAllConfig(id,list);
            }
        }
        return list;
    }

    //创建阶段
    @Override
    public String createStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        return stagesDao.createStages(stagesEntity);
    }

    //更新阶段
    @Override
    public void updateStages(PipelineStages stages) {
        PipelineStagesEntity stagesEntity = BeanMapper.map(stages, PipelineStagesEntity.class);
        stagesDao.updateStages(stagesEntity);
    }

    //删除阶段
    @Override
    public void deleteStages(String stageId) {
        stagesDao.deleteStages(stageId);
    }

    //查询单个阶段
    @Override
    public PipelineStages findOneStages(String stageId) {
        PipelineStagesEntity oneStages = stagesDao.findOneStages(stageId);
        return BeanMapper.map(oneStages, PipelineStages.class);
    }

    //查询所有阶段
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
