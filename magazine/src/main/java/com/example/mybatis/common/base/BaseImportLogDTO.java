package com.example.mybatis.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel("导入信息")
public class BaseImportLogDTO {

    @ApiModelProperty(value = "总数")
    private int totalCount;

    @ApiModelProperty(value = "成功数")
    private int successCount;

    @ApiModelProperty(value = "失败数")
    private int failCount;

    @ApiModelProperty(value = "附件id")
    private Long attachmentId;

    private List<BaseImportLogDetailDTO> logDetailDTOS;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public List<BaseImportLogDetailDTO> getLogDetailDTOS() {
        return logDetailDTOS;
    }

    public void setLogDetailDTOS(List<BaseImportLogDetailDTO> logDetailDTOS) {
        this.logDetailDTOS = logDetailDTOS;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }
}
