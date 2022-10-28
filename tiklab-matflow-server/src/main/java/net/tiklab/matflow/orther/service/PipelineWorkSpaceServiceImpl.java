package net.tiklab.matflow.orther.service;


import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.service.PipelineService;
import net.tiklab.matflow.execute.model.FileTree;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import java.util.*;

@Service
@Exporter
public class PipelineWorkSpaceServiceImpl implements PipelineWorkSpaceService {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineOpenService pipelineOpenService;

    @Autowired
    PipelineFileService pipelineFileService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineWorkSpaceServiceImpl.class);

    //获取文件树
    @Override
    public List<FileTree> fileTree(String pipelineId, String userId){
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (pipeline == null)return null;
        pipelineOpenService.findOpen(userId, pipeline);
        //设置拉取地址
        String path = PipelineUntil.findFileAddress()+ pipeline.getPipelineName();
        List<FileTree> trees = new ArrayList<>();
        File file = new File(path);
        //判断文件是否存在
        if (file.exists()){
            List<FileTree> list = pipelineFileService.fileTree(file, trees);
            list.sort(Comparator.comparing(FileTree::getTreeType,Comparator.reverseOrder()));
            return list;
        }
        return null;
    }

    //读取文件信息
    @Override
    public  List<String> readFile(String path){
        return PipelineUntil.readFile(path);
    }









}
