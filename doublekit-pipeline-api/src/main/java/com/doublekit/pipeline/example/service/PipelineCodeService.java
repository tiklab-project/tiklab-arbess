package com.doublekit.pipeline.example.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.setting.proof.model.Proof;

import java.util.List;
import java.util.Map;


@JoinProvider(model = PipelineCode.class)
public interface PipelineCodeService {

    /**
     * 创建
     * @param pipelineCode code信息
     * @return codeId
     */
     String createCode(PipelineCode pipelineCode);

    Map<String, String> createTest(PipelineConfigure pipelineConfigure);

    /**
     * 删除
     * @param codeId codeId
     */
     void deleteCode(String codeId);

    void deleteTest(PipelineConfigure pipelineConfigure);

    /**
     * 更新
     * @param pipelineCode 更新信息
     */
     void updateCode(PipelineCode pipelineCode);

    void updateTest(PipelineConfigure pipelineConfigure);

    /**
     * 查询单个信息
     * @param codeId codeId
     * @return code信息
     */
    @FindOne
    PipelineCode findOneCode(String codeId);

    /**
     * 查询凭证
     * @param pipelineConfigure 配置信息
     * @return 凭证
     */
    Proof findOneProof(PipelineConfigure pipelineConfigure);

    /**
     * 查询所有信息
     * @return code信息集合
     */
    @FindAll
     List<PipelineCode> findAllCode();



    @FindList
    List<PipelineCode> findAllCodeList(List<String> idList);
    
}
