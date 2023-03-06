package net.tiklab.matflow.task.code.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.code.model.TaskCode;


import java.util.List;

/**
 * 源码服务接口
 */
@JoinProvider(model = TaskCode.class)
public interface TaskCodeService {

    /**
     * 创建
     * @param taskCode code信息
     * @return codeId
     */
     String createCode(TaskCode taskCode);


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
    TaskCode findOneCodeConfig(String configId);


    /**
     * 删除
     * @param codeId codeId
     */
     void deleteCode(String codeId);

    /**
     * 更新
     * @param taskCode 更新信息
     */
     void updateCode(TaskCode taskCode);

    /**
     * 查询单个信息
     * @param codeId codeId
     * @return code信息
     */
    @FindOne
    TaskCode findOneCode(String codeId);
    /**
     * 查询所有信息
     * @return code信息集合
     */
    @FindAll
    List<TaskCode> findAllCode();


    @FindList
    List<TaskCode> findAllCodeList(List<String> idList);

}
