package io.tiklab.matflow.task.code.service;


import io.tiklab.matflow.task.code.model.XcodeBranch;
import io.tiklab.matflow.task.code.model.XcodeRepository;

import java.util.List;

public interface TaskCodeXcodeService {

    /**
     * 获取xcode所有仓库
     * @param authId 认证id
     * @return 仓库
     */
    List<XcodeRepository> findAllRepository(String authId);


    /**
     * 查询所有仓库分支
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @return 所有分支
     */
    List<XcodeBranch> findAllBranch(String authId, String rpyName);


    /**
     * 获取分支
     * @param authId 认证id
     * @param rpyId 仓库id
     * @param branchId 分支id
     * @return 分支
     */
    XcodeBranch findOneBranch(String authId,String rpyId,String branchId);


    /**
     * 获取仓库git地址
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @return 仓库地址
     */
    XcodeRepository findRepository(String authId,String rpyName);



}
