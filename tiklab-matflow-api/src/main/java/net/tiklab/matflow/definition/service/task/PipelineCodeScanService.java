package net.tiklab.matflow.definition.service.task;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.task.PipelineCodeScan;
import java.util.List;

@JoinProvider(model = PipelineCodeScan.class)
public interface PipelineCodeScanService {

    /**
     * 创建流水线代码扫描
     * @param pipelineCodeScan 流水线代码扫描
     * @return 流水线代码扫描id
     */
    String createCodeScan(PipelineCodeScan pipelineCodeScan);

    /**
     * 删除流水线代码扫描
     * @param codeScanId 流水线代码扫描id
     */
    void deleteCodeScan(String codeScanId);

    /**
     * 更新代码扫描信息
     * @param pipelineCodeScan 信息
     */
    void updateCodeScan(PipelineCodeScan pipelineCodeScan);

    /**
     * 查询代码扫描信息
     * @param codeScanId id
     * @return 信息
     */
    @FindOne
    PipelineCodeScan findOneCodeScan(String codeScanId);

    /**
     * 查询所有流水线代码扫描
     * @return 流水线代码扫描列表
     */
    @FindAll
    List<PipelineCodeScan> findAllCodeScan();


    @FindList
    List<PipelineCodeScan> findAllCodeScanList(List<String> idList);
    
}
