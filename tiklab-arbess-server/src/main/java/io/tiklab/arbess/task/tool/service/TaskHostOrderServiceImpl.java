package io.tiklab.arbess.task.tool.service;

import io.tiklab.arbess.task.tool.dao.TaskHostOrderDao;
import io.tiklab.arbess.task.tool.entity.TaskHostOrderEntity;
import io.tiklab.arbess.task.tool.model.TaskHostOrder;
import io.tiklab.arbess.task.tool.model.TaskHostOrderQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHostOrderServiceImpl implements TaskHostOrderService {

    @Autowired
    TaskHostOrderDao hostOrderDao;

    @Override
    public String createHostOrder(TaskHostOrder hostOrder) {
        TaskHostOrderEntity hostOrderEntity = BeanMapper.map(hostOrder, TaskHostOrderEntity.class);
        return hostOrderDao.createTaskHostOrder(hostOrderEntity);
    }

    @Override
    public void deleteHostOrder(String id) {
        hostOrderDao.deleteTaskHostOrder(id);
    }

    @Override
    public void updateHostOrder(TaskHostOrder hostOrder) {
        TaskHostOrderEntity hostOrderEntity = BeanMapper.map(hostOrder, TaskHostOrderEntity.class);
        hostOrderDao.updateTaskHostOrder(hostOrderEntity);
    }

    @Override
    public TaskHostOrder findHostOrder(String id) {
        TaskHostOrderEntity hostOrderEntity = hostOrderDao.findTaskHostOrder(id);
        return BeanMapper.map(hostOrderEntity, TaskHostOrder.class);
    }

    @Override
    public List<TaskHostOrder> findAllHostOrder() {
        List<TaskHostOrderEntity> hostOrderEntityList = hostOrderDao.findAllTaskHostOrder();
        return BeanMapper.mapList(hostOrderEntityList, TaskHostOrder.class);
    }

    @Override
    public List<TaskHostOrder> findHostOrderList(TaskHostOrderQuery query) {
        List<TaskHostOrderEntity> hostOrderEntityList = hostOrderDao.findTaskHostOrderList(query);
        return BeanMapper.mapList(hostOrderEntityList, TaskHostOrder.class);
    }

    @Override
    public List<TaskHostOrder> findHostOrderList(List<String> idList) {
        List<TaskHostOrderEntity> hostOrderEntityList = hostOrderDao.findTaskHostOrderList(idList);
        return BeanMapper.mapList(hostOrderEntityList, TaskHostOrder.class);
    }

    @Override
    public Pagination<TaskHostOrder> findHostOrderPage(TaskHostOrderQuery query) {
        Pagination<TaskHostOrderEntity> hostOrderEntityPagination = hostOrderDao.findTaskHostOrderPage(query);
        List<TaskHostOrder> hostOrderList = BeanMapper.mapList(hostOrderEntityPagination.getDataList(), TaskHostOrder.class);
        return PaginationBuilder.build(hostOrderEntityPagination, hostOrderList);
    }
}
