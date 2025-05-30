package io.tiklab.arbess.setting.other.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;



//@ApiModel
@Join
@Mapper
public class Resources {

    //@ApiProperty(name = "id" ,desc="id")
    private String id;

    //@ApiProperty(name = "version" ,desc="版本 1.免费 2.付费")
    private int version;

    //@ApiProperty(name = "month" ,desc="月份")
    private String month;

    //@ApiProperty(name = "useCcyNumber" ,desc="使用并发数")
    private int useCcyNumber;

    //@ApiProperty(name = "residueCcyNumber" ,desc="剩余并发数")
    private int residueCcyNumber;

    //@ApiProperty(name = "residueCcyNumber" ,desc="总并发数")
    private int ccyNumber;

    //@ApiProperty(name = "useSceNumber" ,desc="总构建时长")
    private int sceNumber;

    //@ApiProperty(name = "useSceNumber" ,desc="使用构建时长")
    private int useSceNumber;

    //@ApiProperty(name = "residueSceNumber" ,desc="剩余构建时长")
    private int residueSceNumber;

    //@ApiProperty(name = "cacheNumber" ,desc="总缓存大小")
    private double cacheNumber;

    //@ApiProperty(name = "useCacheNumber" ,desc="使用缓存大小")
    private double useCacheNumber;

    //@ApiProperty(name = "residueCacheNumber" ,desc="剩余缓存大小")
    private double residueCacheNumber;

    private String beginTime;

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getUseCcyNumber() {
        return useCcyNumber;
    }

    public void setUseCcyNumber(int useCcyNumber) {
        this.useCcyNumber = useCcyNumber;
    }

    public int getResidueCcyNumber() {
        return residueCcyNumber;
    }

    public void setResidueCcyNumber(int residueCcyNumber) {
        this.residueCcyNumber = residueCcyNumber;
    }

    public int getCcyNumber() {
        return ccyNumber;
    }

    public void setCcyNumber(int ccyNumber) {
        this.ccyNumber = ccyNumber;
    }

    public int getSceNumber() {
        return sceNumber;
    }

    public void setSceNumber(int sceNumber) {
        this.sceNumber = sceNumber;
    }

    public int getUseSceNumber() {
        return useSceNumber;
    }

    public void setUseSceNumber(int useSceNumber) {
        this.useSceNumber = useSceNumber;
    }

    public int getResidueSceNumber() {
        return residueSceNumber;
    }

    public void setResidueSceNumber(int residueSceNumber) {
        this.residueSceNumber = residueSceNumber;
    }

    public double getCacheNumber() {
        return cacheNumber;
    }

    public void setCacheNumber(double cacheNumber) {
        this.cacheNumber = cacheNumber;
    }

    public double getUseCacheNumber() {
        return useCacheNumber;
    }

    public void setUseCacheNumber(double useCacheNumber) {
        this.useCacheNumber = useCacheNumber;
    }

    public double getResidueCacheNumber() {
        return residueCacheNumber;
    }

    public void setResidueCacheNumber(double residueCacheNumber) {
        this.residueCacheNumber = residueCacheNumber;
    }
}
