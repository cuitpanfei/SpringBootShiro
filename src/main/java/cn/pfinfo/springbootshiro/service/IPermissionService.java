/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service;

import java.util.List;

import cn.pfinfo.springbootshiro.entiry.Permission;

/**
 * Created by qiaoshuang on 2017/1/5.
 */
public interface IPermissionService {

    List<Permission> getPermissionsByRoleId(Long roleId);
}
