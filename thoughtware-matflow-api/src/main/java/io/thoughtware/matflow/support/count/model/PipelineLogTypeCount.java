package io.thoughtware.matflow.support.count.model;

import io.thoughtware.security.logging.logging.model.LoggingType;

public class PipelineLogTypeCount {

    private LoggingType loggingType;

    private Integer typeNumber;


    public LoggingType getLoggingType() {
        return loggingType;
    }

    public void setLoggingType(LoggingType loggingType) {
        this.loggingType = loggingType;
    }

    public Integer getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(Integer typeNumber) {
        this.typeNumber = typeNumber;
    }
}
