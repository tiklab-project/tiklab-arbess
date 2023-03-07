package io.tiklab.matflow.task.codescan.service;

import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.task.codescan.model.TaskCodeScan;

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


    /**
     * 根据配置id删除任务
     * @param configId 配置id
     */
    void deleteCodeScanConfig(String configId);

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    TaskCodeScan findOneCodeScanConfig(String configId);

    /**
     * 查询代码扫描信息
     * @param codeScanId id
     * @return 信息
     */
    @FindOne
    TaskCodeScan findOneCodeScan(String codeScanId);

    /**
     * 查询所有流水线代码扫描
     * @return 流水线代码扫描列表
     */
    @FindAll
    List<TaskCodeScan> findAllCodeScan();


    @FindList
    List<TaskCodeScan> findAllCodeScanList(List<String> idList);
    
}
