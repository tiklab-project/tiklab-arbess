package net.tiklab.matflow.orther.service;

import net.tiklab.core.page.Pagination;
import net.tiklab.matflow.orther.model.PipelineActivity;
import net.tiklab.matflow.orther.model.PipelineTask;
import net.tiklab.oplog.log.modal.OpLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PipelineActivityService {

    /**
     * 创建日志
     * @param type 日志类型 (创建 create，删除 delete，执行 exec，更新 update)
     * @param templateId 模板id (创建 流水线--pipeline，运行 pipelineExec，凭证--pipelineProof,其他--pipelineOther)
     * @param map 日志信息
     */
    void log(String type, String templateId, Map<String, String> map);


    /**
     * 查询日志
     * @return 日志
     */
    List<PipelineActivity> findLog();


    /**
     * 查询待办
     */
    List<PipelineTask> findTask();


}
