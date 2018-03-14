package cn.pfinfo.springbootshiro.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.pfinfo.springbootshiro.common.annotation.Log;
import cn.pfinfo.springbootshiro.common.config.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.common.util.Result;
import cn.pfinfo.springbootshiro.entity.Schedule;
import cn.pfinfo.springbootshiro.service.user.IScheduleService;

@RestController
public class ScheduleController {
	
	@Autowired
	private IScheduleService scheduleService;
	
	@Log("获取日程")
	@RequestMapping("/enevt/getEvents")
	public Result getAll(String username){
		Map<String,Object> map = new HashMap<>();
		Long userid = ShiroUtil.getCurrentUserId();
		List<Schedule> list = new ArrayList<>();
		try{
			list = scheduleService.getEventsByUserId(userid);
		}catch(Exception e){
			if(userid!=null){
				return Result.error(e.getMessage());
			}
		}
		map.put("count", list.size());
		map.put("data", list);
		return userid==null?Result.error("请先登录"):Result.ok(map);
		
	}
	@RequestMapping("/{username}/enevt/getPageEvents")
	public Result getAll(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model){
		Long id = (Long)ShiroUtil.getSession().getAttribute("userSessionId");
		if(null==id){
			return null;
		}
		Pageable page = new PageRequest(pageIndex, pageSize,Sort.Direction.ASC, "scheduleid");
		Page<Schedule> list = scheduleService.getEventsByUserId(id,page);
		Result result = Result.layuiPageOk(list.getSize(), list.getContent());
		return result;
	}
	
	@GetMapping("/{username}/enevt/getone")
	public Schedule getOne(Long id){
		return scheduleService.getOne(id);
	}
}
