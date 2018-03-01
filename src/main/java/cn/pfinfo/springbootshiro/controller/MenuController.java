package cn.pfinfo.springbootshiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.pfinfo.springbootshiro.service.site.IMenuService;
import cn.pfinfo.springbootshiro.vo.TreeNode;

@RequestMapping("system/menu")
public class MenuController {
	
	@Autowired
	private IMenuService menuService;
	
	@RequestMapping("findAll")
	public List<TreeNode> findAll(){
	    return this.menuService.findAll();
	  }

}
