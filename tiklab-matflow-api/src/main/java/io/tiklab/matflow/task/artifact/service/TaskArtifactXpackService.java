package io.tiklab.matflow.task.artifact.service;

import io.tiklab.xpack.repository.model.Repository;

import java.util.List;

public interface TaskArtifactXpackService {

    /**
     * 获取xpack仓库
     * @param authId 认证id
     * @return 仓库
     */
    List<Repository> findAllRepository(String authId);


    /**
     *
     * @param authId 认证id
     * @param rpyName 仓库名称
     * @return 仓库地址
     */
    String findRepository(String authId,String rpyName);



}
