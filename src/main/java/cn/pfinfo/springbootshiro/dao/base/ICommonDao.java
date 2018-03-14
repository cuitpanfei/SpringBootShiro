package cn.pfinfo.springbootshiro.dao.base;

import java.io.Serializable;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

/**
 * 申明式接口
 * Created by panfei on 2018/1/19.
 */
public interface ICommonDao<T,ID extends Serializable>{
	
	default public <Z> Specification<Z> getSpec(String name,String value){
		Specification<Z> spec = (root,query,cb)->{
			Predicate p1=cb.equal(root.get(name).as(String.class), value);
			query.where(p1);
			return query.getRestriction();
		}; 
		return spec;
	}
}
