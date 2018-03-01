/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.dao.interf.IPermissionDao;
import cn.pfinfo.springbootshiro.entity.Permission;
import cn.pfinfo.springbootshiro.service.user.IPermissionService;

/**
 * Created by panfei on 2017/1/5.
 */
@Service("permissionService")
@Transactional  
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    protected IPermissionDao permissionDao;

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        return null;
    }

	@Override
	public Permission getOne(Long id) {
		return permissionDao.findOne(id);
	}
}
