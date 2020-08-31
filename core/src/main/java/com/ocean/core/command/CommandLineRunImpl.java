package com.ocean.core.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommandLineRunImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("--------------系统启动成功----------");
    }
}
