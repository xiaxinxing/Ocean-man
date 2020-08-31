package com.ocean.core.commons.conf;

import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.ocean.core.properties.MpshareTableProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * @author : xxx
 * @date : 2019-12-11 15:34
 * @Desc 启用mybatis分页
 **/
@EnableTransactionManagement
@Configuration
@MapperScan("com.xxx.mybatisplus.mapper")
@Slf4j
public class MybatisPlusConfig {


    @Autowired
    MpshareTableProperties tableProperties;


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        //dynamicTableNameParser.setTableNameHandlerMap(getTableNameHandlerMap());
        //paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
        return paginationInterceptor;
    }

    /**
     * 自定义动态表名规则
     **/
    private HashMap<String, ITableNameHandler> getTableNameHandlerMap() {
        return new HashMap<String, ITableNameHandler>(2) {{
            //涉及表集合
            List<String> tables = tableProperties.getShareTable();
            //动态表规则 初始表名+_+code
            tables.forEach(tableTitle -> put(tableTitle, (metaObject, sql, tableName) -> geTableRule(tableName)));

        }};
    }

    private String geTableRule(String tableName) {
        LocalDate now = LocalDate.now();
        LocalDate date = SharingTableContext.getDate();
        if (null != SharingTableContext.getDate()) {
            now = SharingTableContext.getDate();
        }
        String timeString = LocalDate.of(now.getYear(), now.getMonth(), 1).toString();
        //查询表名是否存在
        String newTableName = tableName.toUpperCase() + "_" + timeString.replace("-", "_");
        /*int i = sysUserMapper.selectTableExists(newTableName);
        if (i == 0) {
            sysUserMapper.createTable(newTableName);
        }*/
        log.info("此次查询的表名为 {}", newTableName);
        return newTableName;
    }


}


