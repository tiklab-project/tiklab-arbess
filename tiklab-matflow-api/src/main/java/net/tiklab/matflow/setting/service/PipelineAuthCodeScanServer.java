package net.tiklab.matflow.setting.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.setting.model.PipelineAuthCodeScan;

import java.util.List;
@JoinProvider(model = PipelineAuthCodeScan.class)
public interface PipelineAuthCodeScanServer {

    /**
     * 创建流水线服务授权
     * @param pipelineAuthCodeScan 流水线服务授权
     * @return 流水线服务授权id
     */
    String createAuthCodeScan(PipelineAuthCodeScan pipelineAuthCodeScan);

    /**
     * 删除流水线服务授权
     * @param authCodeScanId 流水线服务授权id
     */
    void deleteAuthCodeScan(String authCodeScanId);

    /**
     * 更新服务授权信息
     * @param pipelineAuthCodeScan 信息
     */
    void updateAuthCodeScan(PipelineAuthCodeScan pipelineAuthCodeScan);

    /**
     * 查询服务授权信息
     * @param authCodeScanId id
     * @return 信息
     */
    @FindOne
    PipelineAuthCodeScan findOneAuthCodeScan(String authCodeScanId);

    /**
     * 查询所有流水线服务授权
     * @return 流水线服务授权列表
     */
    @FindAll
    List<PipelineAuthCodeScan> findAllAuthCodeScan();


    @FindList
    List<PipelineAuthCodeScan> findAllAuthCodeScanList(List<String> idList);
    
}
