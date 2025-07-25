package io.tiklab.arbess.task.checkpoint.service;

import io.tiklab.arbess.task.checkpoint.dao.TaskCheckPointDao;
import io.tiklab.arbess.task.checkpoint.entity.TaskCheckPointEntity;
import io.tiklab.arbess.task.checkpoint.model.TaskCheckPoint;
import io.tiklab.arbess.task.checkpoint.model.TaskCheckPointQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCheckPointServiceImpl implements TaskCheckPointService {

    @Autowired
    TaskCheckPointDao checkPointDao;

    @Override
    public String createCheckPoint(TaskCheckPoint checkPoint) {
        TaskCheckPointEntity checkPointEntity = BeanMapper.map(checkPoint, TaskCheckPointEntity.class);
        return checkPointDao.createTaskCheckPoint(checkPointEntity);
    }

    @Override
    public void deleteCheckPoint(String id) {
        checkPointDao.deleteTaskCheckPoint(id);
    }

    @Override
    public void updateCheckPoint(TaskCheckPoint checkPoint) {
        TaskCheckPointEntity checkPointEntity = BeanMapper.map(checkPoint, TaskCheckPointEntity.class);
        checkPointDao.updateTaskCheckPoint(checkPointEntity);
    }

    @Override
    public TaskCheckPoint findCheckPoint(String id) {
        TaskCheckPointEntity checkPointEntity = checkPointDao.findTaskCheckPoint(id);
        return BeanMapper.map(checkPointEntity, TaskCheckPoint.class);
    }

    @Override
    public List<TaskCheckPoint> findAllCheckPoint() {
        List<TaskCheckPointEntity> checkPointEntityList = checkPointDao.findAllTaskCheckPoint();
        return BeanMapper.mapList(checkPointEntityList, TaskCheckPoint.class);
    }

    @Override
    public List<TaskCheckPoint> findCheckPointList(TaskCheckPointQuery query) {
        List<TaskCheckPointEntity> checkPointEntityList = checkPointDao.findTaskCheckPointList(query);
        return BeanMapper.mapList(checkPointEntityList, TaskCheckPoint.class);
    }

    @Override
    public List<TaskCheckPoint> findCheckPointList(List<String> idList) {
        List<TaskCheckPointEntity> checkPointEntityList = checkPointDao.findTaskCheckPointList(idList);
        return BeanMapper.mapList(checkPointEntityList, TaskCheckPoint.class);
    }

    @Override
    public Pagination<TaskCheckPoint> findCheckPointPage(TaskCheckPointQuery query) {
        Pagination<TaskCheckPointEntity> checkPointEntityPagination = checkPointDao.findTaskCheckPointPage(query);
        List<TaskCheckPoint> checkPointList = BeanMapper.mapList(checkPointEntityPagination.getDataList(), TaskCheckPoint.class);
        return PaginationBuilder.build(checkPointEntityPagination, checkPointList);
    }
}
