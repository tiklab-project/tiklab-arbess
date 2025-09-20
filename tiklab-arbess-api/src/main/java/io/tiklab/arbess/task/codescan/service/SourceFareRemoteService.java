package io.tiklab.arbess.task.codescan.service;

import io.tiklab.arbess.task.codescan.model.SourceFareProject;
import io.tiklab.arbess.task.codescan.model.SourceFareProjectQuery;

import java.util.List;

public interface SourceFareRemoteService {

    /**
     * 查询项目列表
     * @param sourceFareProjectQuery 查询参数
     * @return 项目列表
     */
    List<SourceFareProject> findSourceFareProjectList(SourceFareProjectQuery sourceFareProjectQuery);

}
