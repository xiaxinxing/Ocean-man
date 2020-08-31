package com.ocean.core.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    @TableId
    private Integer id;

    private String name;

    private String url;

    private Integer pid;

    private Integer level;

    private String menuIcon;

    private String menuType;

    private String remarks;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String sort;

    private Integer isEnable;

    private Integer hidden;

    private String createBy;

    private String updateBy;

    @ApiModelProperty(value = "shiro的权限名 多个逗号区分")
    private String permissions;

    /**
     * 分页字段
     */
    @TableField(exist = false)
    private Integer page;
    @TableField(exist = false)
    private Integer limit;
    private List<Menu> children;


}
