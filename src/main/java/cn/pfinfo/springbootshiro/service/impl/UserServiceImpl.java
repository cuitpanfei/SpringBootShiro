/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service.impl;

import cn.pfinfo.springbootshiro.dao.interf.IUserDao;
import cn.pfinfo.springbootshiro.entiry.User;
import cn.pfinfo.springbootshiro.service.IRoleService;
import cn.pfinfo.springbootshiro.service.IUserService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by qiaoshuang on 2017/1/4.
 */
@Service
@Log4j
public class UserServiceImpl implements IUserService {

    @Resource
    private IRoleService roleService;

    @Resource
    private IUserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public User findByUserName(String username) {
        log.info("UserServiceImpl.findByUsername()");
        User user = userDao.findbyUserName(username);
        user.setRoleList(roleService.getRolesWithPermissionByUserId(user.getId()));
        return user;
    }
}
