package cn.pfinfo.springbootshiro.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.pfinfo.springbootshiro.entity.Article;
import cn.pfinfo.springbootshiro.service.IBaseService;

public interface IArticleService  extends IBaseService<Article>  {

	Page<Article> findAllByUserId(String authorName, Pageable pageable);

	Page<Article> findAll(Pageable pageable);

	void modify(Article article);

	void save(Article article);

	Article findLike(Article article);

}
