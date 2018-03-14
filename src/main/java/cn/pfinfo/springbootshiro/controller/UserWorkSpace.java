package cn.pfinfo.springbootshiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("u")
public class UserWorkSpace {
	
    /**
     * 用户中心
     * @return
     */
    @GetMapping({"/","/personal"})
    public String userMain(String pageNumber,Model model){
    	model.addAttribute("currentPageAllAppoint",pageNumber);
    	return "user/usermain";
    }
	
}
