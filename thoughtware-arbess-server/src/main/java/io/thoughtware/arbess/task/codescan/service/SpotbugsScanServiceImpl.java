package io.thoughtware.arbess.task.codescan.service;

import io.thoughtware.arbess.task.codescan.model.SpotbugsBugSummary;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.arbess.task.codescan.model.SpotbugsBugQuery;
import io.thoughtware.arbess.task.code.service.SpotbugsScanService;
import io.thoughtware.arbess.task.codescan.dao.SpotbugsScanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

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
        SpotbugsBugSummary spotbugs = findOneSpotbugs(bugId);
        if (Objects.isNull(spotbugs)){
            return;
        }
        String xmlPath = spotbugs.getXmlPath();

        File file = new File(xmlPath);
        if (file.exists()){
            file.delete();
        }
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

    @Override
    public Pagination<SpotbugsBugSummary> findSpotbugsPage(SpotbugsBugQuery bugQuery) {
        return spotbugsScanDao.findSpotbugsPage(bugQuery);
    }
}






















