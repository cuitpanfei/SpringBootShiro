package cn.pfinfo.springbootshiro.service.impl.site;

import java.util.List;

import cn.pfinfo.springbootshiro.entity.Menu;
import cn.pfinfo.springbootshiro.service.IBaseService;
import cn.pfinfo.springbootshiro.vo.TreeNode;

public interface IMenuService extends IBaseService<Menu> {
	List<TreeNode> findAll();
}
