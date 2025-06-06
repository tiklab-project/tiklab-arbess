package io.tiklab.arbess.support.message.service;

import io.tiklab.arbess.support.message.dao.TaskMessageUserDao;
import io.tiklab.arbess.support.message.entity.TaskMessageUserEntity;
import io.tiklab.arbess.support.message.model.TaskMessageUser;
import io.tiklab.arbess.support.message.model.TaskMessageUserQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class TaskMessageUserServiceImpl implements TaskMessageUserService {

    @Autowired
    TaskMessageUserDao messageUserDao;

    @Override
    public String createMessageUser(TaskMessageUser taskMessageUser) {
        TaskMessageUserEntity messageUserEntity = BeanMapper.map(taskMessageUser, TaskMessageUserEntity.class);
        return messageUserDao.createMessageUser(messageUserEntity);
    }

    @Override
    public void deleteMessageUser(String id) {
        messageUserDao.deleteMessageUser(id);
    }

    @Override
    public void updateMessageUser(TaskMessageUser taskMessageUser) {
        TaskMessageUserEntity messageUser = BeanMapper.map(taskMessageUser, TaskMessageUserEntity.class);
        messageUserDao.updateMessageUser(messageUser);
    }

    @Override
    public TaskMessageUser findMessageUser(String id) {
        TaskMessageUserEntity taskMessageUser = messageUserDao.findMessageUser(id);
        return BeanMapper.map(taskMessageUser, TaskMessageUser.class);
    }

    @Override
    public List<TaskMessageUser> findAllMessageUser() {
        List<TaskMessageUserEntity> messageUserList = messageUserDao.findAllMessageUser();
        return BeanMapper.mapList(messageUserList, TaskMessageUser.class);
    }

    @Override
    public List<TaskMessageUser> findMessageUserList(List<String> idList) {
        List<TaskMessageUserEntity> messageUserList = messageUserDao.findMessageUserList(idList);
        return BeanMapper.mapList(messageUserList, TaskMessageUser.class);
    }

    @Override
    public List<TaskMessageUser> findMessageUserList(TaskMessageUserQuery userQuery) {
        List<TaskMessageUserEntity> messageUserList = messageUserDao.findMessageUserList(userQuery);
        return BeanMapper.mapList(messageUserList, TaskMessageUser.class);
    }

    @Override
    public Pagination<TaskMessageUser> findMessageUserPage(TaskMessageUserQuery userQuery) {

        Pagination<TaskMessageUserEntity> messageUserPage = messageUserDao.findMessageUserPage(userQuery);
        List<TaskMessageUserEntity> dataList = messageUserPage.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(messageUserPage, new ArrayList<>());
        }
        List<TaskMessageUser> taskMessageUsers = BeanMapper.mapList(dataList, TaskMessageUser.class);
        return PaginationBuilder.build(messageUserPage, taskMessageUsers);
    }
    
}
