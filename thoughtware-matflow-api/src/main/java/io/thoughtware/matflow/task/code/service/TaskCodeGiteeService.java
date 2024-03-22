package io.thoughtware.matflow.task.code.service;

import io.thoughtware.matflow.task.code.model.ThirdBranch;
import io.thoughtware.matflow.task.code.model.ThirdHouse;
import io.thoughtware.matflow.task.code.model.ThirdQuery;
import io.thoughtware.matflow.task.code.model.ThirdUser;

import java.util.List;

public interface TaskCodeGiteeService {

    /**
     * 获取AccessToken的授权用户信息
     * @param thirdQuery 凭证
     * @return 授权用户信息
     */
    ThirdUser findAuthUser(ThirdQuery thirdQuery);

    /**
     * 获取凭证认证的仓库列表
     * @param thirdQuery 凭证
     * @return 仓库列表
     */
    List<ThirdHouse> findStoreHouseList(ThirdQuery thirdQuery);


    /**
     * 获取指定仓库信息
     * @param thirdQuery 仓库
     * @return 仓库信息
     */
    ThirdHouse findStoreHouse(ThirdQuery thirdQuery);

    /**
     * 获取凭证认证的仓库分支列表
     * @param thirdQuery 凭证
     * @return 分支列表
     */
    List<ThirdBranch> findStoreBranchList(ThirdQuery thirdQuery);


}
