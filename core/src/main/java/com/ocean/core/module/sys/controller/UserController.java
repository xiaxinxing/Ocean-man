package com.ocean.core.module.sys.controller;


import com.ocean.core.module.sys.entity.User;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxx
 * @since 2020-07-04
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {


    @RequestMapping("/check")
    public Object index(User user) {
        System.out.println(user.toString());
        return user.toString();
    }

}
