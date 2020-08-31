package com.ocean.core.module.sys.utils;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ocean.core.commons.constant.CommonConstant;
import com.ocean.core.commons.handler.BaseContextHandler;
import com.ocean.core.module.sys.entity.Menu;
import com.ocean.core.module.sys.entity.Role;
import com.ocean.core.module.sys.entity.User;
import com.ocean.core.module.sys.entity.UserRole;
import com.ocean.core.module.sys.mapper.RoleMapper;
import com.ocean.core.module.sys.mapper.UserMapper;
import com.ocean.core.module.sys.mapper.UserRoleMapper;
import com.ocean.core.module.sys.service.IRoleService;
import com.ocean.core.module.sys.service.IUserRoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SysUserUtil {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();


    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    IRoleService roleService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    UserMapper userMapper;


    public User getUser(String userName) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getLoginName, userName));
    }

    /**
     * 获取当前登录用户
     *
     * @return User
     */
    public User getCurrentUser() {
        return userMapper.selectById(BaseContextHandler.getCurrentUserId());
    }

    /**
     * 获取当前登录用户的角色
     */
    public Role getRoleByUser() {
        return roleService.getById(userRoleService.getOne(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, getCurrentUser().getId())).getId());
    }

    /**
     * 获取当前登录用户 权限范围内的菜单并且是启用状态
     *
     * @return List<Menu>
     */
    public List<Menu> getMenuByUser() {
        return userMapper.selectMenuList(getCurrentUser().getId(), CommonConstant.IS_ENABLE_TRUE, CommonConstant.SYSMENU_TYPE_MENU, null);
    }

    /**
     * 获取当前登录用户所有有权限的按钮类型
     */
    public List<Menu> getMenuButtonByUser() {
        return userMapper.selectMenuList(getCurrentUser().getId(), CommonConstant.IS_ENABLE_TRUE, CommonConstant.SYSMENU_TYPE_BTN, null);
    }

    public List<Menu> getPermissionByUser(User user) {
        return userMapper.selectMenuList(user.getId(), CommonConstant.IS_ENABLE_TRUE, null, null);
    }


}
