package net.tiklab.matflow.orther.service;

import com.alibaba.fastjson.JSONObject;
import net.tiklab.core.page.Page;
import net.tiklab.core.page.Pagination;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.model.PipelineActivity;
import net.tiklab.oplog.log.modal.OpLog;
import net.tiklab.oplog.log.modal.OpLogQuery;
import net.tiklab.oplog.log.modal.OpLogTemplate;
import net.tiklab.oplog.log.service.OpLogService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.*;

/**
 * 动态
 */

@Exporter
@Service
public class PipelineActivityServiceImpl implements PipelineActivityService {

    @Autowired
    OpLogService logService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineActivityServiceImpl.class);

    /**
     * 创建日志
     * @param type 日志类型 (创建 create，删除 delete，执行 exec，更新 update)
     * @param templateId 模板id (创建 流水线--pipeline，运行 pipelineExec，凭证--pipelineProof,其他--pipelineOther)
     * @param map 日志信息
     */
    @Override
    public void log(String type, String templateId,HashMap<String, String> map){
        OpLog log = new OpLog();
        OpLogTemplate opLogTemplate = new OpLogTemplate();
        opLogTemplate.setId(templateId);
        log.setActionType(type);
        log.setModule("matflow");
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        String loginId = LoginContext.getLoginId();
        User user = new User();
        user.setId(loginId);
        log.setUser(user);
        log.setBgroup("matflow");
        log.setOpLogTemplate(opLogTemplate);
        log.setContent(JSONObject.toJSONString(map));
        logService.createLog(log);
    }

    /**
     * 查询日志
     * @return 日志
     */
    @Override
    public List<PipelineActivity> findLog(){
        OpLogQuery opLogQuery = new OpLogQuery();
        opLogQuery.setBgroup("matflow");
        List<OpLog> logList = logService.findLogList(opLogQuery);

        if (logList == null){
            return null;
        }
        logList.sort(Comparator.comparing(OpLog::getTimestamp).reversed());
        List<PipelineActivity> pipelineActivities = new ArrayList<>();
        if (logList.size() < 6){
            for (OpLog opLog : logList) {
                PipelineActivity pipelineActivity = updateActivities(opLog);
                pipelineActivities.add(pipelineActivity);
            }
        }else {
            for (int i = 0; i < 6; i++) {
                PipelineActivity pipelineActivity = updateActivities(logList.get(i));
                pipelineActivities.add(pipelineActivity);
            }
        }

        pipelineActivities.sort(Comparator.comparing(PipelineActivity::getCreateTime).reversed());
        return pipelineActivities;
    }

    private PipelineActivity updateActivities(OpLog opLog){
        String content = opLog.getContent();
        JSONObject jsonObject = JSONObject.parseObject(content);
        PipelineActivity pipelineActivity = new PipelineActivity();
        pipelineActivity.setPipelineId(jsonObject.getString("pipelineId"));
        pipelineActivity.setMessage(jsonObject.getString("message"));
        pipelineActivity.setCreateTime(String.valueOf(opLog.getTimestamp()));
        pipelineActivity.setType(opLog.getActionType());
        pipelineActivity.setUserName(opLog.getUser().getName());
        pipelineActivity.setPipelineName(jsonObject.getString("pipelineName"));
        pipelineActivity.setTemplateType(opLog.getOpLogTemplate().getId());
        return pipelineActivity;
    }



}
































