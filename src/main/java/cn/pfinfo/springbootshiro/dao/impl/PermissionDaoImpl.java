package cn.pfinfo.springbootshiro.dao.impl;

import cn.pfinfo.springbootshiro.dao.interf.IPermissionDao;
import cn.pfinfo.springbootshiro.entiry.Permission;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by panfei on 2018/1/19.
 */
@Component
@Log4j
public class PermissionDaoImpl implements IPermissionDao {
    /**
     * 保存一个实体
     *
     * @param entity
     */
    @Override
    public void save(Permission entity) {

    }

    /**
     * 更新一个实体
     *
     * @param entity
     */
    @Override
    public void update(Permission entity) {

    }

    /**
     * 通过主键删除一个实体
     *
     * @param aLong 实体的主键
     */
    @Override
    public void delete(Long aLong) {

    }

    /**
     * 通过id获取一个实体
     *
     * @param aLong 实体的id
     * @return 实体
     */
    @Override
    public Permission get(Long aLong) {
        return null;
    }

    /**
     * 通过多个id查询并返回实体集合
     *
     * @param longs
     */
    @Override
    public List<Permission> find(Long... longs) {
        return null;
    }

    /**
     * 通过角色id获取该角色的权限集合
     * @param roleId 角色id
     * @return 该角色的权限集合
     */
    @Override
    public List<Permission> getByRoleId(Long roleId) {
        return null;
    }
}
