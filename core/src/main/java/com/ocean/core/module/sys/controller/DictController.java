package com.ocean.core.module.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.common.utils.JSONMessage;
import com.ocean.core.commons.vo.ResultMessage;
import com.ocean.core.module.sys.entity.SysDict;
import com.ocean.core.module.sys.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxx
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/sys/dict")
public class DictController {

    @Autowired
    IDictService dictService;


    @RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
    public ResultMessage<Object> list(SysDict dict){
        LambdaQueryWrapper<SysDict> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(dict.getDictCode())){
            queryWrapper.eq(SysDict::getDictCode,dict.getDictCode());
        }
        if(StringUtils.isNotBlank(dict.getDictName())){
            queryWrapper.like(SysDict::getDictName,dict.getDictName());
        }
        return ResultMessage.ok(dictService.page(new Page<>(), queryWrapper));
    }

    @RequestMapping(value = "getById",method = RequestMethod.GET)
    public ResultMessage<Object> getById(String id){
        return ResultMessage.ok(dictService.getById(id));
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public ResultMessage<Object> save(SysDict dict){
        try {
            Assert.hasLength(dict.getDictCode(),"字典类型为空");
            Assert.hasLength(dict.getDictLabel(),"字典标签名为空");
            Assert.hasLength(dict.getDictValue(),"字典值为空");
        }catch (IllegalArgumentException exception){
            return ResultMessage.error(exception.getMessage());
        }
        return ResultMessage.ok(dictService.save(dict));
    }
    @RequestMapping(value = "remove",method = RequestMethod.GET)
    public JSONMessage remove(String id){
        return JSONMessage.success(dictService.removeById(id));
    }
}
