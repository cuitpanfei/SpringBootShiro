package cn.pfinfo.springbootshiro.dao.interf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.pfinfo.springbootshiro.dao.base.ICommonDao;
import cn.pfinfo.springbootshiro.entity.Role;

/**
 * Created by panfei on 2018/1/19.
 */
@Repository
public interface IRoleDao extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role>,ICommonDao<Role,Long> {
}
