package cn.pfinfo.springbootshiro.service.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.pfinfo.springbootshiro.entity.Schedule;
import cn.pfinfo.springbootshiro.service.IBaseService;

public interface IScheduleService extends IBaseService<Schedule> {

	/**
	 * 根据用户id分页查询一页日程
	 * @param userid
	 * @param pageable
	 * @return
	 */
	Page<Schedule> getEventsByUserId(Long userid,Pageable pageable);
	/**
	 * 根据用户id查询所有日程
	 * @param userid
	 * @return
	 */
	List<Schedule> getEventsByUserId(Long userid);

}
