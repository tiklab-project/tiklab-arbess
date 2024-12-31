package io.tiklab.arbess.home.service;

import java.util.Map;

public interface HomeCountService {

    /**
     * 获取流水线统计信息
     * @return 统计信息
     */
    Map<String,Object> findCount();


}