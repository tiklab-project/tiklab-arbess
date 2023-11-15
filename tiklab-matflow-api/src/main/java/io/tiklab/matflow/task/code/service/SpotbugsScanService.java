package io.tiklab.matflow.task.code.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.task.codescan.model.SpotbugsBugQuery;
import io.tiklab.matflow.task.codescan.model.SpotbugsBugSummary;

import java.util.List;

/**
 * @author zcamy
 */
public interface SpotbugsScanService {


    String creatSpotbugs(SpotbugsBugSummary bugSummary);


    void updateSpotbugs(SpotbugsBugSummary bugSummary);


    void deleteSpotbugs(String bugId);

    SpotbugsBugSummary findOneSpotbugs(String bugId);


    List<SpotbugsBugSummary> findAllSpotbugs();


    List<SpotbugsBugSummary> findSpotbugsList(SpotbugsBugQuery bugQuery);


    Pagination<SpotbugsBugSummary> findSpotbugsPage(SpotbugsBugQuery bugQuery);










}
