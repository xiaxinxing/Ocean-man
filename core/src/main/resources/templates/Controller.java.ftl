package ${package.Controller};

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ocean.common.utils.JSONMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import ${package.Entity}.${entity};
import ${package.ServiceImpl}.${entity}Service;
import cn.hutool.core.util.StrUtil;

/**
 *
 * @ClassName: ${table.controllerName}
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ${author}
 * @date ${date}
 */

@RestController
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
public class ${table.controllerName} {

    @Autowired
    private ${entity}Service ${table.entityPath}Service;

    @RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
    public JSONMessage list(${package.ModuleName}${entity} ${table.entityPath}){
        return JSONMessage.success(${table.entityPath}Service.page(new Page<>(), Wrappers.lambdaQuery()));
    }

    @RequestMapping(value = "getById",method = RequestMethod.GET)
    public JSONMessage getById(String id){
        return JSONMessage.success(${table.entityPath}Service.getById(id));
    }
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public JSONMessage save(${package.ModuleName}${entity} ${table.entityPath}){
        return JSONMessage.success(${table.entityPath}Service.save(${table.entityPath}));
    }
    @RequestMapping(value = "remove",method = RequestMethod.GET)
    public JSONMessage remove(String id){
        return JSONMessage.success(${table.entityPath}Service.removeById(id));
    }

}