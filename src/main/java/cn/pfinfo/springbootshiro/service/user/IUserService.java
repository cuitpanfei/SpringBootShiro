/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.pfinfo.springbootshiro.dao.exception.DaoException;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.IBaseService;

/**
 * Created by panfei on 2018/1/4.
 */
public interface IUserService extends IBaseService<User>{

    User findByUserName(String username) throws DaoException;

	Page<User> listUsersByNameLike(String name, Pageable pageable);
}
