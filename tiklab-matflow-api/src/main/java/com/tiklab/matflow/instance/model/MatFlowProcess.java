package com.tiklab.matflow.instance.model;


import com.tiklab.join.annotation.Join;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.execute.model.MatFlowDeploy;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.postlink.annotation.ApiModel;

/**
 * 存放执行的信息
 */

@ApiModel
@Join
public class MatFlowProcess {

    private Proof proof;

    private MatFlowExecHistory matFlowExecHistory;

    private MatFlowExecLog matFlowExecLog;

    private MatFlowConfigure matFlowConfigure;

    private MatFlowDeploy matFlowDeploy;

    public MatFlowExecHistory getMatFlowExecHistory() {
        return matFlowExecHistory;
    }

    public void setMatFlowExecHistory(MatFlowExecHistory matFlowExecHistory) {
        this.matFlowExecHistory = matFlowExecHistory;
    }

    public MatFlowExecLog getMatFlowExecLog() {
        return matFlowExecLog;
    }

    public void setMatFlowExecLog(MatFlowExecLog matFlowExecLog) {
        this.matFlowExecLog = matFlowExecLog;
    }

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
    }

    public MatFlowConfigure getMatFlowConfigure() {
        return matFlowConfigure;
    }

    public void setMatFlowConfigure(MatFlowConfigure matFlowConfigure) {
        this.matFlowConfigure = matFlowConfigure;
    }

    public MatFlowDeploy getMatFlowDeploy() {
        return matFlowDeploy;
    }

    public void setMatFlowDeploy(MatFlowDeploy matFlowDeploy) {
        this.matFlowDeploy = matFlowDeploy;
    }
}
