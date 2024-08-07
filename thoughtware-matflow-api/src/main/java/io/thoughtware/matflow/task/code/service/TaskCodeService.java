package io.thoughtware.matflow.task.code.service;


import io.thoughtware.matflow.task.code.model.TaskCode;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;


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
     * 根据配置id查询任务
     * @param taskId 配置id
     * @return 任务
     */
    TaskCode findCodeByAuth(String taskId);


    /**
     * 验证
     * @param taskType 任务类型
     * @param object object
     * @return 是否验证通过
     */
    Boolean codeValid(String taskType,Object object);

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
