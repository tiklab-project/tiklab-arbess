package io.thoughtware.arbess.task.codescan.model;

/**
 * @author zcamy
 */
public class SpotbugsBugClass {


    private String classname;

    private int startLine;

    private int endLine;

    private String message;


    public String getClassname() {
        return classname;
    }

    public SpotbugsBugClass setClassname(String classname) {
        this.classname = classname;
        return this;
    }

    public int getStartLine() {
        return startLine;
    }

    public SpotbugsBugClass setStartLine(int startLine) {
        this.startLine = startLine;
        return this;
    }

    public int getEndLine() {
        return endLine;
    }

    public SpotbugsBugClass setEndLine(int endLine) {
        this.endLine = endLine;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SpotbugsBugClass setMessage(String message) {
        this.message = message;
        return this;
    }
}
