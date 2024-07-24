package io.thoughtware.matflow.support.count.model;

import io.thoughtware.security.logging.logging.model.LoggingType;
import io.thoughtware.user.user.model.User;

import java.util.List;

public class PipelineLogUserCount {


    private LoggingType loggingType;

    private Integer typeNumber;

    private User user;

    private List<PipelineLogUserCount> userCountList;

    public PipelineLogUserCount() {
    }

    public PipelineLogUserCount(User user, Integer typeNumber) {
        this.user = user;
        this.typeNumber = typeNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PipelineLogUserCount> getUserCountList() {
        return userCountList;
    }

    public void setUserCountList(List<PipelineLogUserCount> userCountList) {
        this.userCountList = userCountList;
    }

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
