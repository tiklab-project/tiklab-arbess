package io.thoughtware.matflow.task.codescan.model;

/**
 * @author zcamy
 */
public class SpotbugsBugMethod {

    private String methodName;

    private String signature;

    private int startLine;

    private int endLine;

    private String message;

    public String getMethodName() {
        return methodName;
    }

    public SpotbugsBugMethod setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public SpotbugsBugMethod setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public int getStartLine() {
        return startLine;
    }

    public SpotbugsBugMethod setStartLine(int startLine) {
        this.startLine = startLine;
        return this;
    }

    public int getEndLine() {
        return endLine;
    }

    public SpotbugsBugMethod setEndLine(int endLine) {
        this.endLine = endLine;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SpotbugsBugMethod setMessage(String message) {
        this.message = message;
        return this;
    }
}
