package net.tiklab.pipeline.definition.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.pipeline.definition.model.PipelineCode;


import java.util.List;


@JoinProvider(model = PipelineCode.class)
public interface PipelineCodeService {

    /**
     * 创建
     * @param pipelineCode code信息
     * @return codeId
     */
     String createCode(PipelineCode pipelineCode);

    /**
     * 删除
     * @param codeId codeId
     */
     void deleteCode(String codeId);

    /**
     * 根据流水线id查询配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    PipelineCode findCode(String pipelineId);

    /**
     * 更新
     * @param pipelineCode 更新信息
     */
     void updateCode(PipelineCode pipelineCode);

    /**
     * 更新
     * @param pipelineCode 更新信息
     * @param pipelineId 流水线id
     */
     void updateCode(PipelineCode pipelineCode,String pipelineId);

    /**
     * 更新源码地址
     * @param pipelineCode 源码信息
     * @return 源码信息
     */
     PipelineCode getUrl(PipelineCode pipelineCode);

    /**
     * 查询单个信息
     * @param codeId codeId
     * @return code信息
     */
    @FindOne
    PipelineCode findOneCode(String codeId);
    /**
     * 查询所有信息
     * @return code信息集合
     */
    @FindAll
    List<PipelineCode> findAllCode();


    @FindList
    List<PipelineCode> findAllCodeList(List<String> idList);

}
