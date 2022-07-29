package com.tiklab.matfiow.execute.model.CodeGit;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.join.annotation.Join;

import java.util.List;

/**
 * 获取git代码的提交记录
 */

@ApiModel
@Join
public class GitCommit {

    //提交ID
    private String commitId;

    //提交人
    private String commitName;

    //提交时间
    private String commitTime;

    //提交信息
    private String commitMassage;

    //提交文件
    private List<String> commitFile;

    //评论信息
    private List<GitCommit> gitCommit;

    //天数
    private String dayTime;

    //时间戳
    private int time;


    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getCommitName() {
        return commitName;
    }

    public void setCommitName(String commitName) {
        this.commitName = commitName;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getCommitMassage() {
        return commitMassage;
    }

    public void setCommitMassage(String commitMassage) {
        this.commitMassage = commitMassage;
    }

    public List<String> getCommitFile() {
        return commitFile;
    }

    public void setCommitFile(List<String> commitFile) {
        this.commitFile = commitFile;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<GitCommit> getCommit() {
        return gitCommit;
    }

    public void setCommit(List<GitCommit> gitCommit) {
        this.gitCommit = gitCommit;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }
}
