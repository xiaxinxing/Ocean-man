package com.ocean.core.module.sys.mapper;

import com.ocean.core.module.sys.entity.Menu;
import com.ocean.core.module.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xxx
 * @since 2020-07-04
 */
public interface UserMapper extends BaseMapper<User> {


    List<Menu> selectMenuList(String userId,Integer isEnabled, String menuType,String name);

    User selectUserAndRoleByLoginName(String loginName);

}
