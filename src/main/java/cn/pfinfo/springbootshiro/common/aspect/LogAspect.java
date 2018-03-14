package cn.pfinfo.springbootshiro.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.pfinfo.springbootshiro.common.annotation.Log;
import cn.pfinfo.springbootshiro.common.config.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.common.domain.LogDO;
import cn.pfinfo.springbootshiro.common.service.LogService;
import cn.pfinfo.springbootshiro.common.util.HttpContextUtils;
import cn.pfinfo.springbootshiro.common.util.IPUtils;
import cn.pfinfo.springbootshiro.common.util.JSONUtils;
import cn.pfinfo.springbootshiro.entity.User;

@Aspect
@Component
public class LogAspect {

    @Autowired
    LogService logService;


    @Pointcut("@annotation(cn.pfinfo.springbootshiro.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogDO sysLog = new LogDO();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
        	String params = JSONUtils.beanToJson(args[0]);
        	int end = params.length()>4999?4999:params.length();
            params = params.substring(0, end);
            sysLog.setParams(params);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户
        User currUser = ShiroUtil.getCurrentUser();
        if (null == currUser) {
        	sysLog.setUserId(-1L);
            sysLog.setUsername("获取用户信息为空");
        } else {
            sysLog.setUserId(ShiroUtil.getCurrentUserId());
            sysLog.setUsername(ShiroUtil.getCurrentUserName());
        }
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        sysLog.setGmtCreate(date);
        // 保存系统日志
        logService.save(sysLog);
    }
}
