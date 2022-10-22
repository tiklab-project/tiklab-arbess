package net.tiklab.matflow.orther.service;

import net.tiklab.matflow.execute.model.FileTree;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 文件操作
 */

@Service
public class PipelineFileServiceImpl implements PipelineFileService {

    /**
     * 获取文件树
     * @param path 文件地址
     * @param list 存放树的容器
     * @return 树
     */
    @Override
    public  List<FileTree> fileTree(File path, List<FileTree> list){
        File[] files = path.listFiles();
        if (files != null){
            for (File file : files) {
                FileTree fileTree = new FileTree();
                fileTree.setTreeName(file.getName());
                if (file.isDirectory()){
                    fileTree.setTreeType(2);
                    List<FileTree> trees = new ArrayList<>();
                    fileTree.setFileTree(trees);
                    fileTree(file,trees);
                }else {
                    fileTree.setTreePath(file.getPath());
                    fileTree.setTreeType(1);
                }
                list.add(fileTree);
                list.sort(Comparator.comparing(FileTree::getTreeType,Comparator.reverseOrder()));
            }
        }
        return list;
    }

}
