/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pfinfo.springbootshiro.configuration.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.entity.Schedule;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.user.IScheduleService;
import cn.pfinfo.springbootshiro.service.user.IUserService;
import cn.pfinfo.springbootshiro.util.PageImplWrapper;
import cn.pfinfo.springbootshiro.vo.user.UserInfoVo;

/**
 * Created by panfei on 2018/1/4.
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IScheduleService scheduleService;
	/**
	 * 查询所用用户
	 * @return
	 */
    @GetMapping("/userList")
	public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
			@RequestParam(value="name",required=false,defaultValue="") String name,
			Model model) {
	 
		Pageable pageable = new PageRequest(pageIndex, pageSize,Sort.Direction.ASC, "id");  
		Page<User> page = userService.listUsersByNameLike(name, pageable);
		List<User> list = page.getContent();	// 当前所在页面数据列表
		PageImplWrapper<User> mypage = PageImplWrapper.copy(page);
		model.addAttribute("page", mypage);
		model.addAttribute("userList", list);
		return new ModelAndView("user/users");
	}

    /**
     * 用户添加
     *
     * @return
     */
    @GetMapping("/userAdd")
    public String userInfoAdd(){
        return "user/userAdd";
    }
    @PostMapping("/userAdd")
    public String userInfoAdd(User user,Model model){
    	model.addAttribute("msg", user.toString());
      return "user/userAdd";
    }
    /**
     * 用户删除
     *
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("user:del") // 权限管理
    public String userDel(){
        return "userDel";
    }
    /**
     * 用户详情
     *
     * @return
     */
    @GetMapping("/info")
    public String userinfo(HttpServletRequest request,Model model,Long id){
    	UserInfoVo userInfoVo;
    	User suser = (User)request.getSession().getAttribute("userinfo");
    	if(id==null||suser.getId()==id){
    		userInfoVo = new UserInfoVo(suser);
    	}else{
    		User temp = userService.getOne(id);
    		userInfoVo = new UserInfoVo(temp);
    	}
    	 
    	model.addAttribute("userinfo", userInfoVo);
        return "user/userinfo";
    }
    /**
     * 用户中心
     *
     * @return
     */
    @GetMapping({"/","/personal"})
    public String userMain(String pageNumber,Model model){
    	model.addAttribute("currentPageAllAppoint",pageNumber);
    	return "user/usermain";
    }
    
    @GetMapping("/enevt/getone")
    @ResponseBody
	public Schedule getOne(Long id){
		return scheduleService.getOne(id);
	}

	@GetMapping("/enevt/getPageEvents")
	@ResponseBody
	public PageImplWrapper<Schedule> getEvents(Long pageSize,Long pageIndex,HttpServletResponse response){
		Long id = (Long)ShiroUtil.getSession().getAttribute("userSessionId");
		if(null==id){
			return null;
		}
		int pageIntIndex =(int) (pageIndex==null?0:pageIndex);
		int pageIntSize = (int) (pageSize==null?10:pageSize);
		Pageable page = new PageRequest(pageIntIndex, pageIntSize,Sort.Direction.ASC, "scheduleid");
		Page<Schedule> result = scheduleService.getEventsByUserId(id,page);
		return PageImplWrapper.copy(result);
	}
	
	@GetMapping("/enevt/getEvents")
	@ResponseBody
	public List<Schedule> getEvents(){
		Long id = (Long)ShiroUtil.getSession().getAttribute("userSessionId");
		if(id==null){
			return new LinkedList<Schedule>();
		}
		List<Schedule> result = scheduleService.getEventsByUserId(id);
		return result;
	}

}
