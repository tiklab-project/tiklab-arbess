package io.thoughtware.arbess.task.codescan.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

/**
 * @author Spotbugs代码扫描信息
 */
@Join
@Mapper(targetName = "io.thoughtware.matflow.task.codescan.entity.SpotbugsScanEntity")
public class SpotbugsBugSummary {

    // id
    private String id;

    // 流水线id
    private String pipelineId;

    // 扫描时间
    private String scanTime;

    // 总共扫描的类的数量。
    private String totalClasses;

    // 引用的类的数量
    private String referencedClasses;

    // 检测到的问题（Bugs）的数量
    private String totalBugs;

    // 项目中的包（package）数量
    private String numPackages;

    // Java 版本信息
    private String vmVersion;

    // 优先级为 1 的问题数
    private String priorityOne;

    // 优先级为 2 的问题数
    private String priorityTwo;

    // 优先级为 3 的问题数
    private String priorityThree;

    // xml文件报错地址
    private String xmlPath;

    // xml文件内容
    private String xmlFileContent;

    public String getXmlFileContent() {
        return xmlFileContent;
    }

    public void setXmlFileContent(String xmlFileContent) {
        this.xmlFileContent = xmlFileContent;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public SpotbugsBugSummary setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
        return this;
    }

    public String getVmVersion() {
        return vmVersion;
    }

    public SpotbugsBugSummary setVmVersion(String vmVersion) {
        this.vmVersion = vmVersion;
        return this;
    }

    public String getId() {
        return id;
    }

    public SpotbugsBugSummary setId(String id) {
        this.id = id;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public SpotbugsBugSummary setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getScanTime() {
        return scanTime;
    }

    public SpotbugsBugSummary setScanTime(String scanTime) {
        this.scanTime = scanTime;
        return this;
    }

    public String getTotalClasses() {
        return totalClasses;
    }

    public SpotbugsBugSummary setTotalClasses(String totalClasses) {
        this.totalClasses = totalClasses;
        return this;
    }

    public String getReferencedClasses() {
        return referencedClasses;
    }

    public SpotbugsBugSummary setReferencedClasses(String referencedClasses) {
        this.referencedClasses = referencedClasses;
        return this;
    }

    public String getTotalBugs() {
        return totalBugs;
    }

    public SpotbugsBugSummary setTotalBugs(String totalBugs) {
        this.totalBugs = totalBugs;
        return this;
    }

    public String getNumPackages() {
        return numPackages;
    }

    public SpotbugsBugSummary setNumPackages(String numPackages) {
        this.numPackages = numPackages;
        return this;
    }

    public String getPriorityOne() {
        return priorityOne;
    }

    public SpotbugsBugSummary setPriorityOne(String priorityOne) {
        this.priorityOne = priorityOne;
        return this;
    }

    public String getPriorityTwo() {
        return priorityTwo;
    }

    public SpotbugsBugSummary setPriorityTwo(String priorityTwo) {
        this.priorityTwo = priorityTwo;
        return this;
    }

    public String getPriorityThree() {
        return priorityThree;
    }

    public SpotbugsBugSummary setPriorityThree(String priorityThree) {
        this.priorityThree = priorityThree;
        return this;
    }
















}
