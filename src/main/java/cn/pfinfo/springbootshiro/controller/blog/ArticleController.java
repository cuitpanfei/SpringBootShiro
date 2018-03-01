package cn.pfinfo.springbootshiro.controller.blog;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.pfinfo.springbootshiro.configuration.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.entity.Article;
import cn.pfinfo.springbootshiro.entity.User;
import cn.pfinfo.springbootshiro.service.user.IArticleService;
import cn.pfinfo.springbootshiro.service.user.IUserService;
import cn.pfinfo.springbootshiro.util.PageImplWrapper;

@Controller
public class ArticleController {
	
	@Autowired
	private IArticleService articleService;
	@Autowired
	private IUserService userService;
	
	@RequestMapping("user/article/create")
    private String showPage(){
		Long userid = (Long)ShiroUtil.getSession().getAttribute("userSessionId");
        return userid==null?"redirect:/":"article/create";
    }
	
	@GetMapping("/getArticle")
	@ResponseBody
	public Article getArticle(Article article){
		return articleService.findLike(article);
		
	}
	@PutMapping("/modifyArticle")
	@ResponseBody
	public Article modifyArticle(Model model,Article article){
		articleService.modify(article);
		return article;
	}
	@PostMapping("/creatArticle")
	@ResponseBody
	public Article creatArticle(Article article){
		articleService.save(article);
		Article after = articleService.findLike(article);
		return after;
	}
	@GetMapping("/getAllArticle")
	@ResponseBody
	public PageImplWrapper<Article> getAllArticle(Model model,HttpServletResponse response,String pageNum, String pageSize,Article article){
		 int pageIntNum = Integer.parseInt(pageNum==null?"0":pageNum);
	        int pageIntSize = Integer.parseInt(pageSize==null?"10":pageSize);
		Pageable pageable = new PageRequest(pageIntNum, pageIntSize,Sort.Direction.ASC, "id");
        Page<Article> page = articleService.findAll(pageable);
        ShiroUtil.getSession().setAttribute("page", page.getContent());
        response.setStatus(200);
		return PageImplWrapper.copy(page);
		
	}
	@GetMapping("/user/getAllArticle")
	@ResponseBody
	public PageImplWrapper<Article> getUserAllArticle(String pageNum, String pageSize, Article article){
		String id = (String) ShiroUtil.getSession().getAttribute("userSessionId");
		if(pageNum == null){
            pageNum="1";
        }
        Long parseInt = Long.parseLong(id);
        int pageIntNum = Integer.parseInt(pageNum==null?"0":pageSize);
        int pageIntSize = Integer.parseInt(pageSize==null?"10":pageSize);
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        User user = userService.getOne(parseInt);
        Pageable pageable = new PageRequest(pageIntNum, pageIntSize,Sort.Direction.ASC, "id");
        Page<Article> page = articleService.findAllByUserId(user.getName(),pageable);
		return PageImplWrapper.copy(page);
		
	}
	@GetMapping("/user/article")
	@ResponseBody
	public Article showUserArticle(Long uid,Long aid){
		return articleService.getOne(aid);
		
	}

}
