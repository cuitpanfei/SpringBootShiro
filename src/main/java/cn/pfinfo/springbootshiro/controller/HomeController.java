/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.controller;

import java.security.AccessControlException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pfinfo.springbootshiro.common.annotation.Log;
import cn.pfinfo.springbootshiro.common.config.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.common.util.Constatns;
import cn.pfinfo.springbootshiro.common.util.HttpContextUtils;
import cn.pfinfo.springbootshiro.common.util.VerifyCodeUtils;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.user.IUserService;
import lombok.extern.log4j.Log4j;

/**
 * Created by panfei on 2016/12/30.
 */
@Controller
@Log4j
public class HomeController {
	
	@Autowired
	private IUserService userService;

	@Log("请求访问主页")
    @RequestMapping({"/", "index"})
    public ModelAndView index(Model model) {
        ModelAndView view = new ModelAndView("index").addAllObjects(model.asMap());
        return view;
    }
	@Log("请求访问登陆注册页")
    @RequestMapping(value = "/loginOrRegister", method = RequestMethod.GET)
    public String login() {
    	if(ShiroUtil.isCurrentUser()){
    		return "redirect:/";
    	}
        return "loginOrRegister";
    }

	@Log("执行登陆操作")
    @RequestMapping(value = "/loginOrRegister", method = RequestMethod.POST)
    public String login(User user,Model model) {
    	try{
    		chickCode();
    	}catch(AccessControlException e){
    		log.error(e);
    		model.addAttribute("errmsg", e.getMessage());
    		return "loginOrRegister";
    	}
    	
    	UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
    	token.setRememberMe(false);
    	try{
    		ShiroUtil.getSubject().login(token);
    	}catch (Exception e) {
    		log.error(e);
    		model.addAttribute("errmsg", e.getMessage());
    		return "loginOrRegister";
		}
		return "redirect:/";
    }
	@Log("执行注册操作")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User user,Model model) {
    	try{
    		chickCode();
    	}catch(AccessControlException e){
    		log.error(e);
    		model.addAttribute("errmsg", e.getMessage());
    		return "loginOrRegister";
    	}
    	
    	user.setPassword(ShiroUtil.passwordSimpleHash("md5", user.getPassword(), user.getSaltTo()).toString());
    	userService.save(user);
    	return login(user,model);
    }
    private boolean chickCode() throws AccessControlException{
    	String code = (String) ShiroUtil.getSession().getAttribute(Constatns.VERYFYCODE);
    	if(code == null){
    		throw new AccessControlException("验证码错误");
    	}
    	return code == (String)HttpContextUtils.getHttpServletRequest().getAttribute("jcaptchaCode");
    }
    @Log("执行退出操作")
    @RequestMapping(value = "/logout")
    public String logout() {
    	ShiroUtil.getSubject().logout();
    	HttpContextUtils.getHttpServletRequest().getSession().invalidate();
    	return "/index";
    }
    @Log("执行了搜索")
    @GetMapping(value = "/search")
    @ResponseBody
    public Model search(Model model,String key) {
    	ModelAndView mv = new ModelAndView();
    	mv.setStatus(HttpStatus.OK);
    	String[] searchKey = key.split("+");
    	model.addAttribute("keys",searchKey);
		mv.addAllObjects(model.asMap());
    	return model;
    }
    @GetMapping("getCode")
    public void getGifCode(HttpServletResponse response){
    	HttpServletRequest request;
    	try { 
    		request = HttpContextUtils.getHttpServletRequest();
    		response.setHeader("Pragma", "No-cache");  
            response.setHeader("Cache-Control", "no-cache");  
            response.setDateHeader("Expires", 0);  
            response.setContentType("image/gif");  
    		String w = request.getParameter("width");
    		String h = request.getParameter("height");
    		String valueCode = VerifyCodeUtils.generateVerifyCode(4);
    		request.getSession().setAttribute(Constatns.VERYFYCODE, valueCode.toLowerCase()); 
    		VerifyCodeUtils.outputImage(Integer.valueOf(w), Integer.valueOf(h), response.getOutputStream(), valueCode);
    		log.info("captcha:"+valueCode.toLowerCase()); 
    	}catch (Exception e) {
    		log.error("获取验证码异常："+e.getMessage(),e);
    	} 
    }


}
