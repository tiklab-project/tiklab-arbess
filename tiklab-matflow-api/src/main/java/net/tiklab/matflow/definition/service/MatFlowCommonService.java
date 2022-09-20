package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.execute.model.FileTree;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MatFlowCommonService {

    /**
     * 获取不同系统应用文件保存地址
     * @return 应用地址
     */
     String getFileAddress();

    /**
     * 删除文件
     * @param file 文件地址
     */
    Boolean deleteFile(File file);

    /**
     * 获取文件流
     * @param path 文件地址
     * @return 文件信息
     */
     List<String> readFile(String path);


    /**
     * 字符串写入文件中
     * @param massage 支付串
     * @param filePath 文件地址
     * @throws IOException 写入失败
     */
     void writePrivateKeyPath(String massage, String filePath) throws IOException;


    /**
     * 获取文件树
     * @param path 文件地址
     * @param list 存放树的容器
     * @return 树
     */
    List<FileTree> fileTree(File path, List<FileTree> list);

    /**
     * 获取符合条件的文件名
     * @param path 文件地址
     * @param list 存放文件地址
     * @return 文件地址集合
     */
    List<String> getFilePath(File path,List<String> list);


    /**
     * 匹配字符串获取文件全路径
     * @param matFlowName 文件地址
     * @param regex 匹配条件
     * @return 文件地址
     */
    String getFile(String matFlowName, String regex);
}
