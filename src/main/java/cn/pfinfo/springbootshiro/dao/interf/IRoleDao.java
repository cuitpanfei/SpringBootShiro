package cn.pfinfo.springbootshiro.dao.interf;

import cn.pfinfo.springbootshiro.dao.base.IBaseDao;
import cn.pfinfo.springbootshiro.entiry.Role;

import java.util.List;

/**
 * Created by panfei on 2018/1/19.
 */
public interface IRoleDao extends IBaseDao<Role,Long> {

    /**
     * 通过用户ID 获取其所有角色集合
     * @param userId 用户ID
     * @return 用户所有角色集合
     */
    List<Role> getByUserId(Long userId);
}
