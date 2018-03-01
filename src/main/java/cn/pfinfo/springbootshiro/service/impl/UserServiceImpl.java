package cn.pfinfo.springbootshiro.service.impl;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.pfinfo.springbootshiro.dao.exception.DaoException;
import cn.pfinfo.springbootshiro.dao.interf.IUserDao;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.user.IUserService;
import lombok.extern.log4j.Log4j;

/**
 * Created by panfei on 2018/1/4.
 */
@Transactional  
@Service("userService")
@Log4j
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public User findByUserName(String username) throws DaoException {
        log.info("UserServiceImpl.findByUsername()");
        User user = userDao.findByUserName(username);
        return user;
    }

	@Override
	public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        Page<User> userPage = userDao.findAll(new Specification<User>(){  
            @Override  
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {  
            	 Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+name+"%");  
            	 query.where(criteriaBuilder.and(p1));  
                return query.getRestriction();  
            }  
        },pageable);  
		return userPage;
	}

	@Override
	public User getOne(Long id) {
		return userDao.findOne(id);
	}

}
