package cn.pfinfo.springbootshiro.dao.interf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.pfinfo.springbootshiro.dao.base.ICommonDao;
import cn.pfinfo.springbootshiro.entity.Organization;

@Repository
public interface IOrganizationDao extends JpaRepository<Organization, Long>,JpaSpecificationExecutor<Organization>, ICommonDao<Organization, Long> {

}
