package io.thoughtware.matflow.support.agent.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.matflow.support.agent.dao.AgentDao;
import io.thoughtware.matflow.support.agent.entity.AgentEntity;
import io.thoughtware.matflow.support.agent.model.Agent;
import io.thoughtware.matflow.support.agent.model.AgentQuery;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.ws.server.SocketServerHandler;
import io.thoughtware.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static io.thoughtware.matflow.support.util.util.PipelineFinal.DEFAULT;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    AgentDao agentDao;

    @Override
    public void initAgent(Agent agent) {
        AgentQuery agentQuery = new AgentQuery();
        agentQuery.setAddress(agent.getAddress());
        List<Agent> agentList = findAgentList(agentQuery);
        if (agentList.isEmpty()) {
            // 如果不存在连接，设置为默认
            List<AgentEntity> agentList1 = agentDao.findAgentList();
            if (Objects.isNull(agentList1) || agentList1.isEmpty()){
                agent.setBusinessType(DEFAULT);
            }
            createAgent(agent);
        }else {
            Agent agent1 = agentList.get(0);
            agent1.setCreateTime(PipelineUtil.date(1));
            updateAgent(agent1);
        }
    }

    @Override
    public String createAgent(Agent agent) {

        AgentEntity agentEntity = BeanMapper.map(agent, AgentEntity.class);

        return agentDao.createAgent(agentEntity);
    }

    @Override
    public void updateAgent(Agent agent) {
        AgentEntity agentEntity = BeanMapper.map(agent, AgentEntity.class);

        agentDao.updateAgent(agentEntity);
    }

    public Agent findDefaultAgent(){
        AgentQuery agentQuery = new AgentQuery();
        agentQuery.setBusinessType(DEFAULT);
        List<Agent> agentList = findAgentList(agentQuery);
        if (agentList.isEmpty()){
            return null;
        }
        return agentList.get(0);
    }


    @Override
    public void updateDefaultAgent(String id) {
        Agent agent = findAgent(id);

        Agent agent1 = findDefaultAgent();
        if (!Objects.isNull(agent1)){
            agent1.setBusinessType("local");
            updateAgent(agent1);
        }
        agent.setBusinessType(DEFAULT);
        updateAgent(agent);
    }

    @Override
    public void deleteAgent(String id) {

        agentDao.deleteAgent(id);
    }

    @Override
    public Agent findAgent(String id) {
        AgentEntity agentEntity = agentDao.findAgent(id);
        return BeanMapper.map(agentEntity, Agent.class);
    }

    @Override
    public List<Agent> findAgentList(AgentQuery agentQuery) {
        List<AgentEntity> agentEntityList = agentDao.findAgentList(agentQuery);
        if (Objects.isNull(agentEntityList) || agentEntityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<Agent> agentList = BeanMapper.mapList(agentEntityList, Agent.class);
        for (Agent agent : agentList) {
            isConnect(agent);
        }
        return agentList;
    }

    @Override
    public Pagination<Agent> findAgentPage(AgentQuery agentQuery) {
        Pagination<AgentEntity> agentEntityPage = agentDao.findAgentPage(agentQuery);
        List<AgentEntity> agentEntityList = agentEntityPage.getDataList();
        if (Objects.isNull(agentEntityList) || agentEntityList.isEmpty()) {
            return PaginationBuilder.build(agentEntityPage,Collections.emptyList());
        }
        List<Agent> agentList = BeanMapper.mapList(agentEntityList, Agent.class);
        for (Agent agent : agentList) {
            isConnect(agent);
        }
        return PaginationBuilder.build(agentEntityPage,agentList);
    }


    public void isConnect(Agent agent){
        WebSocketSession session = SocketServerHandler.sessionMap.get(agent.getAddress());
        agent.setConnect(!Objects.isNull(session));
    }
}
