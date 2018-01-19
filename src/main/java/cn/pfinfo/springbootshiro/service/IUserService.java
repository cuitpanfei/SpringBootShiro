/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.service;

import cn.pfinfo.springbootshiro.entiry.User;

/**
 * Created by qiaoshuang on 2017/1/4.
 */
public interface IUserService {

    User findByUserName(String username);
}
