package net.tiklab.matflow.orther.service;

import com.alibaba.fastjson.JSONObject;
import net.tiklab.oplog.log.modal.OpLog;
import net.tiklab.oplog.log.modal.OpLogTemplate;
import net.tiklab.oplog.log.service.OpLogService;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class MatFlowActivityLogServiceImpl implements MatFlowActivityLogService {

    @Autowired
    OpLogService logService;

    /**
     * 创建日志
     * @param type 日志类型
     * @param module 日志模块
     * @param map 日志信息
     */
    public void log(String type, String module, Map<String,String> map){
        OpLog log = new OpLog();
        OpLogTemplate opLogTemplate = new OpLogTemplate();
        opLogTemplate.setId("matflow");
        log.setActionType(type);
        log.setModule(module);
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


}
