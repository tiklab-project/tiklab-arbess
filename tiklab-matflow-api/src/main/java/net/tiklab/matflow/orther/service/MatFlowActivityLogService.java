package net.tiklab.matflow.orther.service;

import java.util.Map;

public interface MatFlowActivityLogService {

    void log(String type, String module, Map<String,String> map);
}
