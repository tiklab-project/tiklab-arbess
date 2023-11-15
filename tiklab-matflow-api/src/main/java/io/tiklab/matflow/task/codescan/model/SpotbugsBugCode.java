package io.tiklab.matflow.task.codescan.model;

/**
 * @author zcamy
 */
public class SpotbugsBugCode {

    private String abbrev;


    private String cweid;

    private String description;

    public String getAbbrev() {
        return abbrev;
    }

    public SpotbugsBugCode setAbbrev(String abbrev) {
        this.abbrev = abbrev;
        return this;
    }

    public String getCweid() {
        return cweid;
    }

    public SpotbugsBugCode setCweid(String cweid) {
        this.cweid = cweid;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SpotbugsBugCode setDescription(String description) {
        this.description = description;
        return this;
    }
}
