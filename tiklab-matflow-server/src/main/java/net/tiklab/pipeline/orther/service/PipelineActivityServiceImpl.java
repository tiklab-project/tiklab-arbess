package net.tiklab.pipeline.orther.service;

import com.alibaba.fastjson.JSONObject;
import net.tiklab.oplog.log.modal.OpLog;
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
     * @param massage 日志信息
     */
    public void log(String type, String templateId,String massage){
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
        HashMap<String, String> map = new HashMap<>();
        map.put("massage", massage);
        log.setContent(JSONObject.toJSONString(map));
        logService.createLog(log);
    }

    //public OpLog findLog(){
    //
    //}

}
