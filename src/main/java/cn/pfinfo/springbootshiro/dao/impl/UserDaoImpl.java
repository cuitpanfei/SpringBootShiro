package cn.pfinfo.springbootshiro.dao.impl;

import cn.pfinfo.springbootshiro.dao.base.ICommonDao;
import cn.pfinfo.springbootshiro.dao.interf.IUserDao;
import cn.pfinfo.springbootshiro.entiry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by panfei on 2018/1/19.
 */
@Component
public class UserDaoImpl implements IUserDao {
    @Autowired
    private ICommonDao<User,Long> baseDao;

    /**
     * 保存一个实体
     *
     * @param entity
     */
    @Override
    public void save(User entity) {
//        baseDao.save(entity);
    }

    /**
     * 更新一个实体
     *
     * @param entity
     */
    @Override
    public void update(User entity) {

    }

    /**
     * 通过主键删除一个实体
     *
     * @param id 实体的主键
     */
    @Override
    public void delete(Long id) {

    }

    /**
     * 通过id获取一个实体
     *
     * @param id 实体的id
     * @return 实体
     */
    @Override
    public User get(Long id) {
        return null;
    }

    /**
     * 通过多个id查询并返回实体集合
     *
     * @param ids
     */
    @Override
    public List<User> find(Long... ids) {
        return null;
    }

    /**
     * 通过用户名查询一个用户实体
     *
     * @param username 用户名
     * @return 实体
     */
    @Override
    public User findbyUserName(String username) {
        return null;
    }
}
