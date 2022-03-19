package com.doublekit.pipeline.instance.service;

import java.io.IOException;
import java.util.List;

public interface GiteeService {

    String getCode() throws IOException;

    String getAccessToken(String code) throws IOException;

    List<String> getAllStorehouse();

    List<String> getBranch(String projectName);

}
