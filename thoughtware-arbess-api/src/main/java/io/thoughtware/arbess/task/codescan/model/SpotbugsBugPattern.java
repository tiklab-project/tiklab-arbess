package io.thoughtware.arbess.task.codescan.model;

/**
 * @author zcamy
 */
public class SpotbugsBugPattern {

    private String type;

    private String abbrev;

    private String category;


    private String shortDescription;

    private String details;

    public String getType() {
        return type;
    }

    public SpotbugsBugPattern setType(String type) {
        this.type = type;
        return this;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public SpotbugsBugPattern setAbbrev(String abbrev) {
        this.abbrev = abbrev;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public SpotbugsBugPattern setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public SpotbugsBugPattern setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public SpotbugsBugPattern setDetails(String details) {
        this.details = details;
        return this;
    }
}
