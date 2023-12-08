package io.thoughtware.matflow.task.codescan.model;

/**
 * @author Spotbugs代码扫描
 */
public class SpotbugsBugCategory {


    //  bug描述
    private String category;

    private String description;


    public String getCategory() {
        return category;
    }

    public SpotbugsBugCategory setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SpotbugsBugCategory setDescription(String description) {
        this.description = description;
        return this;
    }
}
