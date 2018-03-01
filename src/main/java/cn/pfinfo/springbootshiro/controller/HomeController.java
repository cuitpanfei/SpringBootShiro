/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.controller;

import java.security.AccessControlException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pfinfo.springbootshiro.configuration.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.util.Constatns;
import cn.pfinfo.springbootshiro.util.VerifyCodeUtils;
import lombok.extern.log4j.Log4j;

/**
 * Created by panfei on 2016/12/30.
 */
@Controller
@Log4j
public class HomeController {

    @RequestMapping({"/", "index"})
    public ModelAndView index(Model model, Map<String, Object> map, @ModelAttribute("user") User user) {
        model.addAttribute("count_new", "1");
        model.addAttribute("count_hot", "10");
        ModelAndView view = new ModelAndView("index").addAllObjects(model.asMap());

        return view;
    }

    @RequestMapping(value = "/loginOrRegister", method = RequestMethod.GET)
    public String login() {
    	if(ShiroUtil.isCurrentUser()){
    		return "redirect:/";
    	}
        return "loginOrRegister";
    }

    @RequestMapping(value = "/loginOrRegister", method = RequestMethod.POST)
    public String login(HttpServletRequest request, User user,Model model) {
    	try{
    		chickCode(request);
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
    private boolean chickCode(HttpServletRequest request) throws AccessControlException{
    	String code = (String) ShiroUtil.getSession().getAttribute(Constatns.VERYFYCODE);
    	if(code == null){
    		throw new AccessControlException("验证码错误");
    	}
    	return code == (String)request.getAttribute("jcaptchaCode");
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
    	ShiroUtil.getSubject().logout();
    	request.getSession().invalidate();
    	return "/index";
    }
    @GetMapping(value = "/search")
    @ResponseBody
    public Model search(Model model,HttpServletRequest request) {
    	ModelAndView mv = new ModelAndView();
    	mv.setStatus(HttpStatus.OK);
    	String searchValue = request.getParameter("key");
    	String[] searchKey = searchValue.indexOf("+")==-1?null:request.getParameter("key").split("+");
    	model.addAttribute("keys",searchKey==null?searchValue:searchKey);
		mv.addAllObjects(model.asMap());
    	return model;
    }
    @GetMapping("getCode")
    public void getGifCode(HttpServletResponse response,HttpServletRequest request){
    	try { 
    		response.setHeader("Pragma", "No-cache");  
            response.setHeader("Cache-Control", "no-cache");  
            response.setDateHeader("Expires", 0);  
            response.setContentType("image/gif");  
    		String w = request.getParameter("width");
    		String h = request.getParameter("height");
    		String valueCode = VerifyCodeUtils.generateVerifyCode(4);
    		request.getSession().setAttribute(Constatns.VERYFYCODE, valueCode.toLowerCase()); 
    		VerifyCodeUtils.outputImage(Integer.valueOf(w), Integer.valueOf(h), response.getOutputStream(), valueCode);
    		//存入Shiro会话session 
    		log.info("captcha:"+valueCode.toLowerCase()); 
    	}catch (Exception e) {
    		log.error("获取验证码异常："+e.getMessage(),e);
    	} 
    }


}
