package cn.pfinfo.springbootshiro.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.pfinfo.springbootshiro.common.domain.LogDO;
import cn.pfinfo.springbootshiro.dao.base.ICommonDao;

@Repository
public interface LogDao extends JpaRepository<LogDO, Long>,JpaSpecificationExecutor<LogDO>, ICommonDao<LogDO, Long> {
	List<LogDO> findByIdIn(Long ...ids);
}
