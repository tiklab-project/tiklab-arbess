package io.tiklab.arbess.task.strategy.service;

import io.tiklab.arbess.task.strategy.dao.TaskHostStrategyDao;
import io.tiklab.arbess.task.strategy.entity.TaskHostStrategyEntity;
import io.tiklab.arbess.task.strategy.model.TaskHostStrategy;
import io.tiklab.arbess.task.strategy.model.TaskHostStrategyQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHostStrategyServiceImpl implements TaskHostStrategyService {

    @Autowired
    TaskHostStrategyDao StrategyDao;

    @Override
    public String createStrategy(TaskHostStrategy Strategy) {
        TaskHostStrategyEntity StrategyEntity = BeanMapper.map(Strategy, TaskHostStrategyEntity.class);
        return StrategyDao.createTaskStrategy(StrategyEntity);
    }

    @Override
    public void deleteStrategy(String id) {
        StrategyDao.deleteTaskStrategy(id);
    }

    @Override
    public void updateStrategy(TaskHostStrategy Strategy) {
        TaskHostStrategyEntity StrategyEntity = BeanMapper.map(Strategy, TaskHostStrategyEntity.class);
        StrategyDao.updateTaskStrategy(StrategyEntity);
    }

    @Override
    public TaskHostStrategy findStrategy(String id) {
        TaskHostStrategyEntity StrategyEntity = StrategyDao.findTaskStrategy(id);
        return BeanMapper.map(StrategyEntity, TaskHostStrategy.class);
    }

    @Override
    public List<TaskHostStrategy> findAllStrategy() {
        List<TaskHostStrategyEntity> StrategyEntityList = StrategyDao.findAllTaskStrategy();
        return BeanMapper.mapList(StrategyEntityList, TaskHostStrategy.class);
    }

    @Override
    public List<TaskHostStrategy> findStrategyList(TaskHostStrategyQuery query) {
        List<TaskHostStrategyEntity> StrategyEntityList = StrategyDao.findTaskStrategyList(query);
        return BeanMapper.mapList(StrategyEntityList, TaskHostStrategy.class);
    }

    @Override
    public List<TaskHostStrategy> findStrategyList(List<String> idList) {
        List<TaskHostStrategyEntity> StrategyEntityList = StrategyDao.findTaskStrategyList(idList);
        return BeanMapper.mapList(StrategyEntityList, TaskHostStrategy.class);
    }

    @Override
    public Pagination<TaskHostStrategy> findStrategyPage(TaskHostStrategyQuery query) {
        Pagination<TaskHostStrategyEntity> StrategyEntityPagination = StrategyDao.findTaskStrategyPage(query);
        List<TaskHostStrategy> StrategyList = BeanMapper.mapList(StrategyEntityPagination.getDataList(), TaskHostStrategy.class);
        return PaginationBuilder.build(StrategyEntityPagination, StrategyList);
    }
}
