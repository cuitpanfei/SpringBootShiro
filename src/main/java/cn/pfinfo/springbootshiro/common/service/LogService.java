package cn.pfinfo.springbootshiro.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.common.domain.LogDO;

@Service
public interface LogService {
	void save(LogDO logDO);
	Page<LogDO> queryList(Pageable pageable);
	
	/**
	 * 删除指定id的日志
	 * @param id
	 */
	void remove(Long id) throws Exception;
	
	/**
	 * 删除日志并返回操作成功的数量
	 * @param ids
	 * @return
	 */
	int batchRemove(Long[] ids);
}
