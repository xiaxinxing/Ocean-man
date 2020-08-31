package com.ocean.core.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxx
 * @since 2020-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_log")
@ApiModel(value="Log对象", description="")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请求url")
    private String url;

    @ApiModelProperty(value = "请求花费时间 毫秒")
    private Integer costTime;

    @ApiModelProperty(value = "请求方式 ")
    @TableField("request_Type")
    private Integer requestType;

    @ApiModelProperty(value = "请求ip")
    private String ip;

    @ApiModelProperty(value = "方法体")
    private String method;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "操作人姓名")
    private String userName;

    @ApiModelProperty(value = "操作人id")
    private String userId;

    @ApiModelProperty(value = "日志详细描述")
    private String logContent;

    @ApiModelProperty(value = "日志类型 登录日志或操作日志")
    private Integer logType;

    @ApiModelProperty(value = "操作类型 增删改查")
    private Integer operateType;


}
