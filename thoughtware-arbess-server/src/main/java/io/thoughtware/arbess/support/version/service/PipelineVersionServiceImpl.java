package io.thoughtware.arbess.support.version.service;

import io.thoughtware.licence.licence.model.Version;
import io.thoughtware.licence.licence.service.VersionService;
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
