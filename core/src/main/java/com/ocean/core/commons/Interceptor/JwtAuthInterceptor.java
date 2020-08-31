package com.ocean.core.commons.Interceptor;

import com.ocean.core.commons.handler.BaseContextHandler;
import com.ocean.core.commons.jwt.JwtUtil;
import com.ocean.core.properties.ShiroPermissionProperties;
import com.ocean.core.properties.ShiroProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    ShiroProperties shiroProperties;

    @Autowired
    JwtUtil jwtUtil;

    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("Content-Type：" + request.getContentType());
        log.info("访问路径 :" + request.getRequestURI());
        request.setCharacterEncoding("utf-8");
        //添加响应参数 允许其他域名访问
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        response.setCharacterEncoding("utf-8");
        checkNeedLogin(request.getRequestURI(), request.getHeader("token"));
        return true;
    }

    private void checkNeedLogin(String requestUrl, String token) {
        //找到不需要验证token的
        List<String> noCheckList = shiroProperties.getPermission().stream()
                .filter(e -> e.getPermission().equals("anno")).map(e -> Arrays.asList(e.getUrls())).flatMap(List::stream).collect(Collectors.toList());
        noCheckList.forEach(e -> {
            Pattern compile = Pattern.compile(e);
            Matcher matcher = compile.matcher(requestUrl);
            if (!matcher.find()) {
                Assert.notNull(token, "请求token过期或不存在");
            }
        });
        if (StringUtils.isNotBlank(token)) {
            String id = jwtUtil.getUserId(token);
            //把请求头携带的token放到当前线程中去
            BaseContextHandler.setCurrentUser(id);
        }
    }
}
