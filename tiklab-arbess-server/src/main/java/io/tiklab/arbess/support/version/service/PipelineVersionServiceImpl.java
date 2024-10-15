package io.tiklab.arbess.support.version.service;

import io.tiklab.licence.licence.model.Version;
import io.tiklab.licence.licence.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineVersionServiceImpl implements PipelineVersionService {

    @Autowired
    VersionService versionServer;


    @Override
    public Boolean isVip() {
        Version version = versionServer.getVersion();

        Boolean expired = version.getExpired();
        // Integer release = version.getRelease();
        return !expired;
    }



}
