package com.ocean.sys.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 日志配置类
 */

@Component
@ConfigurationProperties(prefix = "ocean-log")
@Data
public class LogProperties {

    /**
     * 是否启用日志记录功能
     */
    private String isEnable="1";

    /**
     * 只匹配  前缀相等的url
     */
    private String urlPrefix="/";


}
