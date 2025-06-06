package io.tiklab.arbess.support.message.service;

import io.tiklab.arbess.support.message.model.TaskMessageType;
import io.tiklab.arbess.support.message.model.TaskMessageTypeQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.arbess.support.message.dao.TaskMessageTypeDao;
import io.tiklab.arbess.support.message.entity.TaskMessageTypeEntity;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class TaskMessageTypeServiceImpl implements TaskMessageTypeService {

    @Autowired
    TaskMessageTypeDao messageTypeDao;

    @Override
    public String createMessageType(TaskMessageType taskMessageType) {
        TaskMessageTypeEntity messageTypeEntity = BeanMapper.map(taskMessageType, TaskMessageTypeEntity.class);
        return messageTypeDao.createMessageType(messageTypeEntity);
    }

    @Override
    public void deleteMessageType(String id) {
        messageTypeDao.deleteMessageType(id);
    }

    @Override
    public void updateMessageType(TaskMessageType taskMessageType) {
        TaskMessageTypeEntity messageType = BeanMapper.map(taskMessageType, TaskMessageTypeEntity.class);
        messageTypeDao.updateMessageType(messageType);
    }

    @Override
    public TaskMessageType findMessageType(String id) {
        TaskMessageTypeEntity taskMessageType = messageTypeDao.findMessageType(id);
        return BeanMapper.map(taskMessageType, TaskMessageType.class);
    }

    @Override
    public List<TaskMessageType> findAllMessageType() {
        List<TaskMessageTypeEntity> messageTypeList = messageTypeDao.findAllMessageType();
        return BeanMapper.mapList(messageTypeList, TaskMessageType.class);
    }

    @Override
    public List<TaskMessageType> findMessageTypeList(List<String> idList) {
        List<TaskMessageTypeEntity> messageTypeList = messageTypeDao.findMessageTypeList(idList);
        return BeanMapper.mapList(messageTypeList, TaskMessageType.class);
    }

    @Override
    public List<TaskMessageType> findMessageTypeList(TaskMessageTypeQuery typeQuery) {
        List<TaskMessageTypeEntity> messageTypeList = messageTypeDao.findMessageTypeList(typeQuery);
        return BeanMapper.mapList(messageTypeList, TaskMessageType.class);
    }

    @Override
    public Pagination<TaskMessageType> findMessageTypePage(TaskMessageTypeQuery typeQuery) {

        Pagination<TaskMessageTypeEntity> messageTypePage = messageTypeDao.findMessageTypePage(typeQuery);
        List<TaskMessageTypeEntity> dataList = messageTypePage.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(messageTypePage, new ArrayList<>());
        }
        List<TaskMessageType> taskMessageTypes = BeanMapper.mapList(dataList, TaskMessageType.class);
        return PaginationBuilder.build(messageTypePage, taskMessageTypes);
    }
}
