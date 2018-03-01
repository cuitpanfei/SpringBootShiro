/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service.user;

import java.util.List;

import cn.pfinfo.springbootshiro.entity.Role;
import cn.pfinfo.springbootshiro.service.IBaseService;

/**
 * Created by panfei on 2017/1/5.
 */
public interface IRoleService extends IBaseService<Role> {

	List<Role> getRolesWithPermissionByUserId(Long id);

}
