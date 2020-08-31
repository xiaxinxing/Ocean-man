package com.ocean.sys.log.interceptor;

import com.ocean.sys.log.entity.SysLog;
import com.ocean.sys.log.mapper.SysLogMapper;
import com.ocean.sys.log.properties.LogProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    LogProperties logProperties;

    @Autowired
    SysLogMapper logMapper;

    private String enable = "1";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断记录不记录日志
        if (enable.equals(logProperties.getIsEnable())) {
            //匹配前缀
            String requestURI = request.getRequestURI();
            Pattern urlCompile = Pattern.compile(logProperties.getUrlPrefix());
            if (urlCompile.matcher(requestURI).find()) {
                SysLog log = SysLog.builder().ip(getRealIpAddr(request)).requestTime(LocalDate.now())
                        .url(requestURI).requestBody("").build();
                logMapper.insert(log);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String getRealIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            ip = ip.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
