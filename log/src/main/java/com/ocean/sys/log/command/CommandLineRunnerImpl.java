package com.ocean.sys.log.command;


import com.ocean.sys.log.mapper.SysLogMapper;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @author : xxx
 * 在程序启动后执行
 **/
@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {


    @Autowired
    SysLogMapper logMapper;


    @Override
    public void run(String... args) throws Exception {
        log.info("=======日志系统启动成功,开始检查表结构=======");
        String sys_log = logMapper.existTable("sys_log");
        if (StringUtils.isBlank(sys_log)) {
            log.info("=======检测到没有表结构 开始建表=======");
            logMapper.createLogTable();
        }else{
            log.info("========检测已经建表======");
        }

    }
}
