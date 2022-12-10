package net.tiklab.matflow.task.server;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.model.PipelineCode;


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
     * 根据配置id删除任务
     * @param configId 配置id
     */
    void deleteCodeConfig(String configId);

    /**
     * 根据配置id查询任务
     * @param configId 配置id
     * @return 任务
     */
    PipelineCode findOneCodeConfig(String configId);


    /**
     * 删除
     * @param codeId codeId
     */
     void deleteCode(String codeId);

    /**
     * 更新
     * @param pipelineCode 更新信息
     */
     void updateCode(PipelineCode pipelineCode);

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
