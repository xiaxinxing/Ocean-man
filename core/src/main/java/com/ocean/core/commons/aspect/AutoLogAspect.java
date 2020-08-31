package com.ocean.core.commons.aspect;

import com.ocean.core.commons.annotation.AutoLog;
import com.ocean.core.commons.constant.CommonConstant;
import com.ocean.core.module.sys.entity.SysLog;
import com.ocean.core.module.sys.entity.User;
import com.ocean.core.module.sys.service.ILogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class AutoLogAspect {
    @Autowired
    private ILogService sysLogService;

    @Pointcut("@annotation(com.ocean.core.commons.annotation.AutoLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        AutoLog autoLog = method.getAnnotation(AutoLog.class);
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        if (null != autoLog) {
            sysLog.setLogContent(autoLog.value());
            sysLog.setLogType(autoLog.logType());
            //设置操作类型
            if (0 == autoLog.operateType()) {
                sysLog.setOperateType(autoLog.operateType());
            } else if (sysLog.getLogType() == CommonConstant.LOG_TYPE_2) {
                sysLog.setOperateType(getOperateType(methodName, autoLog.operateType()));
            }
        }
        //获取请求request
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //设置IP地址
        sysLog.setIp(getIpAddr(request));

        //获取登录用户信息
        User sysUser = (User) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            sysLog.setUserName(sysUser.getLoginName());
            sysLog.setUserId(sysUser.getId());
        }
        //耗时
        sysLog.setCostTime((int) time);
        //保存系统日志
        sysLogService.save(sysLog);
    }

    /**
     * 获取操作类型
     */
    private int getOperateType(String methodName, int operateType) {
        if (operateType > 0) {
            return operateType;
        }
        if (methodName.startsWith("list")||methodName.startsWith("find")||methodName.startsWith("select")||methodName.startsWith("get")) {
            return CommonConstant.OPERATE_TYPE_1;
        }
        if (methodName.startsWith("add")||methodName.startsWith("save")||methodName.startsWith("insert")) {
            return CommonConstant.OPERATE_TYPE_2;
        }
        if (methodName.startsWith("edit")||methodName.startsWith("update")) {
            return CommonConstant.OPERATE_TYPE_3;
        }
        if (methodName.startsWith("delete")||methodName.startsWith("remove")) {
            return CommonConstant.OPERATE_TYPE_4;
        }
        if (methodName.startsWith("import")) {
            return CommonConstant.OPERATE_TYPE_5;
        }
        if (methodName.startsWith("export")) {
            return CommonConstant.OPERATE_TYPE_6;
        }
        return CommonConstant.OPERATE_TYPE_1;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ip = "";
        }


        return ip;
    }
}
