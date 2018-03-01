package cn.pfinfo.springbootshiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.pfinfo.springbootshiro.service.user.IScheduleService;

@RestController
public class ScheduleController {
	
	@SuppressWarnings("unused")
	@Autowired
	private IScheduleService scheduleService;
	
}
