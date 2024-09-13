package io.thoughtware.arbess.task.codescan.entity;

import io.thoughtware.dal.jpa.annotation.*;

/**
 * @author Spotbugs代码扫描
 */
@Entity
@Table(name="pip_task_code_scan_spotbugs")
public class SpotbugsScanEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    // 流水线id
    @Column(name = "pipeline_id" ,notNull = true)
    private String pipelineId;

    // 扫描时间
    @Column(name = "scan_time" ,notNull = true)
    private String scanTime;

    // 总共扫描的类的数量。
    @Column(name = "total_classes" ,notNull = true)
    private String totalClasses;

    // 引用的类的数量
    @Column(name = "referenced_classes" ,notNull = true)
    private String referencedClasses;

    // 检测到的问题（Bugs）的数量
    @Column(name = "total_bugs" ,notNull = true)
    private String totalBugs;

    // 项目中的包（package）数量
    @Column(name = "num_packages" ,notNull = true)
    private String numPackages;

    // 优先级为 1 的问题数
    @Column(name = "priority_one" ,notNull = true)
    private String priorityOne;

    // 优先级为 2 的问题数
    @Column(name = "priority_two" ,notNull = true)
    private String priorityTwo;

    // 优先级为 3 的问题数
    @Column(name = "priority_three" ,notNull = true)
    private String priorityThree;

    @Column(name = "xml_path" ,notNull = true)
    private String xmlPath;

    public String getXmlPath() {
        return xmlPath;
    }

    public SpotbugsScanEntity setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
        return this;
    }

    public String getId() {
        return id;
    }

    public SpotbugsScanEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public SpotbugsScanEntity setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getScanTime() {
        return scanTime;
    }

    public SpotbugsScanEntity setScanTime(String scanTime) {
        this.scanTime = scanTime;
        return this;
    }

    public String getTotalClasses() {
        return totalClasses;
    }

    public SpotbugsScanEntity setTotalClasses(String totalClasses) {
        this.totalClasses = totalClasses;
        return this;
    }

    public String getReferencedClasses() {
        return referencedClasses;
    }

    public SpotbugsScanEntity setReferencedClasses(String referencedClasses) {
        this.referencedClasses = referencedClasses;
        return this;
    }

    public String getTotalBugs() {
        return totalBugs;
    }

    public SpotbugsScanEntity setTotalBugs(String totalBugs) {
        this.totalBugs = totalBugs;
        return this;
    }

    public String getNumPackages() {
        return numPackages;
    }

    public SpotbugsScanEntity setNumPackages(String numPackages) {
        this.numPackages = numPackages;
        return this;
    }

    public String getPriorityOne() {
        return priorityOne;
    }

    public SpotbugsScanEntity setPriorityOne(String priorityOne) {
        this.priorityOne = priorityOne;
        return this;
    }

    public String getPriorityTwo() {
        return priorityTwo;
    }

    public SpotbugsScanEntity setPriorityTwo(String priorityTwo) {
        this.priorityTwo = priorityTwo;
        return this;
    }

    public String getPriorityThree() {
        return priorityThree;
    }

    public SpotbugsScanEntity setPriorityThree(String priorityThree) {
        this.priorityThree = priorityThree;
        return this;
    }
}
