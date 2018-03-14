package cn.pfinfo.springbootshiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.pfinfo.springbootshiro.dao.interf.IArticleLabelDao;
import cn.pfinfo.springbootshiro.entity.ArticleLabel;
import cn.pfinfo.springbootshiro.service.user.IArticleLabelService;
import lombok.extern.log4j.Log4j;


@Log4j
@Service("articleLabelService")
public class ArticleLabelServiceImpl implements IArticleLabelService {

	
	@Autowired
	private IArticleLabelDao articleLabelDao;

	@Override
	public ArticleLabel getOne(Long id) {
		return articleLabelDao.findOne(id);
	}

	@Override
	public void saveAllByStr(List<String> labels) {
		
		labels.forEach(label->{
			try{
				articleLabelDao.save(new ArticleLabel(label));
			}catch(RuntimeException e){
				Throwable cause = e.getCause();
			    if(cause instanceof org.hibernate.exception.ConstraintViolationException) {
			        String errMsg = ((org.hibernate.exception.ConstraintViolationException)cause).getSQLException().getMessage();
			        if(!StringUtils.isEmpty(errMsg) && errMsg.indexOf("ORA-00001")!=-1) {
			            log.error("重复！");
			        }
			    }
			}
		});
	}


	@Override
	public void saveAllByEntity(List<ArticleLabel> labels) {
		
	}

}
