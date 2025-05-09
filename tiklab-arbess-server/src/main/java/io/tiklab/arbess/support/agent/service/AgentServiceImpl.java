package io.tiklab.arbess.support.agent.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.arbess.support.agent.dao.AgentDao;
import io.tiklab.arbess.support.agent.entity.AgentEntity;
import io.tiklab.arbess.support.agent.model.Agent;
import io.tiklab.arbess.support.agent.model.AgentQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.ws.server.SocketServerHandler;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.DEFAULT;

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
            agent.setDisplayType("no");
            List<AgentEntity> agentList1 = agentDao.findAgentList();
            if (Objects.isNull(agentList1) || agentList1.isEmpty()){
                agent.setBusinessType(DEFAULT);
                agent.setDisplayType("yes");
            }
            createAgent(agent);
        }else {
            agent.setId(agentList.get(0).getId());
            agent.setCreateTime(PipelineUtil.date(1));
            updateAgent(agent);
        }
    }

    @Override
    public void updateAgentStatus(AgentQuery agentQuery) {
        List<String> agentIdList = agentQuery.getAgentIdList();
        if (agentIdList.isEmpty()){
            return;
        }
        for (String agentId : agentIdList) {
            Agent agent1 = findAgent(agentId);
            agent1.setDisplayType("yes");
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

    @Override
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
