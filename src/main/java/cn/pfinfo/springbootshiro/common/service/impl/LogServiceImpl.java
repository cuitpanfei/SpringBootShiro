package cn.pfinfo.springbootshiro.common.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.pfinfo.springbootshiro.common.dao.LogDao;
import cn.pfinfo.springbootshiro.common.domain.LogDO;
import cn.pfinfo.springbootshiro.common.service.LogService;
import cn.pfinfo.springbootshiro.common.util.page.PageImplWrapper;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class LogServiceImpl implements LogService {
	@Autowired
	LogDao logDao;

	@Async
	@Override
	public void save(LogDO logDO) {
		 logDao.save(logDO);
	}

	@Override
	public Page<LogDO> queryList(Pageable pageable) {
		Page<LogDO> logs = logDao.findAll(pageable);
		Page<LogDO> page = PageImplWrapper.copy(logs);
		return page;
	}

	@Override
	public void remove(Long id) throws Exception{
		logDao.delete(id);
	}

	@Override
	public int batchRemove(Long[] id) {
		List<Long> ids = Arrays.asList(id);
		List<LogDO> all = logDao.findAll(ids);
		int size = all.size();
		try{
			logDao.deleteInBatch(all);
		}catch(Exception e){
			log.error("部分日志删除失败",e);
			size-=logDao.findAll(ids).size();
		}
		return size;
	}
}
