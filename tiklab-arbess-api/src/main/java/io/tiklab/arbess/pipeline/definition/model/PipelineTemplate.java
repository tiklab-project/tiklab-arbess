package io.tiklab.arbess.pipeline.definition.model;

import java.util.List;

public class PipelineTemplate {

    /**
     * 任务类型
     */
    private List<String> types;


    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
