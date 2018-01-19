/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.pfinfo.springbootshiro.entiry;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * 系统角色实体类
 *
 * Created by qiaoshuang on 2017/1/3.
 */
@Entity
@Table
@Data
public class Role implements Serializable{

    private static final long serialVersionUID = 2231705667121768303L;

    // 编号
    private Long id;

    // 角色标识 程序中判断使用,如"admin",这个是唯一的
    private String role;

    // 角色描述,UI界面显示使用
    private String description;

    // 是否可用,如果不可用将不会添加给用户
    private Boolean available = Boolean.FALSE;

    //角色 -- 权限关系：多对多关系
    private List<Permission> permissions;

    // 用户 - 角色关系定义;
    private List<User> userInfos;
}
