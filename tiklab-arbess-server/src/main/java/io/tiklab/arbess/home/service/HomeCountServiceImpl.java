package io.tiklab.arbess.home.service;

import io.tiklab.arbess.setting.env.service.EnvService;
import io.tiklab.arbess.setting.group.service.GroupService;
import io.tiklab.arbess.setting.hostgroup.service.AuthHostGroupService;
import io.tiklab.arbess.setting.host.service.AuthHostService;
import io.tiklab.arbess.setting.auth.service.AuthService;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.setting.tool.service.ScmService;
import io.tiklab.arbess.support.condition.service.ConditionService;
import io.tiklab.arbess.support.message.model.TaskMessage;
import io.tiklab.arbess.support.message.model.TaskMessageQuery;
import io.tiklab.arbess.support.message.service.TaskMessageService;
import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.postprocess.service.PostprocessService;
import io.tiklab.arbess.support.trigger.model.Trigger;
import io.tiklab.arbess.support.trigger.model.TriggerQuery;
import io.tiklab.arbess.support.trigger.service.TriggerService;
import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.model.VariableQuery;
import io.tiklab.arbess.support.variable.service.VariableService;
import io.tiklab.arbess.support.webHook.model.WebHook;
import io.tiklab.arbess.support.webHook.model.WebHookQuery;
import io.tiklab.arbess.support.webHook.service.PipelineWebHookServer;
import io.tiklab.licence.appauth.service.ApplyAuthService;
import io.tiklab.licence.licence.model.Version;
import io.tiklab.licence.licence.service.VersionService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.message.message.service.MessageNoticeService;
import io.tiklab.message.setting.service.MessageSendTypeService;
import io.tiklab.privilege.role.service.RoleService;
import io.tiklab.security.backups.service.BackupsDbService;
import io.tiklab.user.directory.service.UserDirService;
import io.tiklab.user.orga.service.OrgaService;
import io.tiklab.user.user.service.UserProcessor;
import io.tiklab.user.usergroup.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeCountServiceImpl implements HomeCountService {


    @Autowired
    UserProcessor userService;

    @Autowired
    OrgaService orgaService;

    @Autowired
    UserDirService userDirService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    RoleService roleService;

    @Autowired
    MessageNoticeService noticeService;

    @Autowired
    MessageSendTypeService sendTypeService;

    @Autowired
    VersionService versionService;

    @Autowired
    ApplyAuthService applyAuthService;

    @Autowired
    BackupsDbService backupsDbService;

    @Autowired
    EnvService envService;

    @Autowired
    ScmService scmService;

    @Autowired
    GroupService groupService;

    @Autowired
    AuthService authService;

    @Autowired
    AuthHostService authHostService;

    @Autowired
    AuthThirdService authThirdService;

    @Autowired
    AuthHostGroupService authHostGroupService;


    @Autowired
    PipelineWebHookServer webHookServer;

    @Autowired
    TriggerService triggerService;

    @Autowired
    VariableService variableService;

    @Autowired
    TaskMessageService taskMessageService;

    @Override
    public Map<String, Object> findCount(){

        Map<String,Object> map = new HashMap<>();

        Integer userNumber = userService.findUserNumber();
        map.put("userNumber",userNumber);

        Integer orgaNumber = orgaService.findOrgaNumber();
        map.put("orgaNumber",orgaNumber);

        Integer userDirNumber = userDirService.findUserDirNumber();
        map.put("userDirNumber",userDirNumber);

        Integer userGroupNumber = userGroupService.findUserGroupNumber();
        map.put("userGroupNumber",userGroupNumber);

        Integer roleNumber = roleService.findRoleNumber();
        map.put("roleNumber",roleNumber);

        Integer noticeNumber = noticeService.findNoticeNumber(PipelineFinal.appName);
        map.put("noticeNumber",noticeNumber);

        Integer sendTypeNumber = sendTypeService.findSendTypeNumber();
        map.put("sendTypeNumber",sendTypeNumber);

        Version version = versionService.getVersion();
        map.put("version",version.getExpired());

        Integer applyAuthNumber = applyAuthService.findApplyAuthNumber();
        map.put("applyAuthNumber",applyAuthNumber);

        String lastBackupsTime = backupsDbService.findLastBackupsTime();
        map.put("lastBackupsTime",lastBackupsTime);

        Integer envNumber = envService.findEnvNumber();
        map.put("envNumber",envNumber);

        Integer scmNumber = scmService.findScmNumber();
        map.put("scmNumber",scmNumber);

        Integer groupNumber = groupService.findGroupNumber();
        map.put("groupNumber",groupNumber);

        Integer authNumber = authService.findAuthNumber();
        map.put("authNumber",authNumber);

        Integer hostNumber = authHostService.findHostNumber();
        map.put("hostNumber",hostNumber);

        Integer serverNumber = authThirdService.findAuthServerNumber();
        map.put("serverNumber",serverNumber);

        Integer hostGroupNumber = authHostGroupService.findHostGroupNumber();
        map.put("hostGroupNumber",hostGroupNumber);

        return map;
    }


    @Override
    public Map<String,Object> findPipelineCount(String pipelineId){
        VariableQuery variableQuery = new VariableQuery();
        variableQuery.setPipelineId(pipelineId);
        variableQuery.setType(1);
        List<Variable> variableList = variableService.findVariableList(variableQuery);

        TaskMessageQuery taskMessageQuery = new TaskMessageQuery();
        taskMessageQuery.setPipelineId(pipelineId);
        taskMessageQuery.setType(1);
        List<TaskMessage> taskMessageList = taskMessageService.findTaskMessageList(taskMessageQuery);

        Map<String,Object> map = new HashMap<>();
        map.put("massageNumber",taskMessageList.size());
        map.put("variableNumber",variableList.size());

        return map;
    }

    @Override
    public Map<String,Object> findTaskCount(String pipelineId,String taskId){
        VariableQuery variableQuery = new VariableQuery();
        // variableQuery.setPipelineId(pipelineId);
        variableQuery.setType(2);
        variableQuery.setTaskId(taskId);
        List<Variable> variableList = variableService.findVariableList(variableQuery);

        TaskMessageQuery taskMessageQuery = new TaskMessageQuery();
        // taskMessageQuery.setPipelineId(pipelineId);
        taskMessageQuery.setType(2);
        taskMessageQuery.setTaskId(taskId);
        List<TaskMessage> taskMessageList = taskMessageService.findTaskMessageList(taskMessageQuery);

        Map<String,Object> map = new HashMap<>();
        map.put("massageNumber",taskMessageList.size());
        map.put("variableNumber",variableList.size());

        return map;
    }



}























