package io.tiklab.arbess.task.code.service;

import io.tiklab.arbess.task.codescan.model.SpotbugsBugQuery;
import io.tiklab.arbess.task.codescan.model.SpotbugsBugSummary;
import io.tiklab.core.page.Pagination;

import java.util.List;


/**
 * Spotbugs扫描服务接口
 */
public interface SpotbugsScanService {

    /**
     * 创建Spotbugs
     * @param bugSummary SpotbugsBugSummary
     * @return SpotbugsBugSummary
     */
    String creatSpotbugs(SpotbugsBugSummary bugSummary);

    /**
     * 更新Spotbugs
     * @param bugSummary SpotbugsBugSummary
     */
    void updateSpotbugs(SpotbugsBugSummary bugSummary);

    /**
     * 删除Spotbugs
     * @param bugId spotbugsId
     */
    void deleteSpotbugs(String bugId);

    
    SpotbugsBugSummary findOneSpotbugs(String bugId);


    List<SpotbugsBugSummary> findAllSpotbugs();


    List<SpotbugsBugSummary> findSpotbugsList(SpotbugsBugQuery bugQuery);


    Pagination<SpotbugsBugSummary> findSpotbugsPage(SpotbugsBugQuery bugQuery);










}
