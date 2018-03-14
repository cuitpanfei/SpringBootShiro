package cn.pfinfo.springbootshiro.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import cn.pfinfo.springbootshiro.dao.interf.IArticleDao;
import cn.pfinfo.springbootshiro.entity.Article;
import cn.pfinfo.springbootshiro.service.user.IArticleService;
import lombok.extern.log4j.Log4j;


@Log4j
@Service("articleService")
public class ArticleServiceImpl implements IArticleService {

	
	@Autowired
	private IArticleDao articleDao;

	@Override
	public Article getOne(Long id) {
		return articleDao.findOne(id);
	}

	@Override
	public Page<Article> findAllByUserName(String author,Pageable pageable) {
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
	public Article modify(Article article) {
		return articleDao.saveAndFlush(article);	
	}

	@Override
	public Article save(Article article) {
		return articleDao.save(article);	
	}

	@Override
	public Article findLike(Article article) {
		return articleDao.findOne(Example.of(article));
	}
	

	
    @Override
    public String staticBlog(String author,HttpServletRequest request, Map<String,Object> map) throws Exception {
    
        String compon = (String) map.get("localPath");
        String savePath = compon;
        // 临时文件目录
        String tempPath =savePath+ "temp";
        SimpleDateFormat sdf = new SimpleDateFormat((String) map.get("fomat"));
        String ymd = sdf.format(new Date());
        String htmlDate="html/"+author+"/"+ ymd + "/";
        savePath += htmlDate;
        
        //创建文件夹
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        tempPath += "/" + ymd + "/";
        //创建临时文件夹
        File dirTempFile = new File(tempPath);
        if (!dirTempFile.exists()) {
            dirTempFile.mkdirs();
        }
    	
    	//构造模板引擎
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/article/");//模板所在目录，相对于当前classloader的classpath。
        resolver.setSuffix((String) map.get("templateSuffix"));//模板文件后缀
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        
        // 生成实际存储的真实文件名
        String realName = ((Article)map.get("article")).getId() + ".html";
        // 文件存放的真实路径
        String realPath = savePath + "/" + realName;
        
        FileWriter write;
		try {
			write = new FileWriter(realPath);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("页面静态化异常", e);
			throw new Exception("页面静态化发生异常，请重试");
		}
        Context context = new Context();
        context.setVariables(map);
        templateEngine.process("template", context , write);
		return htmlDate+ realName;

    }

	@Override
	public List<Article> findByIdIn(Long[] aids) {
		List<Long> ids = Arrays.asList(aids);
		return articleDao.findAll(ids);
	}
}
