/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service.impl;

import cn.pfinfo.springbootshiro.dao.interf.IRoleDao;
import cn.pfinfo.springbootshiro.entiry.Role;
import cn.pfinfo.springbootshiro.service.IPermissionService;
import cn.pfinfo.springbootshiro.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by qiaoshuang on 2017/1/5.
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    private IPermissionService permissionService;

    @Resource
    private IRoleDao roleDao;

    @Override
    public List<Role> getRolesWithPermissionByUserId(Long userId) {
        List<Role> roles = roleDao.getByUserId(userId);
        for (Role role : roles) {
            role.setPermissions(permissionService.getPermissionsByRoleId(role.getId()));
        }
        return roles;
    }
}
