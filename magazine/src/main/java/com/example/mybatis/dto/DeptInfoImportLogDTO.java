package com.example.mybatis.dto;

import com.example.mybatis.common.base.BaseImportLogDetailDTO;


public class DeptInfoImportLogDTO implements BaseImportLogDetailDTO {
    private Integer no;

    private Boolean isSuccess;

    private String failMessage;

    @Override
    public Integer getNo() {
        return no;
    }

    @Override
    public void setNo(Integer no) {
        this.no = no;
    }

    @Override
    public Boolean getSuccess() {
        return isSuccess;
    }

    @Override
    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    @Override
    public String getFailMessage() {
        return failMessage;
    }

    @Override
    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    @Override
    public String getAutoUserName() {
        return null;
    }

    @Override
    public void setAutoUserName(String autoUserName) {

    }


    @Override
    public String getAutoPassword() {
        return null;
    }

    @Override
    public void setAutoPassword(String autoPassword) {

    }
}
