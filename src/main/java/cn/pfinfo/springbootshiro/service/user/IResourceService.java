package cn.pfinfo.springbootshiro.service.user;

import java.util.List;

import cn.pfinfo.springbootshiro.entity.Resource;
import cn.pfinfo.springbootshiro.service.IBaseService;

public interface IResourceService extends IBaseService<Resource> {

	List<Resource> findAll();

}
