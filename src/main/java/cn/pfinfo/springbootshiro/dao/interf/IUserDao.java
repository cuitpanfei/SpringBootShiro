package cn.pfinfo.springbootshiro.dao.interf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.pfinfo.springbootshiro.dao.base.ICommonDao;
import cn.pfinfo.springbootshiro.dao.exception.DaoException;
import cn.pfinfo.springbootshiro.entity.User;

/**
 * Created by panfei on 2018/1/19.
 */
@Repository
public interface IUserDao extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>, ICommonDao<User,Long> {

    /**
     * 通过用户名查询一个用户实体
     * @param username 用户名
     * @return 实体
     * @throws DaoException 
     */
//    User findByUserName(String username) throws DaoException;
    User findByUserName(String username);
}
