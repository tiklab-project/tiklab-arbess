package io.tiklab.arbess.task.codescan.model;

import java.util.List;

/**
 * @author zcamy
 */
public class SpotbugsBugPackageStats {

    private String packagePath;

    private int totalBugs;


    private int totalTypes;

    private List<SpotbugsBugClassStats> classStatsList;


    public String getPackagePath() {
        return packagePath;
    }

    public SpotbugsBugPackageStats setPackagePath(String packagePath) {
        this.packagePath = packagePath;
        return this;
    }

    public int getTotalBugs() {
        return totalBugs;
    }

    public SpotbugsBugPackageStats setTotalBugs(int totalBugs) {
        this.totalBugs = totalBugs;
        return this;
    }

    public int getTotalTypes() {
        return totalTypes;
    }

    public SpotbugsBugPackageStats setTotalTypes(int totalTypes) {
        this.totalTypes = totalTypes;
        return this;
    }

    public List<SpotbugsBugClassStats> getClassStatsList() {
        return classStatsList;
    }

    public SpotbugsBugPackageStats setClassStatsList(List<SpotbugsBugClassStats> classStatsList) {
        this.classStatsList = classStatsList;
        return this;
    }
}
