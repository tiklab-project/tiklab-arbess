package io.thoughtware.arbess.task.codescan.model;

import java.util.List;

/**
 * @author zcamy
 */
public class SpotbugsBugClassStats {

    private String className;

    private String classPath;

    private int bugNumber;

    private Boolean isInterface;

    private List<SpotbugsBugInstance> bugInstanceList;

    public List<SpotbugsBugInstance> getBugInstanceList() {
        return bugInstanceList;
    }

    public SpotbugsBugClassStats setBugInstanceList(List<SpotbugsBugInstance> bugInstanceList) {
        this.bugInstanceList = bugInstanceList;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public SpotbugsBugClassStats setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getClassPath() {
        return classPath;
    }

    public SpotbugsBugClassStats setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    public int getBugNumber() {
        return bugNumber;
    }

    public SpotbugsBugClassStats setBugNumber(int bugNumber) {
        this.bugNumber = bugNumber;
        return this;
    }

    public Boolean getInterface() {
        return isInterface;
    }

    public SpotbugsBugClassStats setInterface(Boolean anInterface) {
        isInterface = anInterface;
        return this;
    }
}
