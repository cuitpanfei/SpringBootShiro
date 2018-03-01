package cn.pfinfo.springbootshiro.service.impl.common;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.entity.Permission;
import cn.pfinfo.springbootshiro.service.common.ICommonService;
import cn.pfinfo.springbootshiro.service.impl.PermissionServiceImpl;

@Service
public class CommonServiceImpl extends PermissionServiceImpl implements ICommonService {

	public List<Permission> findFilterChain() {
		return permissionDao.findAll(new Specification<Permission>() {
			
			@Override
			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("resourceType").as(String.class), "url");
				query.where(p1);
				return query.getRestriction();
			}
		});
	}

}
