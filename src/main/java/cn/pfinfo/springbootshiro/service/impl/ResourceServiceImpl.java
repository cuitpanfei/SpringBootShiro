package cn.pfinfo.springbootshiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.dao.interf.IResourceDao;
import cn.pfinfo.springbootshiro.entity.Resource;
import cn.pfinfo.springbootshiro.service.user.IResourceService;

@Service
public class ResourceServiceImpl implements IResourceService {

	@Autowired
	private IResourceDao resourceDao;
	@Override
	public Resource getOne(Long id) {
		return resourceDao.getOne(id);
	}
	@Override
	public List<Resource> findAll() {
		return resourceDao.findAll();
	}

}
