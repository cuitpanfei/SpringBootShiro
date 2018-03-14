package cn.pfinfo.springbootshiro.controller.blog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.pfinfo.springbootshiro.common.config.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.common.util.Result;
import cn.pfinfo.springbootshiro.common.util.page.PageImplWrapper;
import cn.pfinfo.springbootshiro.entity.Article;
import cn.pfinfo.springbootshiro.entity.ArticleLabel;
import cn.pfinfo.springbootshiro.entity.ArticleStaticInfo;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.user.IArticleLabelService;
import cn.pfinfo.springbootshiro.service.user.IArticleService;
import cn.pfinfo.springbootshiro.service.user.IUserService;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class ArticleController {	
	
	@Autowired
    private Environment env;
	
	@Autowired
	private IArticleService articleService;
	@Autowired
	private IArticleLabelService articleLabelService;
	@Autowired
	private IUserService userService;
	
	
	/**
	 * @param username 文章作者名
	 * @param aid 文章id
	 * @param model
	 * @param article 修改后的文章实体
	 * @return
	 */
	@PostMapping("/u/{username}/article/edit")
	public String edit(@PathVariable String username,Long aid,Model model,Article article){
		User user = (User)ShiroUtil.getSession().getAttribute("userinfo");
		if(username.equals(user.getName())){
			Article temp = articleService.getOne(aid);
			String[] label = article.getLabel().split(",");
			List<String> labels =  Arrays.asList(label);
			articleLabelService.saveAllByStr(labels);
			temp.setLabel(article.getLabel());
			temp.setOriginal(1);
			temp.setUdescription(article.getUdescription());
			temp.setDescription(article.getDescription());
			temp.setUpdateTime(new Date());
			articleService.save(article);
			return "article/create";
		}
		return "redirect:/";
	}
	/**
	 * 
	 * @param username 文章作者
	 * @param aid      文章id
	 * @param model
	 * @return
	 */
	@PostMapping("/u/{username}/article/delete")
	@ResponseBody
	public Result del(@PathVariable String username,Long aid,Model model){
		if(!username.equals(ShiroUtil.getCurrentUserName())){
			return Result.error("你没有相关权限");
		}
		Article article = articleService.getOne(aid);
		article.setStatus(1);
		article.setUpdateTime(new Date());
		articleService.save(article);
		return Result.ok();
	}
	
	/**
	 * 
	 * @param username 文章作者名称
	 * @param aid 文章id
	 * @param model 
	 * @return 文章修改视图
	 */
	@GetMapping("/u/{username}/article/edit")
	public String editGet(@PathVariable String username,Long aid,Model model){
		if(username.equals(ShiroUtil.getCurrentUserName())){
			Article article = new Article();
			article.setId(aid);
			article.setAuthor(username);
			if(aid!=null){
				article = articleService.findLike(article);
			}
			List<ArticleLabel> forLabel = new ArrayList<ArticleLabel>();
			forLabel.add(new ArticleLabel("java"));
			forLabel.add(new ArticleLabel("Spring Boot"));
			forLabel.add(new ArticleLabel("SpringMVC"));
			forLabel.add(new ArticleLabel("个人网站"));
			forLabel.add(new ArticleLabel("其他"));
			model.addAttribute("article",article);
			model.addAttribute("forLabel",forLabel);
			return "article/create";
		}
		return "redirect:/";
	}
	@RequestMapping("/u/{username}/article/view")
	public String view(@PathVariable String username,Long aid,Model model){
		Long userid = (Long)ShiroUtil.getSession().getAttribute("userSessionId");
		if(userid!=null){
			Article article = new Article();
			article.setId(aid);
			article.setAuthor(username);
			article = articleService.findLike(article);
			model.addAttribute("article",article);
			return "article/template";
		}
		return "redirect:/";
	}
	
	/**
	 * 文章展示类，返回视图用于查看文章详情
	 * @param username 作者名
	 * @param aid      文章id
	 * @param model
	 * @return
	 */
	@RequestMapping("/{username}/article/show")
	public String show(@PathVariable String username,Long aid,Model model){
		Article article = new Article();
		article.setId(aid);
		article.setAuthor(username);
		article = articleService.findLike(article);
		ArticleStaticInfo info = article.getStaticinfo();
		String returnStr;
		if(info==null){
			model.addAttribute("article",article);
			returnStr = "article/template";
		}else{
			model.addAttribute("url",info.getUrl());
			returnStr = "article/staticTemplate";
		}
		return returnStr;
	}
	
	/**
	 * 文章添加功能，用于新增一篇文章，新增后，转发到此文的查看视图。如果未登录将会转发到首页
	 * @param username 文章作者
	 * @param model
	 * @param article 新增文章对应的实体
	 * @return 
	 */
	@PostMapping("/u/{username}/article/add")
	public String add(@PathVariable String username,Model model, Article article){
		if(username.equals(ShiroUtil.getCurrentUserName())){
			article.setAuthor(username);
			article.setCreateTime(new Date());
			article.setStatus(0);
			article = articleService.save(article);
			model.addAttribute("addedArticle",article);
			return "redirect:/"+username+"/article/view?aid="+article.getId();
		}
		return "redirect:/";
	}
	@GetMapping("/u/{username}/article/add")
	public String addGet(@PathVariable String username,Model model){
		Long userid = (Long)ShiroUtil.getSession().getAttribute("userSessionId");
		if(userid!=null){
			return "redirect:/u/"+username+"/article/edit";
		}
		return "redirect:/";
	}
	
	@GetMapping("/getAllArticle")
	@ResponseBody
	public PageImplWrapper<Article> getAllArticle(Model model,HttpServletRequest request,
			String pageNum, String pageSize,Article article){
		int pageIntNum = Integer.parseInt(pageNum==null?"0":pageNum);
        int pageIntSize = Integer.parseInt(pageSize==null?"10":pageSize);
		Pageable pageable = new PageRequest(pageIntNum, pageIntSize,Sort.Direction.ASC, "id");
        Page<Article> page = articleService.findAll(pageable);
        System.out.println("========> Sessionid: "+request.getSession().getId());
        request.getSession().setAttribute("page", page);
		return PageImplWrapper.copy(page);
		
	}
	@GetMapping("/u/{username}/article/list")
	@ResponseBody
	public Result getUserAllArticle(@PathVariable String username,
			String pageNum, String pageSize, Article article){
		if(pageNum == null){
            pageNum="1";
        }
        int pageIntNum = Integer.parseInt(pageNum==null?"0":pageNum);
        int pageIntSize = Integer.parseInt(pageSize==null?"10":pageSize);
        User user = userService.findByUserName(username);
        Pageable pageable = new PageRequest(pageIntNum, pageIntSize,Sort.Direction.ASC, "id");
        Page<Article> page = articleService.findAllByUserName(user.getName(),pageable);
		return Result.layuiPageOk(page.getNumberOfElements(), page.getContent());
	}
	
	/**
	 * 静态化页面
	 * @param aid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/u/{username}/article/toStatic")
	@ResponseBody
	public Result staticBlog(Long aid,HttpServletRequest request) throws Exception{
		//服务器访问前缀
        String preImageDomain = env.getProperty("SERVER_PRE_URL");
		Article article = articleService.getOne(aid);
		if(article==null){
			throw new NotFoundException();
		}
		Map<String, Object> map = new HashMap<>();
		//文件保存真实目录
		map.put("localPath", env.getProperty("REAL_SAVE_PATH"));
		//文件目录生成规则
		map.put("fomat", env.getProperty("DIR_SIMPLE_DATE_FORMAT"));
		map.put("templateSuffix", env.getProperty("spring.thymeleaf.suffix"));
		map.put("article", article);
		String path = articleService.staticBlog(article.getAuthor(), request, map);
		return Result.ok().put("url", preImageDomain+path);
	}
	/**
	 * 批量静态化页面
	 * @param aid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/u/{username}/article/toStaticAll")
	@ResponseBody
	public Result staticBlog(HttpServletRequest request,Long ... ids) throws Exception{
		List<Article> articles = articleService.findByIdIn(ids);
		Map<Long,String> urls = new HashMap<>();
		articles.forEach(article->{
			Map<String, Object> map = new HashMap<>();
			map.put("article", article);
			String url;
			try {
				url = articleService.staticBlog(article.getAuthor(), request, map);
				urls.put(article.getId(), url);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		});
		
		return Result.ok().put("urls",urls);
	}
}
