package cn.pfinfo.springbootshiro.dao.interf;

import cn.pfinfo.springbootshiro.dao.base.IBaseDao;
import cn.pfinfo.springbootshiro.dao.impl.UserDaoImpl;
import cn.pfinfo.springbootshiro.entiry.User;

/**
 * Created by panfei on 2018/1/19.
 */
public interface IUserDao extends IBaseDao<User,Long> {

    /**
     * 通过用户名查询一个用户实体
     * @param username 用户名
     * @return 实体
     */
    User findbyUserName(String username);
}
