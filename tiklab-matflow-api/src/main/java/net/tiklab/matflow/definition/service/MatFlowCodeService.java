package net.tiklab.matflow.definition.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.MatFlowCode;

import java.util.List;


@JoinProvider(model = MatFlowCode.class)
public interface MatFlowCodeService {

    /**
     * 创建
     * @param matFlowCode code信息
     * @return codeId
     */
     String createCode(MatFlowCode matFlowCode);

    /**
     * 删除
     * @param codeId codeId
     */
     void deleteCode(String codeId);

    /**
     * 更新
     * @param matFlowCode 更新信息
     */
     void updateCode(MatFlowCode matFlowCode);

    /**
     * 更新源码地址
     * @param matFlowCode 源码信息
     * @return 源码信息
     */
     MatFlowCode getUrl(MatFlowCode matFlowCode);

    /**
     * 查询单个信息
     * @param codeId codeId
     * @return code信息
     */
    @FindOne
    MatFlowCode findOneCode(String codeId);
    /**
     * 查询所有信息
     * @return code信息集合
     */
    @FindAll
    List<MatFlowCode> findAllCode();


    @FindList
    List<MatFlowCode> findAllCodeList(List<String> idList);

}
