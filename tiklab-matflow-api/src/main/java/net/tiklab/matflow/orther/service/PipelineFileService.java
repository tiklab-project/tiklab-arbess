package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.execute.model.FileTree;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 该类存放对文件的操作
 */
public interface PipelineFileService {


    /**
     * 获取文件树
     * @param path 文件地址
     * @param list 存放树的容器
     * @return 树
     */
    List<FileTree> fileTree(File path, List<FileTree> list);

}
