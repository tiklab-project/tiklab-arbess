package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.matflow.support.postprocess.model.PostprocessInstance;
import org.springframework.stereotype.Service;

@Service
public class PostprocessInstanceServiceImpl implements PostprocessInstanceService{

    @Override
    public String createPostInstance(PostprocessInstance instance) {
        return null;
    }

    @Override
    public void deletePostInstance(String postInstanceId) {

    }

    @Override
    public void deleteAllPostInstance(String instanceId) {

    }
}
