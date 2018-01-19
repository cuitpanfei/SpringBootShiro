package cn.pfinfo.springbootshiro.dao.interf;

import cn.pfinfo.springbootshiro.dao.base.IBaseDao;
import cn.pfinfo.springbootshiro.entiry.Permission;

import java.util.List;

/**
 * Created by panfei on 2018/1/19.
 */
public interface IPermissionDao extends IBaseDao<Permission,Long> {

    /**
     * 通过角色id获取该角色的权限集合
     * @param roleId 角色id
     * @return 该角色的权限集合
     */
    List<Permission> getByRoleId(Long roleId);
}
