package net.tiklab.pipeline.orther.service;


import com.alibaba.fastjson.JSONObject;
import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.definition.service.PipelineService;
import net.tiklab.pipeline.orther.model.PipelineActivity;
import net.tiklab.pipeline.orther.model.PipelineActivityQuery;
import net.tiklab.pipeline.orther.model.PipelineFollow;
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

@Service
@Exporter
public class PipelineHomeServiceImpl implements PipelineHomeService {

    @Autowired
    OpLogService logService;

    @Autowired
    PipelineFollowService pipelineFollowService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineActivityService pipelineActivityService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineHomeServiceImpl.class);


    //更新收藏信息
    @Override
    public String updateFollow(PipelineFollow pipelineFollow){
      return  pipelineFollowService.updateFollow(pipelineFollow);
    }

    //获取用户动态
    @Override
    public PipelineActivityQuery findUserActivity(PipelineActivityQuery pipelineActivityQuery){
        PipelineActivityQuery query = new PipelineActivityQuery();
        List<Pipeline> userPipeline = pipelineService.findAllPipeline(pipelineActivityQuery.getUserId());
        if (userPipeline == null){
            return null;
        }
        pipelineActivityQuery.setPipelineList(userPipeline);
        //获取流水线动态
        List<PipelineActivity> list = pipelineActivityService.findUserActivity(pipelineActivityQuery);
        query.setPageSize(pipelineActivityQuery.getPageSize());
        query.setPage(pipelineActivityQuery.getPage());
        query.setListSize(list.size());
        query.setPageNumber(1);
        if (list.size() < 10){
            query.setDataList(list);
            return query;
        }
        //默认0-10
        if (pipelineActivityQuery.getPage()+ pipelineActivityQuery.getPageSize() == 11){
            query.setDataList(list.subList(0, 10));
            return query;
        }
        int page = (pipelineActivityQuery.getPage() - 1) * pipelineActivityQuery.getPageSize();
        int pageSize = pipelineActivityQuery.getPage()  * pipelineActivityQuery.getPageSize();
        if (page > list.size()){
            return null;
        }
        if (pageSize > list.size()){
            pageSize = list.size();
        }
        query.setDataList(list.subList(page, pageSize));
        query.setPageNumber(list.size()/pageSize + 1);
        query.setListSize(list.size());
        return query;
    }


    /**
     * 创建日志
     * @param type 日志类型 (创建 create，删除 delete，执行 exec，更新 update)
     * @param templateId 模板id (创建 流水线--pipeline，运行 pipelineExec，凭证--pipelineProof,其他--pipelineOther)
     * @param massage 日志信息
     */
    public  void log(String type, String templateId,String massage){
        OpLog log = new OpLog();
        OpLogTemplate opLogTemplate = new OpLogTemplate();
        opLogTemplate.setId(templateId);
        log.setActionType(type);
        log.setModule("pipeline");
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        String loginId = LoginContext.getLoginId();
        User user = new User();
        user.setId(loginId);
        log.setUser(user);
        log.setBgroup("pipeline");
        log.setOpLogTemplate(opLogTemplate);
        HashMap<String, String> map = new HashMap<>();
        map.put("massage", massage);
        log.setContent(JSONObject.toJSONString(map));
        logService.createLog(log);
    }

















}
