package io.tiklab.arbess.task.codescan.model;

import java.util.List;

/**
 * @author 文件代码bug信息
 */
public class SpotbugsBugFileStats {

    private String path;

    private String bugNumber;

    private List<SpotbugsBugInstance> bugInstanceList;

    public List<SpotbugsBugInstance> getBugInstanceList() {
        return bugInstanceList;
    }

    public SpotbugsBugFileStats setBugInstanceList(List<SpotbugsBugInstance> bugInstanceList) {
        this.bugInstanceList = bugInstanceList;
        return this;
    }

    public String getPath() {
        return path;
    }

    public SpotbugsBugFileStats setPath(String path) {
        this.path = path;
        return this;
    }

    public String getBugNumber() {
        return bugNumber;
    }

    public SpotbugsBugFileStats setBugNumber(String bugNumber) {
        this.bugNumber = bugNumber;
        return this;
    }
}
