package com.ocean.core.module.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.jws.soap.SOAPBinding;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxx
 * @since 2020-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="User对象", description="")
@NoArgsConstructor
public class User  implements Serializable {
    
    
    
    @TableId
    private String id;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "用户登录名")
    private String loginName;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户密码 ")
    private String password;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "vip等级")
    private Integer vipLevel;

    @ApiModelProperty(value = "vip积分")
    private Integer vipPoint;

    @ApiModelProperty(value = "对应微信openId")
    private Integer wechatOpenId;



    @TableField(exist = false)
    private String roleId;




}
