package cn.pfinfo.springbootshiro.service.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.pfinfo.springbootshiro.entity.Article;
import cn.pfinfo.springbootshiro.service.IBaseService;

public interface IArticleService  extends IBaseService<Article>  {

	Page<Article> findAllByUserName(String authorName, Pageable pageable);

	Page<Article> findAll(Pageable pageable);

	Article modify(Article article);

	Article save(Article article);

	Article findLike(Article article);

	/**
	 * 博客页面静态化
	 * @param path 模板路径
	 * @param author   文章作者
	 * @param request
	 * @param map
	 * @return 静态化页面url;
	 * @throws Exception
	 */
	String staticBlog(String author, HttpServletRequest request, Map<String, Object> map) throws Exception;

	List<Article> findByIdIn(Long[] aid);

}
