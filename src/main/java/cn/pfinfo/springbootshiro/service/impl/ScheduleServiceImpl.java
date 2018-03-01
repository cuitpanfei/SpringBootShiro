package cn.pfinfo.springbootshiro.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.dao.interf.IScheduleDao;
import cn.pfinfo.springbootshiro.entity.Schedule;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.user.IScheduleService;

@Service("scheduleService")
public class ScheduleServiceImpl implements IScheduleService {
	@Autowired
	private IScheduleDao scheduledao;

	public Schedule getOne(Long id) {
		return scheduledao.findOne(id);
	}

	@Override
	public Page<Schedule> getEventsByUserId(Long userid, Pageable pageable) {
		Page<Schedule> all = scheduledao.findAll(new Specification<Schedule>() {
			@Override
			public Predicate toPredicate(Root<Schedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Join<Schedule,User> userJoin = root.join(root.getModel().getSingularAttribute("setUser", User.class),JoinType.LEFT);
//				Predicate p1 = cb.equal(userJoin.get("id").as(Long.class), userid);
				Path<User> user = root.get("user");
				Predicate p1 = cb.equal(user.get("id").as(Long.class), userid);
				query.where(p1);
				return query.getRestriction();
			}
		},pageable);
		return all;
	}

	@Override
	public List<Schedule> getEventsByUserId(Long userid) {
		List<Schedule> all = scheduledao.findAll(new Specification<Schedule>() {
			@Override
			public Predicate toPredicate(Root<Schedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<User> user = root.get("user");
				Predicate p1 = cb.equal(user.get("id").as(Long.class), userid);
				query.where(p1);
				return query.getRestriction();
			}
		});
		return all;
	}

}
