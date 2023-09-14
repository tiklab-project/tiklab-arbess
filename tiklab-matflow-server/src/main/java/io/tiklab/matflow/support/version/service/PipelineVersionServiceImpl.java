package io.tiklab.matflow.support.version.service;

import io.tiklab.licence.licence.model.Version;
import io.tiklab.licence.licence.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineVersionServiceImpl implements PipelineVersionService {

    @Autowired
    VersionService versionServer;


    @Override
    public Integer version() {
        Version version = versionServer.getVersion();

        Boolean expired = version.getExpired();
        Integer release = version.getRelease();
        if (release == 2 && !expired){
            return 2;
        }
        return 1;
    }



}
