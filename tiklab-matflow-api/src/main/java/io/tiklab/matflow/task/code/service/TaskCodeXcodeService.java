package io.tiklab.matflow.task.code.service;


import io.tiklab.xcode.branch.model.Branch;
import io.tiklab.xcode.repository.model.Repository;

import java.util.List;

public interface TaskCodeXcodeService {

    /**
     * 获取xcode所有仓库
     * @param authId 认证id
     * @return 仓库
     */
    List<Repository> findAllRepository(String authId);


    /**
     * 查询所有仓库分支
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @return 所有分支
     */
    List<Branch> findAllBranch(String authId, String rpyName);


    /**
     * 获取仓库git地址
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @return 仓库地址
     */
    String findRepository(String authId,String rpyName);



}
