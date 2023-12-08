package io.thoughtware.matflow.task.codescan.model;

/**
 * @author zcamy
 */
public class SpotbugsBugSourceLine {


    private String sourcePath;


    private String sourceFile;

    private int startLine;

    private int endLine;

    private String message;


    public String getSourcePath() {
        return sourcePath;
    }

    public SpotbugsBugSourceLine setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public SpotbugsBugSourceLine setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
        return this;
    }

    public int getStartLine() {
        return startLine;
    }

    public SpotbugsBugSourceLine setStartLine(int startLine) {
        this.startLine = startLine;
        return this;
    }

    public int getEndLine() {
        return endLine;
    }

    public SpotbugsBugSourceLine setEndLine(int endLine) {
        this.endLine = endLine;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SpotbugsBugSourceLine setMessage(String message) {
        this.message = message;
        return this;
    }
}
