package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.orther.model.PipelineFollow;

import java.util.List;

public interface PipelineFollowService {

    /**
     * 创建关注
     * @param pipelineFollow 关注
     */
    void updateFollow(PipelineFollow pipelineFollow);

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
    List<Pipeline> findAllFollow(String userId, StringBuilder s);


    /**
     * 查询单个信息
     * @param followId 关注id
     * @return 关注信息
     */

    PipelineFollow findOneFollow(String followId);

    /**
     * 查询所有关注
     * @return 关注集合
     */

    List<PipelineFollow> findAllFollow();

    List<PipelineFollow> findAllFollowList(List<String> idList);
    
}
