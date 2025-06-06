package io.tiklab.arbess.starter.task;


import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import io.tiklab.privilege.dmRole.model.DmRole;
import io.tiklab.privilege.dmRole.model.DmRoleQuery;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.privilege.function.model.Function;
import io.tiklab.privilege.function.service.FunctionService;
import io.tiklab.privilege.role.model.Role;
import io.tiklab.privilege.role.model.RoleFunction;
import io.tiklab.privilege.role.model.RoleFunctionQuery;
import io.tiklab.privilege.role.service.RoleFunctionService;
import io.tiklab.privilege.role.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskInitPrivilege implements TiklabApplicationRunner {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    FunctionService functionService;

    @Autowired
    RoleFunctionService roleFunctionService;

    @Autowired
    DmRoleService dmRoleService;

    @Autowired
    RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("The update privilege.....");
        // findDomainRole();
        logger.info("The update privilege end.");
    }


    public void findDomainRole(){

        String[] privilegeList = new String[]{"66660f6c591d","77770f6c591d","88880f6c591d"};

        List<Pipeline> allPipeline = pipelineService.findAllPipeline();
        List<String> PipleineIdList = allPipeline.stream().map(Pipeline::getId).collect(Collectors.toList());

        for (String pipelineId : PipleineIdList) {
            DmRoleQuery dmRoleQuery = new DmRoleQuery();
            dmRoleQuery.setDomainId(pipelineId);
            List<DmRole> dmRoleList = dmRoleService.findDmRoleListNoQuery(dmRoleQuery);

            Optional<DmRole> first = dmRoleList.stream().filter(a -> !Objects.isNull(a))
                    .filter(a -> a.getBusinessType() == 2)
                    .findFirst();
            boolean present = first.isPresent();
            DmRole dmRole;
            if (present) {
                dmRole = first.get();
            }else {
                logger.info("pipelineId:{},找不到该流水线的超级管理员角色,重新创建。",pipelineId);
                Role role = roleService.findRole("pro_111111");
                String roleId = roleService.createRole(role);
                dmRole = new DmRole();
                dmRole.setDomainId(pipelineId);
                dmRole.setBusinessType(2);
                dmRole.setRole(new Role(roleId));
                String dmRoleId = dmRoleService.saveDmRole(dmRole);
                dmRole.setId(dmRoleId);
            }

            RoleFunctionQuery roleFunctionQuery = new RoleFunctionQuery();
            roleFunctionQuery.setRoleId(dmRole.getRole().getId());
            List<RoleFunction> roleFunctionList = roleFunctionService.findRoleFunctionList(roleFunctionQuery);

            for (String s : privilegeList) {

                List<RoleFunction> list = roleFunctionList.stream()
                        .filter(a -> !Objects.isNull(a.getFunction()))
                        .filter(a -> a.getFunction().getId().equals(s))
                        .collect(Collectors.toList());
                if (!list.isEmpty()){
                    continue;
                }
                logger.info("pipelineId:{},新增角色权限：{}",pipelineId,s);
                RoleFunction roleFunction = new RoleFunction();
                roleFunction.setFunction(new Function(s));
                roleFunction.setRole(dmRole.getRole());
                roleFunctionService.createRoleFunction(roleFunction);
            }


        }


    }




}
