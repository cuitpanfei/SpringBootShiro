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
 * 权限实体类
 *
 * Created by qiaoshuang on 2017/1/4.
 */
@Entity
@Table
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = -8127896221690211473L;

    // 主键
    private long id;

    // 名称
    private String name;

    // 资源类型，[menu|button]
    private String resourceType;

    // 资源路径
    private String url;

    // 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private String permission;

    // 父编号
    private Long parentId;

    // 父编号列表
    private String parentIds;

    private Boolean available = Boolean.FALSE;

    private List<Role> roles;

}
