package cn.pfinfo.springbootshiro.dao.impl;

import cn.pfinfo.springbootshiro.dao.interf.IRoleDao;
import cn.pfinfo.springbootshiro.entiry.Role;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by panfei on 2018/1/19.
 */
@Component
@Log4j
public class RoleDaoImpl implements IRoleDao {
    /**
     * 保存一个实体
     *
     * @param entity
     */
    @Override
    public void save(Role entity) {

    }

    /**
     * 更新一个实体
     *
     * @param entity
     */
    @Override
    public void update(Role entity) {

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
    public Role get(Long aLong) {
        return null;
    }

    /**
     * 通过多个id查询并返回实体集合
     *
     * @param longs
     */
    @Override
    public List<Role> find(Long... longs) {
        return null;
    }
}
