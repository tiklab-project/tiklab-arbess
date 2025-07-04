package io.tiklab.arbess.task.codescan.service;

import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.arbess.task.codescan.model.TaskCodeScan;

import java.util.List;
/**
 * 代码扫描服务接口
 */
@JoinProvider(model = TaskCodeScan.class)
public interface TaskCodeScanService {

    /**
     * 创建流水线代码扫描
     * @param taskCodeScan 流水线代码扫描
     * @return 流水线代码扫描id
     */
    String createCodeScan(TaskCodeScan taskCodeScan);

    /**
     * 删除流水线代码扫描
     * @param codeScanId 流水线代码扫描id
     */
    void deleteCodeScan(String codeScanId);

    /**
     * 更新代码扫描信息
     * @param taskCodeScan 信息
     */
    void updateCodeScan(TaskCodeScan taskCodeScan);


    Boolean codeScanValid(String taskType,TaskCodeScan taskCodeScan);


    /**
     * 根据配置id查询任务
     * @param taskId 配置id
     * @return 任务
     */
    TaskCodeScan findCodeScanByAuth(String taskId);

    /**
     * 查询代码扫描信息
     * @param codeScanId id
     * @return 信息
     */
    @FindOne
    TaskCodeScan findOneCodeScan(String codeScanId);


    TaskCodeScan findOneCodeScanNoQuery(String codeScanId);

    /**
     * 查询所有流水线代码扫描
     * @return 流水线代码扫描列表
     */
    @FindAll
    List<TaskCodeScan> findAllCodeScan();


    @FindList
    List<TaskCodeScan> findAllCodeScanList(List<String> idList);
    
}
