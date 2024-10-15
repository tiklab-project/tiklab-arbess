package io.tiklab.arbess.task.code.service;


import io.tiklab.arbess.task.code.model.*;
import io.tiklab.arbess.task.code.model.ThirdBranch;
import io.tiklab.arbess.task.code.model.ThirdHouse;
import io.tiklab.arbess.task.code.model.ThirdQuery;

import java.util.List;

public interface TaskCodeGittokService {

    /**
     * 获取凭证认证的仓库列表
     * @param thirdQuery 凭证
     * @return 仓库列表
     */
    List<ThirdHouse> findStoreHouseList(ThirdQuery thirdQuery);

    /**
     * 获取凭证认证的仓库分支列表
     * @param thirdQuery 凭证
     * @return 分支列表
     */
    List<ThirdBranch> findHouseBranchList(ThirdQuery thirdQuery);


    /**
     * 获取分支
     * @param authId 认证id
     * @param rpyId 仓库id
     * @param branchId 分支id
     * @return 分支
     */
    ThirdBranch findOneBranch(String authId,String rpyId,String branchId);

    /**
     * 获取凭证认证的指定的仓库
     * @param thirdQuery 凭证
     * @return 指定的仓库
     */
    ThirdHouse findStoreHouse(ThirdQuery thirdQuery);



}
