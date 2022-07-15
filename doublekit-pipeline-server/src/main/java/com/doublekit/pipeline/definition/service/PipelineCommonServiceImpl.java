package com.doublekit.pipeline.definition.service;

import com.doublekit.pipeline.execute.model.CodeGit.FileTree;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PipelineCommonServiceImpl implements PipelineCommonService{

    /**
     * 获取不同系统应用文件保存地址
     * @return 应用地址
     */
    @Override
    public String getFileAddress(){
        String files = "/usr/local/pipeline/";

        String property = System.getProperty("os.name");
        String[] s = property.split(" ");
        if (s[0].equals("Windows")){
            files = "D:\\clone\\";
        }
        return files;
    }

    /**
     * 删除文件
     * @param file 文件地址
     */
    @Override
    public  Boolean deleteFile(File file){
        if (file.isDirectory()) {
            String[] children = file.list();
            //递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean state = deleteFile(new File(file, child));
                    int tryCount = 0;
                    while (!state && tryCount++ < 30) {
                        //回收资源
                        System.gc();
                        state = file.delete();
                    }
                }
            }
            // 目录此时为空，删除
        }
        return file.delete();
    }


    /**
     * 字符串写入文件中
     * @param massage 支付串
     * @param filePath 文件地址
     */
    @Override
    public void writePrivateKeyPath(String massage, String filePath) throws IOException {
        BufferedReader bufferedReader ;
        BufferedWriter bufferedWriter;
        File distFile= new File(filePath);
        if (!distFile.getParentFile().exists()){
            boolean mkdirs = distFile.getParentFile().mkdirs();
        }
        bufferedReader = new BufferedReader(new StringReader(massage));
        bufferedWriter = new BufferedWriter(new FileWriter(distFile));
        char[] buf = new char[1024]; //字符缓冲区
        int len;
        while ( (len = bufferedReader.read(buf)) != -1) {
            bufferedWriter.write(buf, 0, len);
        }
        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }

    /**
     * 获取文件流
     * @param path 文件地址
     * @return 文件信息
     */
    @Override
    public List<String> readFile(String path) {
        if (path == null){
            return null;
        }
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return  null;
        }
        return lines;
    }

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

    /**
     * 获取符合条件的文件名
     * @param path 文件地址
     * @param list 存放文件地址
     * @return 文件地址集合
     */
    @Override
    public  List<String> getFilePath(File path,List<String> list){
        File[] fa = path.listFiles();
        if (fa != null) {
            for (File file : fa) {
                if (file.isDirectory()){
                    getFilePath(file,list);
                }
                list.add(file.getPath());
            }
        }
        return list;
    }

    /**
     * 匹配字符串获取文件全路径
     * @param pipelineName 文件地址
     * @param regex 匹配条件
     * @return 文件地址
     */
    @Override
    public String getFile(String pipelineName, String regex){
        List<String> list = new ArrayList<>();
        String  path= getFileAddress() + pipelineName;
        List<String> filePath = getFilePath(new File(path),new ArrayList<>());
        for (String s : filePath) {
            File file = new File(s);
            if (file.getName().matches("^(.*"+regex+".*)") || file.getName().matches(regex)){
                list.add(s);
            }
        }
        if (list.size()==1){
            return list.get(0);
        }
        return null;
    }

}
