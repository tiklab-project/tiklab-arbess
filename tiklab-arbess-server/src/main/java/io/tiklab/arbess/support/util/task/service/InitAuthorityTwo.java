package io.tiklab.arbess.support.util.task.service;

import io.tiklab.core.utils.UuidGenerator;
import io.tiklab.dsm.support.DsmProcessTask;
import io.tiklab.privilege.role.model.Role;
import io.tiklab.privilege.role.model.RoleQuery;
import io.tiklab.privilege.role.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InitAuthorityTwo implements DsmProcessTask {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RoleService roleService;

    @Override
    public void execute() {
        addFunction();
    }

    public void addFunction() {

        execBatchInsert(addFunctions, "pro_111111");

        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setScope(2);
        roleQuery.setType("2");
        List<Role> roleList = roleService.findRoleList(roleQuery);

        for (Role role : roleList) {
            String[] systemRoleIds = new String[]{};
            String parentId = role.getParentId();
            if (!StringUtils.isEmpty(parentId)){
                systemRoleIds = addFunctions;
            }
            if(systemRoleIds.length == 0){
                Integer businessType = role.getBusinessType();
                if (businessType.equals(2) || businessType.equals(1)){
                    systemRoleIds = addFunctions;
                }
            }

            execBatchInsert(systemRoleIds, role.getId());
        }

    }

    public final String[] addFunctions = new String[]{
            "pip_setting_clone","pip_setting_export"
    };



    public void execBatchInsert(String[] functionIds,String roleId) {

        String sql = "INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, Arrays.stream(functionIds).toList(), functionIds.length,
                (ps, functionId) -> {
                    ps.setString(1, UuidGenerator.getRandomIdByUUID(12));
                    ps.setString(2, roleId);
                    ps.setString(3, functionId);
                }
        );
    }


}
