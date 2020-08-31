package com.ocean.sys.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.sys.log.entity.SysLog;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysLogMapper extends BaseMapper<SysLog> {




    @Update("create table sys_log\n" +
            "(\n" +
            "    id varchar(64) primary key  comment '主键',\n" +
            "    request_time date  comment '请求时间',\n" +
            "    url varchar(64) default ''  comment '请求web路径',\n" +
            "    request_body varchar(2000) default ''  comment '请求体',\n" +
            "    ip varchar(20) default ''  comment '请求ip'\n" +
            ") ")
    void createLogTable();


    @Select("SELECT table_name FROM information_schema.TABLES WHERE table_name = #{tableName} ")
    String existTable(String tableName);

}