/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service.impl;

import cn.pfinfo.springbootshiro.dao.interf.IPermissionDao;
import cn.pfinfo.springbootshiro.entiry.Permission;
import cn.pfinfo.springbootshiro.service.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by qiaoshuang on 2017/1/5.
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    private IPermissionDao permissionDao;

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        return permissionDao.getByRoleId(roleId);
    }
}
