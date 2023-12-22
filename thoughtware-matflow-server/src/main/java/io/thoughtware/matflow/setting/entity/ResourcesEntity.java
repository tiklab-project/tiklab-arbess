package io.thoughtware.matflow.setting.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name="pip_setting_resources")
public class ResourcesEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    @Column(name = "concurrency_number")
    private int useCcyNumber;

    @Column(name = "structure_number" )
    private int useSceNumber;

    @Column(name = "cache_number" )
    private int useCacheNumber;

    @Column(name = "month" )
    private String month;

    @Column(name = "begin_time" )
    private String beginTime;

    @Column(name = "end_time" )
    private String endTime;


    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUseSceNumber() {
        return useSceNumber;
    }

    public void setUseSceNumber(int useSceNumber) {
        this.useSceNumber = useSceNumber;
    }

    public int getUseCcyNumber() {
        return useCcyNumber;
    }

    public void setUseCcyNumber(int useCcyNumber) {
        this.useCcyNumber = useCcyNumber;
    }

    public int getUseCacheNumber() {
        return useCacheNumber;
    }

    public void setUseCacheNumber(int useCacheNumber) {
        this.useCacheNumber = useCacheNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
