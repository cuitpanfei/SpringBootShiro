/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.dao.interf.IRoleDao;
import cn.pfinfo.springbootshiro.entity.Role;
import cn.pfinfo.springbootshiro.service.user.IPermissionService;
import cn.pfinfo.springbootshiro.service.user.IRoleService;

/**
 * Created by panfei on 2017/1/5.
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Resource
    private IPermissionService permissionService;

    @Resource
    private IRoleDao roleDao;

    @Override
    public List<Role> getRolesWithPermissionByUserId(Long userId) {
        List<Role> roles = null;
        return roles;
    }

	@Override
	public Role getOne(Long id) {
		return roleDao.findOne(id);
	}
}
