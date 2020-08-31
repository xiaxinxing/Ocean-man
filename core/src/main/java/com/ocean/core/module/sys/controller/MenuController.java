package com.ocean.core.module.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.ocean.common.utils.JSONMessage;
import com.ocean.core.commons.constant.CommonConstant;
import com.ocean.core.commons.vo.ZtreeNode;
import com.ocean.core.module.sys.entity.Menu;
import com.ocean.core.module.sys.mapper.UserMapper;
import com.ocean.core.module.sys.service.IMenuService;
import com.ocean.core.module.sys.utils.SysUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xxx
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUserUtil sysUserUtil;

    @RequestMapping("list")
    public Object list(Menu menu) {
        try {
            Assert.notNull(menu.getPage(), "当前页码为空");
            Assert.notNull(menu.getLimit(), "当前分页数量为空");
        } catch (IllegalArgumentException e) {
            return JSONMessage.error(e);
        }
        Page<Menu> menuPage = new Page<>(menu.getPage(), menu.getLimit());
        //搜索条件
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(menu.getName())) {
            queryWrapper.like(Menu::getName, menu.getName());
        }
        if (null != menu.getIsEnable()) {
            queryWrapper.eq(Menu::getIsEnable, menu.getIsEnable());
        }
        Page<Menu> page = menuService.page(menuPage, queryWrapper);
        return JSONMessage.success(new HashMap<String, Object>() {{
            put("count", page.getTotal());
            put("data", page.getRecords());
        }});
    }

    @RequestMapping("getById")
    public JSONMessage getById(Menu menu) {
        return JSONMessage.success(null != menuService.getById(menu.getId()) ? menuService.getById(menu.getId()) : new Menu());
    }
    @RequestMapping("/save")
    public JSONMessage save(Menu menu){
        return JSONMessage.success(menuService.saveOrUpdate(menu));
    }







    @RequestMapping("getMenuTree")
    public Object MenuTree(Menu menu) {
        //所有搜索条件的菜单集合
        List<Menu> menus = userMapper.selectMenuList(sysUserUtil.getCurrentUser().getId(), menu.getIsEnable(), CommonConstant.SYSMENU_TYPE_MENU, null);
        return JSONMessage.success(buildMenuTree(0, menus));
    }

    public List<Menu> buildMenuTree(Integer parentId, List<Menu> allMenus) {
        List<Menu> childList = new ArrayList<>();
        allMenus.forEach(e -> {
            if (e.getPid().equals(parentId)) {
                e.setChildren(buildMenuTree(e.getId(), allMenus));
                childList.add(e);
            }
        });
        return childList;
    }

    @RequestMapping("getMenuTreeNodeByRole")
    public Object getMenuTreeNodeByRole() {


        return "";
    }

    /**
     * 构建简单格式的ztree
     * @return List<ZtreeNode>
     */
    public List<ZtreeNode> buildSimpleTreeNode(List<Menu> allMenus, List<Menu> checkedMenus) {
        List<Integer> checkIds = checkedMenus.stream().map(Menu::getId).collect(Collectors.toList());
        List<ZtreeNode> NodeList = new ArrayList<>();
        allMenus.forEach(e -> {
            ZtreeNode ztreeNode = new ZtreeNode();
            ztreeNode.setId(e.getId().toString());
            ztreeNode.setPid(e.getPid().toString());
            ztreeNode.setName(e.getName());
            ztreeNode.setChecked(checkIds.contains(e.getId()));
            NodeList.add(ztreeNode);
        });
        return NodeList;
    }

    /**
     * 构建json嵌套的ztree
     */
    public List<ZtreeNode> buildTreeNode(Integer pid, List<Menu> allMenus, List<Integer> checkIds) {
        List<ZtreeNode> childList = new ArrayList<>();
        allMenus.forEach(e -> {
            ZtreeNode ztreeNode = new ZtreeNode();
            ztreeNode.setId(e.getId().toString());
            ztreeNode.setPid(e.getPid().toString());
            ztreeNode.setName(e.getName());
            ztreeNode.setChecked(checkIds.contains(e.getPid()));
            if (e.getPid().equals(pid)) {
                ztreeNode.setChildren(buildTreeNode(e.getId(), allMenus, checkIds));
                childList.add(ztreeNode);
            }
        });
        return childList;
    }

}
