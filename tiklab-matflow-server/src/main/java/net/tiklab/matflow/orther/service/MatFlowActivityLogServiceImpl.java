package net.tiklab.matflow.orther.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.tiklab.oplog.log.modal.Log;
import net.tiklab.oplog.log.service.LogService;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class MatFlowActivityLogServiceImpl implements MatFlowActivityLogService {

    @Autowired
    LogService logService;

    public void log(String type, String module, Map<String,String> map){
        Log log = new Log();
        log.setActionType(type);
        log.setModule(module);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        String loginId = LoginContext.getLoginId();
        User user = new User();
        user.setId(loginId);
        log.setUser(user);
        log.setBgroup("matflow");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        log.setContent(JSONObject.toJSONString(map));

        logService.createLog(log);
    }


}
