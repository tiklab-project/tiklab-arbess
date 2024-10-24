package io.tiklab.arbess.task.artifact.service;

import io.tiklab.arbess.task.artifact.model.XpackRepository;

import java.util.List;

public interface TaskArtifactXpackService {

    /**
     * 获取xpack仓库
     * @param authId 认证id
     * @return 仓库
     */
    List<XpackRepository> findAllRepository(String authId);


    /**
     *
     * @param authId 认证id
     * @param rpyId 仓库id
     * @return 仓库地址
     */
    XpackRepository findRepository(String authId,String rpyId);



}
