package io.tiklab.arbess.pipeline.instance.model;

import io.tiklab.core.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class PipelineInstancePermissions extends BaseModel {

    private Boolean delete = false;

    private String deleteCode =  "pip_history_delete";

    private Boolean rollback = false;

    private String rollbackCode = "pip_history_rollback";

    private Boolean run = false;

    private String runCode = "pip_history_run";




    public List<String> findPermissionList(){
        List<String> permissions = new ArrayList<>();
        permissions.add(runCode);
        permissions.add(deleteCode);
        permissions.add(rollbackCode);
        return permissions;
    }

    public Boolean getRollback() {
        return rollback;
    }

    public void setRollback(Boolean rollback) {
        this.rollback = rollback;
    }

    public String getRollbackCode() {
        return rollbackCode;
    }

    public void setRollbackCode(String rollbackCode) {
        this.rollbackCode = rollbackCode;
    }

    public Boolean getRun() {
        return run;
    }

    public void setRun(Boolean run) {
        this.run = run;
    }

    public String getRunCode() {
        return runCode;
    }

    public void setRunCode(String runCode) {
        this.runCode = runCode;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }


    public String getDeleteCode() {
        return deleteCode;
    }

    public void setDeleteCode(String deleteCode) {
        this.deleteCode = deleteCode;
    }




    public void addPipelinePermission(PipelineInstancePermissions permissions, String permissionsString){
        if (permissionsString.contains(permissions.getDeleteCode())){
            permissions.setDelete(true);
        }
        if (permissionsString.contains(permissions.getRunCode())){
            permissions.setRun(true);
        }
        if (permissionsString.contains(permissions.getRollbackCode())){
            permissions.setRollback(true);
        }

    }
}
