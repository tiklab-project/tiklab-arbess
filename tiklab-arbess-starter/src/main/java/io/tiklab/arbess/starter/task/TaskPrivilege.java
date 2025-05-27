package io.tiklab.arbess.starter.task;

import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import io.tiklab.privilege.dmRole.model.DmRole;
import io.tiklab.privilege.dmRole.model.DmRoleQuery;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.privilege.role.model.Role;
import io.tiklab.privilege.role.model.RoleQuery;
import io.tiklab.privilege.role.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskPrivilege implements TiklabApplicationRunner {


    @Autowired
    RoleService roleService;

    @Autowired
    DmRoleService dmRoleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("Load init privilege tasks......");
        cleanPrivilege();
        logger.info("Load init privilege success!");
    }

    public void cleanPrivilege(){

        String roleId = "2";
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setParentId(roleId);
        roleQuery.setType("2");
        roleQuery.setScope(2);
        List<Role> roleList = roleService.findRoleList(roleQuery);   // 查询项目角色
        for (Role role : roleList) {
            DmRoleQuery dmRoleQuery = new DmRoleQuery();
            dmRoleQuery.setRoleId(role.getId());
            List<DmRole> dmRoleList = dmRoleService.findDmRoleListNoQuery(dmRoleQuery);  // 查询关联的项目角色

            // 删除项目角色以及关联关系
            for (DmRole dmRole : dmRoleList) {
                dmRoleService.deleteDmRole(dmRole.getId());
            }
        }
        // 删除角色
        roleService.deleteRole(roleId);
    }


}
