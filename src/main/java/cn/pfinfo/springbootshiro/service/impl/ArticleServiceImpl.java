package cn.pfinfo.springbootshiro.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.dao.interf.IArticleDao;
import cn.pfinfo.springbootshiro.entity.Article;
import cn.pfinfo.springbootshiro.service.user.IArticleService;

@Service("articleService")
public class ArticleServiceImpl implements IArticleService {
	
	@Autowired
	private IArticleDao articleDao;

	@Override
	public Article getOne(Long id) {
		return null;
	}

	@Override
	public Page<Article> findAllByUserId(String author,Pageable pageable) {
		Page<Article> allByUserId =  articleDao.findAll(new Specification<Article>() {
			
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.like(root.get("author").as(String.class), author);
				query.where(p1);
				return query.getRestriction();
			}
		}, pageable);
		
		return allByUserId;
		
	}

	@Override
	public Page<Article> findAll(Pageable pageable) {
		return articleDao.findAll(pageable);
	}

	@Override
	public void modify(Article article) {
		article = articleDao.saveAndFlush(article);	
	}

	@Override
	public void save(Article article) {
		article = articleDao.save(article);	
	}

	@Override
	public Article findLike(Article article) {
		return articleDao.findOne(Example.of(article));
	}

}
