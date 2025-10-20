package io.tiklab.arbess.pipeline.definition.model;

import io.tiklab.core.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class PipelinePermissions extends BaseModel {

    private Boolean exec = false;

    private String execCode =  "pip_design_run";

    private Boolean delete = false;

    private String deleteCode = "pip_setting_delete";

    private Boolean edit = false;

    private String editCode = "pip_setting_update";

    private Boolean setting = false;

    private String settingCode = "pip_setting_msg";

    private Boolean clone = false;

    private String cloneCode = "pip_setting_clone";

    private Boolean export = false;

    private String exportCode = "pip_setting_export";


    public List<String> findPermissionList(){
        List<String> permissions = new ArrayList<>();
        permissions.add(execCode);
        permissions.add(deleteCode);
        permissions.add(editCode);
        permissions.add(settingCode);
        permissions.add(cloneCode);
        permissions.add(exportCode);
        return permissions;
    }

    public Boolean getExec() {
        return exec;
    }

    public void setExec(Boolean exec) {
        this.exec = exec;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getSetting() {
        return setting;
    }

    public void setSetting(Boolean setting) {
        this.setting = setting;
    }

    public Boolean getClone() {
        return clone;
    }

    public void setClone(Boolean clone) {
        this.clone = clone;
    }

    public String getExecCode() {
        return execCode;
    }

    public void setExecCode(String execCode) {
        this.execCode = execCode;
    }

    public String getDeleteCode() {
        return deleteCode;
    }

    public void setDeleteCode(String deleteCode) {
        this.deleteCode = deleteCode;
    }

    public String getEditCode() {
        return editCode;
    }

    public void setEditCode(String editCode) {
        this.editCode = editCode;
    }

    public String getSettingCode() {
        return settingCode;
    }

    public void setSettingCode(String settingCode) {
        this.settingCode = settingCode;
    }

    public String getCloneCode() {
        return cloneCode;
    }

    public void setCloneCode(String cloneCode) {
        this.cloneCode = cloneCode;
    }

    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }

    public String getExportCode() {
        return exportCode;
    }

    public void setExportCode(String exportCode) {
        this.exportCode = exportCode;
    }

    public void addPipelinePermission(PipelinePermissions permissions, String permissionsString){

        if (permissionsString.contains(permissions.getExecCode())){
            permissions.setExec(true);
        }

        if (permissionsString.contains(permissions.getDeleteCode())){
            permissions.setDelete(true);
        }

        if (permissionsString.contains(permissions.getEditCode())){
            permissions.setEdit(true);
        }

        if (permissionsString.contains(permissions.getSettingCode())){
            permissions.setSetting(true);
        }

        if (permissionsString.contains(permissions.getCloneCode())){
            permissions.setClone(true);
        }

        if (permissionsString.contains(permissions.getExportCode())){
            permissions.setExport(true);
        }
    }
}
