/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.ModelAndView;

import cn.pfinfo.springbootshiro.common.exception.BDException;
import cn.pfinfo.springbootshiro.common.util.page.PageImplWrapper;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.user.IUserService;

/**
 * Created by panfei on 2018/1/4.
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	
	/**
     * 用户详情
     * @return
     * @throws ControllerException 
     */
    @GetMapping("/info")
    public String userinfo(HttpServletRequest request,Model model,Long id) throws BDException{
    	if(id==null){
    		throw new BDException("用户id为空");
    	}
        return "user/userinfo";
    }
    
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
      return "user/userAdd";
    }
    /**
     * 用户删除
     * @return
     */
    @RequestMapping("/userDel")
    public String userDel(){
        return "userDel";
    }
}
