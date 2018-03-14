package cn.pfinfo.springbootshiro.common.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import cn.pfinfo.springbootshiro.common.config.Constant;
import cn.pfinfo.springbootshiro.common.config.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.common.domain.LogDO;
import cn.pfinfo.springbootshiro.common.service.LogService;
import cn.pfinfo.springbootshiro.common.util.HttpServletUtils;
import cn.pfinfo.springbootshiro.common.util.Result;
import cn.pfinfo.springbootshiro.entity.User;
import lombok.extern.log4j.Log4j;

/**
 * 异常处理器
 */
@RestControllerAdvice
@Log4j
public class BDExceptionHandler {
    @Autowired
    LogService logService;

    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        if (HttpServletUtils.jsAjax(request)) {
            return Result.error(403, "未授权");
        }
        return new ModelAndView("error/403");
    }


    @ExceptionHandler({Exception.class})
    public Object handleException(Exception e, HttpServletRequest request) {
        LogDO logDO = new LogDO();
        logDO.setGmtCreate(new Date());
        logDO.setOperation(Constant.LOG_ERROR);
        logDO.setMethod(request.getRequestURL().toString());
        logDO.setParams(e.toString());
        User current = ShiroUtil.getCurrentUser();
        if(null!=current){
            logDO.setUserId(current.getId());
            logDO.setUsername(current.getUserName());
        }
        logService.save(logDO);
        log.error(e.getMessage(), e);
        if (HttpServletUtils.jsAjax(request)) {
            return Result.error(500, "服务器错误，请联系管理员");
        }
        return new ModelAndView("error/500");
    }
}
