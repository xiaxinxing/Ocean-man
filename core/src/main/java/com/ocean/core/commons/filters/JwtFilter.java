package com.ocean.core.commons.filters;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ocean.core.commons.jwt.JwtUtil;
import com.ocean.core.properties.ShiroProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtFilter implements Filter {

    @Autowired
    ShiroProperties shiroProperties;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    private boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        String requestURI = request.getRequestURI();

        //检测token是否正常
        if (StringUtils.isBlank(token)) {
            return false;
        }
        String id = jwtUtil.getUserId(token);
        String userName = jwtUtil.getUserName(token);
        return jwtUtil.verify(token, id, userName);
    }
}
