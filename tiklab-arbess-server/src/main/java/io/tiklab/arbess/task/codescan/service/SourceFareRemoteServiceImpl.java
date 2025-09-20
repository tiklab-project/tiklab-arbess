package io.tiklab.arbess.task.codescan.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.setting.third.model.AuthThird;
import io.tiklab.arbess.setting.third.service.AuthThirdService;
import io.tiklab.arbess.support.util.util.PipelineRpc;
import io.tiklab.arbess.task.codescan.model.SourceFareProject;
import io.tiklab.arbess.task.codescan.model.SourceFareProjectQuery;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.sourcefare.project.model.Project;
import io.tiklab.sourcefare.project.model.ProjectQuery;
import io.tiklab.sourcefare.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SourceFareRemoteServiceImpl implements SourceFareRemoteService {

    @Autowired
    AuthThirdService authThirdService;

    private String findServerAddress(String authId){
        AuthThird authServer = authThirdService.findOneAuthServer(authId);

        if (Objects.isNull(authServer)){
            return null;
        }
        return authServer.getServerAddress();
    }

    public static <S, T> T copyFields(S source, T target) {
        String jsonString = JSONObject.toJSONString(source);
        return (T) JSONObject.parseObject(jsonString, target.getClass());
    }

    @Override
    public List<SourceFareProject> findSourceFareProjectList(SourceFareProjectQuery sourceFareProjectQuery) {
        PipelineRpc pipelineRpc = PipelineRpc.instance();
        String authId = sourceFareProjectQuery.getAuthId();
        ProjectService serviceRpc = pipelineRpc.findServiceRpc(ProjectService.class, findServerAddress(authId));
        ProjectQuery projectQuery = copyFields(sourceFareProjectQuery, new ProjectQuery());
        List<Project> dataList;
        try {
            dataList = serviceRpc.findProjectList(projectQuery);
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException("SourceFare系统异常，无法连接！");
        }

        List<SourceFareProject> sourceFareProjectList = new ArrayList<>();
        for (Project project : dataList) {
            SourceFareProject sourceFareProject = copyFields(project, new SourceFareProject());
            sourceFareProjectList.add(sourceFareProject);
        }

        return sourceFareProjectList;
    }





}
