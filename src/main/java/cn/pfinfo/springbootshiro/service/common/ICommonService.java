package cn.pfinfo.springbootshiro.service.common;

import java.util.List;

import cn.pfinfo.springbootshiro.entity.Permission;
import cn.pfinfo.springbootshiro.service.site.IResourceService;
import cn.pfinfo.springbootshiro.service.user.IPermissionService;

public interface ICommonService extends IPermissionService,IResourceService{


	List<Permission> findFilterChain();

}
