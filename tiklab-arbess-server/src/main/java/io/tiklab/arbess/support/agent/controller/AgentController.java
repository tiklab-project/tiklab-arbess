package io.tiklab.arbess.support.agent.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.arbess.support.agent.model.Agent;
import io.tiklab.arbess.support.agent.model.AgentQuery;
import io.tiklab.arbess.support.agent.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    AgentService agentService;

    @RequestMapping(path="/findAgentList",method = RequestMethod.POST)
    public Result<List<Agent>> findAgentList(@RequestBody @NotNull @Valid AgentQuery agentQuery){

        List<Agent> agentList = agentService.findAgentList(agentQuery);

        return Result.ok(agentList);
    }


    @RequestMapping(path="/findAgentPage",method = RequestMethod.POST)
    public Result<Pagination<Agent>> findAgentPage(@RequestBody @NotNull @Valid AgentQuery agentQuery){

        Pagination<Agent> agentPage = agentService.findAgentPage(agentQuery);

        return Result.ok(agentPage);
    }

    @RequestMapping(path="/deleteAgent",method = RequestMethod.POST)
    public Result<Void> deleteAgent( @NotNull String id){

        agentService.deleteAgent(id);

        return Result.ok();
    }

    @RequestMapping(path="/updateDefaultAgent",method = RequestMethod.POST)
    public Result<Void> updateDefaultAgent( @NotNull String id){

        agentService.updateDefaultAgent(id);

        return Result.ok();
    }


    @RequestMapping(path="/updateAgentStatus",method = RequestMethod.POST)
    public Result<Void> updateAgentStatus( @RequestBody @NotNull @Valid AgentQuery agentQuery){

        agentService.updateAgentStatus(agentQuery);

        return Result.ok();
    }


}
