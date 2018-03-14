package cn.pfinfo.springbootshiro.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.pfinfo.springbootshiro.common.domain.LogDO;
import cn.pfinfo.springbootshiro.common.service.LogService;
import cn.pfinfo.springbootshiro.common.util.Result;

@RequestMapping("/common/log")
@Controller
public class LogController {
	@Autowired
	LogService logService;
	String prefix = "common/log";

	@GetMapping()
	String log() {
		return prefix + "/log";
	}

	/**
	 * 分页查询
	 * @param async     是否同步
	 * @param pageIndex 开始标志
	 * @param pageSize  页大小
	 * @return
	 */
	@ResponseBody
	@GetMapping("/list")
	Result list(@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model){
		Pageable pageable =  new PageRequest(pageIndex, pageSize,Sort.Direction.ASC, "scheduleid");
		Page<LogDO> page = logService.queryList(pageable);
		return Result.layuiPageOk(page.getTotalPages(), page.getContent());
	}
	
	@ResponseBody
	@PostMapping("/remove")
	Result remove(Long id) {
		try{
			logService.remove(id);
		}catch(Exception e){
			Result.error();
		}
		return Result.ok();
	}

	@ResponseBody
	@PostMapping("/batchRemove")
	Result batchRemove(@RequestParam("ids[]") Long[] ids) {
		int r = logService.batchRemove(ids);
		if (r > 0) {
			return Result.ok();
		}
		return Result.error();
	}
}
