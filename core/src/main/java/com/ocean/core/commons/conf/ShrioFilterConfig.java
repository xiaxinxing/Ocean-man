package com.ocean.core.commons.conf;

import com.alibaba.fastjson.JSON;
import com.ocean.core.commons.filters.JwtFilter;
import com.ocean.core.commons.shirorealm.AuthRealm;
import com.ocean.core.commons.shirorealm.CustomCredentialsMatcher;
import com.ocean.core.properties.ShiroPermissionProperties;
import com.ocean.core.properties.ShiroProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author xxx
 * @Date 2019-10-14
 */
@Configuration
@Slf4j
public class ShrioFilterConfig {
    @Autowired
    ShiroProperties shiroProperties;

    /**
     * 请求路径过滤器名称
     */
    private static final String REQUEST_PATH_FILTER_NAME = "path";

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host+":"+port);
        redisManager.setTimeout(timeout);// 配置缓存过期时间
        return redisManager;
    }
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }
    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager redisSessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }
    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }




    /**
     * 给 realm  自定义验证方式 因为shiro-boot-start 默认会注入一个 realm
     * 如果需要自定义 则需要取名为 authorizer  默认realm发现beanname
     * 存在就不会去初始化默认的
     */
    @Bean(name = "authorizer")
    public AuthRealm authRealm() {
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher("SHA-1");
        customCredentialsMatcher.setHashIterations(1024);
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(customCredentialsMatcher);
        return authRealm;
    }

    /**
     * 设置 securityManager  用于管理realm
     */
    @Bean
    public DefaultWebSecurityManager securityManager(AuthRealm realm,DefaultWebSessionManager sessionManager,RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(realm);
        defaultSecurityManager.setSessionManager(sessionManager);
        defaultSecurityManager.setCacheManager(redisCacheManager);
        return defaultSecurityManager;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持; Controller才能使用@RequiresPermissions
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置需要过滤的路由   /anno 不需要验证    /authc 需要验证用户
     *
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilters(getFilterMap());
        HashMap<String, String> map = new LinkedHashMap<>(8);
        map.put("/register", "anon");
        map.put("/login", "anon");
        map.put("/getCode", "anon");
        map.putAll(getFilterChainDefinitionMap());
        map.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;

    }

    /**
     * 使用 DelegatingFilterProxy 去代理 shiro自带的过滤器
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilterFactoryBean");
        filterRegistrationBean.setFilter(proxy);
        filterRegistrationBean.setAsyncSupported(true);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return filterRegistrationBean;
    }

    /**
     * 获取filter map
     *
     */
    private Map<String, Filter> getFilterMap() {
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put(REQUEST_PATH_FILTER_NAME, new JwtFilter());
        return filterMap;
    }

    private Map<String, String> getFilterChainDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        List<ShiroPermissionProperties> permissionConfigs = shiroProperties.getPermission();
        // 获取自定义权限路径配置集合
        log.debug("permissionConfigs:{}", JSON.toJSONString(permissionConfigs));
        if (CollectionUtils.isNotEmpty(permissionConfigs)) {
            for (ShiroPermissionProperties permissionConfig : permissionConfigs) {
                String url = permissionConfig.getUrl();
                String[] urls = permissionConfig.getUrls();
                String permission = permissionConfig.getPermission();
                if (StringUtils.isBlank(url) && ArrayUtils.isEmpty(urls)) {
                    throw new RuntimeException("shiro permission config 路径配置不能为空");
                }
                if (StringUtils.isBlank(permission)) {
                    throw new RuntimeException("shiro permission config permission不能为空");
                }
                if (StringUtils.isNotBlank(url)) {
                    filterChainDefinitionMap.put(url, permission);
                }
                if (ArrayUtils.isNotEmpty(urls)) {
                    for (String urlString : urls) {
                        filterChainDefinitionMap.put(urlString, permission);
                    }
                }
            }
        }
        //  filterChainDefinitionMap.put("/**", "anon");
        log.debug("filterChainMap:{}", JSON.toJSONString(filterChainDefinitionMap));
        // 添加默认的filter
        return filterChainDefinitionMap;
    }
}
