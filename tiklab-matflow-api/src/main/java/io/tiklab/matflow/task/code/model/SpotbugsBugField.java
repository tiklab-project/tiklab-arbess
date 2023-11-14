package io.tiklab.matflow.task.code.model;

/**
 * @author zcamy
 */
public class SpotbugsBugField {


    private String fieldName;

    private String signature;

    private String message;

    public String getFieldName() {
        return fieldName;
    }

    public SpotbugsBugField setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public SpotbugsBugField setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SpotbugsBugField setMessage(String message) {
        this.message = message;
        return this;
    }
}
