package io.tiklab.matflow.task.codescan.service;

import io.tiklab.matflow.task.code.model.SpotbugsBugQuery;
import io.tiklab.matflow.task.code.model.SpotbugsBugSummary;
import io.tiklab.matflow.task.code.service.SpotbugsScanService;
import io.tiklab.matflow.task.codescan.dao.SpotbugsScanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spotbugs代码扫描实现
 */
@Service
public class SpotbugsScanServiceImpl implements SpotbugsScanService {

    @Autowired
    SpotbugsScanDao spotbugsScanDao;


    @Override
    public String creatSpotbugs(SpotbugsBugSummary bugSummary) {
        return spotbugsScanDao.creatSpotbugs(bugSummary);
    }

    @Override
    public void updateSpotbugs(SpotbugsBugSummary bugSummary) {
        spotbugsScanDao.updateSpotbugs(bugSummary);
    }

    @Override
    public void deleteSpotbugs(String bugId) {
        spotbugsScanDao.deleteSpotbugs(bugId);
    }

    @Override
    public SpotbugsBugSummary findOneSpotbugs(String bugId) {
        return spotbugsScanDao.findOneSpotbugs(bugId);
    }

    @Override
    public List<SpotbugsBugSummary> findAllSpotbugs() {
        return spotbugsScanDao.findAllSpotbugs();
    }

    @Override
    public List<SpotbugsBugSummary> findSpotbugsList(SpotbugsBugQuery bugQuery) {
        return spotbugsScanDao.findSpotbugsList(bugQuery);
    }
}






















