package net.tiklab.matflow.execute.model;



import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;

import java.util.List;

@ApiModel
@Join
public  class FileTree {


    //文件名
    private String treeName;

    //文件类型
    private  Integer treeType;

    //文件路径
    private String treePath;

    //文件目录
    private List<FileTree> fileTree;


    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public Integer getTreeType() {
        return treeType;
    }

    public void setTreeType(Integer treeType) {
        this.treeType = treeType;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public List<FileTree> getFileTree() {
        return fileTree;
    }

    public void setFileTree(List<FileTree> fileTree) {
        this.fileTree = fileTree;
    }


}
