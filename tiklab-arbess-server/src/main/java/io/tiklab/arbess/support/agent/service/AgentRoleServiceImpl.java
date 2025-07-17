package io.tiklab.arbess.support.agent.service;

import io.tiklab.arbess.support.agent.dao.AgentRoleDao;
import io.tiklab.arbess.support.agent.entity.AgentRoleEntity;
import io.tiklab.arbess.support.agent.model.AgentRole;
import io.tiklab.arbess.support.agent.model.AgentRoleQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AgentRoleServiceImpl implements AgentRoleService {

    @Autowired
    AgentRoleDao agentRoleDao;

    @Override
    public String createAgentRole(AgentRole agentRole) {

        AgentRoleEntity agentRoleEntity = BeanMapper.map(agentRole, AgentRoleEntity.class);

        return agentRoleDao.createAgentRole(agentRoleEntity);
    }

    @Override
    public void updateAgentRole(AgentRole agentRole) {
        AgentRoleEntity agentRoleEntity = BeanMapper.map(agentRole, AgentRoleEntity.class);

        agentRoleDao.updateAgentRole(agentRoleEntity);
    }

    @Override
    public void deleteAgentRole(String id) {

        agentRoleDao.deleteAgentRole(id);
    }

    @Override
    public AgentRole findAgentRole(String id) {
        AgentRoleEntity agentRoleEntity = agentRoleDao.findAgentRole(id);
        return BeanMapper.map(agentRoleEntity, AgentRole.class);
    }

    @Override
    public List<AgentRole> findAgentRoleList(AgentRoleQuery agentRoleQuery) {
        List<AgentRoleEntity> agentRoleEntityList = agentRoleDao.findAgentRoleList(agentRoleQuery);
        if (Objects.isNull(agentRoleEntityList) || agentRoleEntityList.isEmpty()) {
            return new ArrayList<>();
        }
        return BeanMapper.mapList(agentRoleEntityList, AgentRole.class);
    }

    @Override
    public List<AgentRole> findAgentRoleList(List<String> agentRoleIdList) {
        List<AgentRoleEntity> agentRoleEntityList = agentRoleDao.findAgentRoleList(agentRoleIdList);
        if (Objects.isNull(agentRoleEntityList) || agentRoleEntityList.isEmpty()) {
            return new ArrayList<>();
        }
        return BeanMapper.mapList(agentRoleEntityList, AgentRole.class);
    }

    @Override
    public List<AgentRole> findAllAgentRole() {
        List<AgentRoleEntity> agentRoleEntityList = agentRoleDao.findAllAgentRole();
        if (Objects.isNull(agentRoleEntityList) || agentRoleEntityList.isEmpty()) {
            return new ArrayList<>();
        }
        return BeanMapper.mapList(agentRoleEntityList, AgentRole.class);
    }

    @Override
    public Pagination<AgentRole> findAgentRolePage(AgentRoleQuery agentRoleQuery) {
        Pagination<AgentRoleEntity> agentRoleEntityPage = agentRoleDao.findAgentRolePage(agentRoleQuery);
        List<AgentRoleEntity> agentRoleEntityList = agentRoleEntityPage.getDataList();
        if (Objects.isNull(agentRoleEntityList) || agentRoleEntityList.isEmpty()) {
            return PaginationBuilder.build(agentRoleEntityPage,new ArrayList<>());
        }
        List<AgentRole> agentRoleList = BeanMapper.mapList(agentRoleEntityList, AgentRole.class);
        return PaginationBuilder.build(agentRoleEntityPage,agentRoleList);
    }



}
