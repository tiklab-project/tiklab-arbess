package com.tiklab.matflow.instance.service;

import com.tiklab.matflow.execute.model.CodeGit.FileTree;
import com.tiklab.matflow.execute.model.CodeGit.GitCommit;

import java.util.List;

public interface MatFlowWorkSpaceService {

    /**
     * 获取文件树
     * @param matFlowId 流水线id
     * @return 文件树
     */
    List<FileTree> fileTree(String userId ,String matFlowId);

    /**
     * 读取文件内容
     * @param path 文件地址
     * @return 文件内容
     */
    List<String> readFile(String path);

    /**
     * 获取提交信息
     * @param matFlowId 流水线id
     * @return 提交信息
     */
    List<List<GitCommit>> getSubmitMassage(String matFlowId);


}
