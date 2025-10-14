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
public class InitAuthority implements DsmProcessTask {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RoleService roleService;

    @Override
    public void execute() {
        addSysRoleFunction();
    }

    private void addSysRoleFunction() {
        for (String sysRoleId : sysRoleIds) {
            String[] systemRoleIds = findSystemRoleIds(sysRoleId);
            execBatchInsert(systemRoleIds, sysRoleId);
        }
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setScope(2);
        roleQuery.setType("2");
        List<Role> roleList = roleService.findRoleList(roleQuery);

        for (Role role : roleList) {
            String[] systemRoleIds = new String[]{};
            String parentId = role.getParentId();
            if (!StringUtils.isEmpty(parentId)){
                systemRoleIds = findSystemRoleIds(parentId);
            }
            if(systemRoleIds.length == 0){
                Integer businessType = role.getBusinessType();
                if (businessType.equals(2) || businessType.equals(1)){
                    systemRoleIds = domainAdminFunctionIds;
                }else {
                    systemRoleIds = domainAdminFunctionIds;
                }
            }

            execBatchInsert(systemRoleIds, role.getId());
        }
    }

    private String[] findSystemRoleIds(String id) {
        switch (id){
            case "111111","1" -> {return allSysFunctionIds;}
            case "696e8b4575bb" -> {return sysFunctionIds;}

            // 项目
            case "pro_111111" -> {return domainAdminFunctionIds;}
            case "1efcc15ab020" -> {return domainFunctionIds;}

            default -> {return new String[]{};}
        }
    }


    private final String[]  sysRoleIds = new String[]{"111111", "1","696e8b4575bb","pro_111111","1efcc15ab020"};


    private final String[] allSysFunctionIds = new String[]{
            "user","user_add_user","user_update_user","user_delete_user","user_update_user_password","user_update_user_recover",
            "orga","orga_add_orga","orga_update_orga","orga_delete_orga","orga_add_user","orga_delete_user",
            "user_group","user_add_group","user_update_group","user_delete_group","user_add_group_user","user_delete_group_user",
            "user_dir","user_dir_sync","user_dir_open","user_dir_config","user_dir_forbid",
            "permission","permission_role_add","permission_role_delete","permission_role_update","permission_role_user_add","permission_role_user_delete","permission_role_permission_update","permission_role_update_default",
            "message","message_update_send_way","message_update_plan_status","message_update_plan_send_way","message_plan_user_add","message_plan_delete",
            "openapi","openapi_add","openapi_delete","openapi_find",
            "backups_and_recover","backups_update_status","backups_create","recover_create",
            "log","log_find",
            "ip_whitelist","ip_whitelist_white","ip_whitelist_black",
            "licence","licence_import",
            "apply_limits","apply_limits_add_user","apply_limits_delete_user","apply_limits_open_user","apply_limits_close_user",
            "custom_logo","custom_logo_update_title","custom_logo_update_status","custom_logo_update_pic",

            // 流水线权限
            "pipeline","pipeline_create","pipeline_run","pipeline_update","pipeline_delete","pipeline_setting","pipeline_clone","pipeline_import",
            "pipeline_history","pipeline_history_delete","pipeline_history_rollback","pipeline_history_run",
            "pipeline_release","pipeline_release_add","pipeline_release_update","pipeline_release_delete",
            "pipeline_statistics","pipeline_overview_statistics","pipeline_run_statistics","pipeline_result_statistics",
            "pipeline_application","pipeline_create_application","pipeline_update_application","pipeline_delete_application",
            "pipeline_environment","pipeline_environment_add","pipeline_environment_update","pipeline_environment_delete",
            "pipeline_variable","pipeline_variable_add","pipeline_variable_update","pipeline_variable_delete",
            "pipeline_agent","pipeline_agent_add","pipeline_agent_update","pipeline_agent_delete",
            "pipeline_host","pipeline_host_add","pipeline_host_update","pipeline_host_delete",
            "pipeline_host_group","pipeline_host_group_add","pipeline_host_group_update","pipeline_host_group_delete",
            "pipeline_kubernetes_cluster","pipeline_kubernetes_cluster_add","pipeline_kubernetes_cluster_update","pipeline_kubernetes_cluster_delete",
            "pipeline_tool_integration","pipeline_tool_integration_add","pipeline_tool_integration_update","pipeline_tool_integration_delete",
            "pipeline_service_integration","pipeline_service_integration_add","pipeline_service_integration_update","pipeline_service_integration_delete",
            "pipeline_resource_monitor","pipeline_resource_update"
    };

    private final String[] sysFunctionIds = new String[]{

            // "user",
            // "orga",
            // "user_group",
            // "user_dir",
            // "permission",
            // "message",
            // "openapi",
            // "backups_and_recover",
            // "log",
            // "ip_whitelist",
            // "licence",
            // "apply_limits",
            // "custom_logo",

            // 流水线权限
            "pipeline","pipeline_create","pipeline_run","pipeline_update","pipeline_delete",
            "pipeline_history","pipeline_history_delete","pipeline_history_run",
            "pipeline_release",
            // "pipeline_statistics",
            // "pipeline_application",
            // "pipeline_environment",
            // "pipeline_variable",
            // "pipeline_agent",
            // "pipeline_host",
            // "pipeline_host_group",
            // "pipeline_kubernetes_cluster",
            // "pipeline_tool_integration",
            // "pipeline_service_integration",
            // "pipeline_resource_monitor",
    };


    private final String[] domainAdminFunctionIds = new String[]{
            "pip_design","pip_design_run","pip_design_update","pip_design_webhook","pip_design_timeout","pip_design_var_add","pip_design_var_update","pip_design_var_delete",
            "pip_history","pip_history_delete","pip_history_rollback","pip_history_run",
            "pip_test_report","pip_test_report_overview","pip_test_report_sonarqube","pip_test_report_sonarqube_delete","pip_test_report_sourcefare","pip_test_report_sourcefare_delete",
            "pip_statistics","pip_statistics_overview","pip_statistics_run","pip_statistics_result",
            "pip_setting","pip_setting_update","pip_setting_delete","pip_setting_clean",
            "pip_setting_msg","domain_user","domain_role","domain_message",
            "pip_test_report_sonarqube_scan","pip_test_report_overview",
            "pip_test_report_testhubo","pip_test_report_testhubo_find","pip_test_report_testhubo_delete",
            "pip_test_report_sonarqube_find","pip_test_report_sourcefare_find","pip_test_report_overview_find",

            // 系统全局设置权限
            "domain_user_add","domain_user_delete","domain_user_update",
            "domain_role_add","domain_role_delete","domain_role_update","domain_role_user_add","domain_role_user_delete","domain_role_permission_update","domain_role_update_default",
            "domain_message_status","domain_message_way","domain_message_user_add","domain_message_user_delete"
    };

    private final String[] domainFunctionIds = new String[]{
            "pip_design","pip_design_run","pip_design_update",
            "pip_history","pip_history_delete","pip_history_run",
            "pip_setting","pip_setting_update",
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

    // public String generateBatchInsertSql(String[] functionIds,String roleId) {
    //
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ");
    //
    //     for (int i = 0; i < functionIds.length; i++) {
    //         String id = UuidGenerator.getRandomIdByUUID(12); // 生成随机id
    //         String functionId = functionIds[i];
    //
    //         sb.append("('").append(id).append("', '").append(roleId).append("', '").append(functionId).append("')");
    //
    //         if (i < functionIds.length - 1) {
    //             sb.append(","); // 多行之间加逗号
    //         }
    //     }
    //     sb.append(";");
    //     return sb.toString();
    // }
















}
