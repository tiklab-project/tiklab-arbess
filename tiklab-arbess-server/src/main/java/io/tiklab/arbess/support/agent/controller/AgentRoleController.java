package io.tiklab.arbess.support.agent.controller;

import io.tiklab.arbess.support.agent.model.AgentRole;
import io.tiklab.arbess.support.agent.model.AgentRoleQuery;
import io.tiklab.arbess.support.agent.service.AgentRoleService;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/agentRole")
public class AgentRoleController {

    @Autowired
    AgentRoleService agentRoleService;


    @RequestMapping(path="/updateAgentRole",method = RequestMethod.POST)
    public Result<Void> updateAgentRole(@RequestBody @NotNull @Valid AgentRole agentRole){

        agentRoleService.updateAgentRole(agentRole);

        return Result.ok();
    }


    @RequestMapping(path="/findAgentRoleList",method = RequestMethod.POST)
    public Result<List<AgentRole>> findAgentRoleList(@RequestBody @NotNull @Valid AgentRoleQuery agentRoleQuery){

        List<AgentRole> agentRoleList = agentRoleService.findAgentRoleList(agentRoleQuery);

        return Result.ok(agentRoleList);
    }


    @RequestMapping(path="/findAgentRolePage",method = RequestMethod.POST)
    public Result<Pagination<AgentRole>> findAgentRolePage(@RequestBody @NotNull @Valid AgentRoleQuery agentRoleQuery){

        Pagination<AgentRole> agentRolePage = agentRoleService.findAgentRolePage(agentRoleQuery);

        return Result.ok(agentRolePage);
    }

    @RequestMapping(path="/deleteAgentRole",method = RequestMethod.POST)
    public Result<Void> deleteAgentRole( @NotNull String id){

        agentRoleService.deleteAgentRole(id);

        return Result.ok();
    }


    @RequestMapping(path="/findAgentRole",method = RequestMethod.POST)
    public Result<AgentRole> findAgentRole( @NotNull String id){

        AgentRole agentRole = agentRoleService.findAgentRole(id);

        return Result.ok(agentRole);
    }

}
