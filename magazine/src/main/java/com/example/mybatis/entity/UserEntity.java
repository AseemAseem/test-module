package com.example.mybatis.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.example.mybatis.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("cr_user")
@ApiModel(value = "类")
@Data
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID")
    private Long id;
    /**
     * 全局唯一id,对应协同用户id
     */
    @ApiModelProperty(value = "全局唯一id,对应协同用户id")
    private String guid;
    /**
     * 登录用户
     */
    @ApiModelProperty(value = "登录用户")
    private String username;
    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    private String password;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String telephone;
    /**
     * 盐值
     */
    @ApiModelProperty(value = "盐值")
    private String salt;
    /**
     * 组织代码
     */
    @TableField("org_id")
    @ApiModelProperty(value = "组织代码")
    private Long orgId;
    /**
     * 全局机构id
     */
    @ApiModelProperty(value = "全局机构id")
    private String goid;
    /**
     * 组织类型：1省级CDC，2市级CDC，3县级CDC 4,医院
     */
    @TableField("org_type")
    @ApiModelProperty(value = "组织类型：1省级CDC，2市级CDC，3县级CDC 4,医院")
    private Integer orgType;
    /**
     * 是否启用 0-启用 1-停用
     */
    @ApiModelProperty(value = "是否启用 0-启用 1-停用")
    private Integer status;
    /**
     * 全局科室id
     */
    @ApiModelProperty(value = "全局科室id")
    private String gdid;
    /**
     * 医院科室代码
     */
    @TableField("dept_code")
    @ApiModelProperty(value = "医院科室代码")
    private String deptCode;
    /**
     * 医院科室名称
     */
    @TableField("dept_name")
    @ApiModelProperty(value = "医院科室名称")
    private String deptName;
    /**
     * 工号
     */
    @TableField("employee_id")
    @ApiModelProperty(value = "工号")
    private String employeeId;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 职称
     */
    @TableField("job_title")
    @ApiModelProperty(value = "职称")
    private String jobTitle;
    /**
     * 专家头衔 0-无 1-临床专家 2-检验科专家 3-放射科专家 4-公共卫生科专家
     */
    @TableField("professor_type")
    @ApiModelProperty(value = "专家头衔 0-无 1-临床专家 2-检验科专家 3-放射科专家 4-公共卫生科专家")
    private Integer professorType;
    /**
     * 性别枚举 1-男 2-女
     */
    @ApiModelProperty(value = "性别枚举 1-男 2-女")
    private String gender;
    /**
     * 上次登录时间
     */
    @TableField("login_time")
    @ApiModelProperty(value = "上次登录时间")
    private Date loginTime;
    /**
     * 上次登录IP
     */
    @TableField("login_ip")
    @ApiModelProperty(value = "上次登录IP")
    private String loginIp;
    /**
     * 0-非管理员 1-管理员
     */
    @ApiModelProperty(value = "0-非管理员 1-管理员")
    private Integer admin;
    /**
     * 权限范围 1-全省 2-全市 3-全县 4-全院
     */
    @TableField("data_scope")
    @ApiModelProperty(value = "权限范围 1-全省 2-全市 3-全县 4-全院")
    private Integer dataScope;
    /**
     * 电子签名图片url
     */
    @TableField("sign_url")
    @ApiModelProperty(value = "电子签名图片url")
    private String signUrl;
    /**
     * 版本号 用于强制退出token
     */
    @ApiModelProperty(value = "版本号 用于强制退出token")
    private Integer version;
    /**
     * 0-非专干人员 1-专干人员
     */
    @TableField("full_time_staff")
    @ApiModelProperty(value = "0-非专干人员 1-专干人员")
    private Integer fullTimeStaff;
}
