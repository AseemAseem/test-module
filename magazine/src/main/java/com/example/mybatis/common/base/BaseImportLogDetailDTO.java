package com.example.mybatis.common.base;


public interface BaseImportLogDetailDTO {

    /**
     * 获取编号
     * @return 行号
     */
    Integer getNo();

    /**
     * 设置行号
     * @param no 行号
     */
    void setNo(Integer no);

    /**
     * 判断是否成功
     * @return 是否成功
     */
    Boolean getSuccess();

    /**
     * 设置是否成功
     * @param success 是否成功
     */
    void setSuccess(Boolean success);

    /**
     * 获取失败信息
     * @return 失败信息
     */
    String getFailMessage();

    /**
     * 设置失败信息
     * @param failMessage 失败信息
     */
    void setFailMessage(String failMessage);

    /**
     * 获取生成的用户名
     * @return 用户名
     */
    String getAutoUserName();

    /**
     * 设置用户名
     * @param autoUserName 用户名
     */
    void setAutoUserName(String autoUserName);

    /**
     * 获取生成的密码
     * @return 密码
     */
    String getAutoPassword();

    /**
     * 设置密码
     * @param autoPassword 密码
     */
    void setAutoPassword(String autoPassword);
}
