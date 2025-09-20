package io.tiklab.arbess.setting.tool.service;

import io.tiklab.arbess.setting.tool.model.ScmRemoteFile;

public interface ScmFileService {

    /**
     * 获取文件在服务器上的bin路径
     * @param filePath 文件路径
     * @return 文件在服务器上的bin路径
     */
    String fileFileBinPath(String filePath);

    /**
     * 从指定 URL 下载文件到目标路径
     */
    String downloadFile(ScmRemoteFile remoteFile);

    /**
     * 从指定 URL 下载文件到目标路径
     */
    void downloadAndInstall(ScmRemoteFile remoteFile);


    ScmRemoteFile findScmRemoteFile(String id);

}
