package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.orther.model.MatFlowFollow;

import java.util.List;

public interface MatFlowFollowService {

    /**
     * 创建关注
     * @param matFlowFollow 关注
     * @return 关注id
     */
    String updateFollow(MatFlowFollow matFlowFollow);

    /**
     * 删除关注
     * @param followId 关注id
     */
    void deleteFollow(String followId);
   
    /**
     * 用户关注的流水线
     * @param userId 用户id
     * @return 经常关注的流水线
     */
    List<MatFlow> findAllFollow(String userId, StringBuilder s);


    /**
     * 查询所有流水线
     * @param userId 用户id
     * @return 流水线信息
     */
    List<MatFlow> findUserMatFlow(String userId, StringBuilder s);

    /**
     * 查询单个信息
     * @param followId 关注id
     * @return 关注信息
     */

    MatFlowFollow findOneFollow(String followId);

    /**
     * 查询所有关注
     * @return 关注集合
     */

    List<MatFlowFollow> findAllFollow();

    List<MatFlowFollow> findAllFollowList(List<String> idList);
    
}
