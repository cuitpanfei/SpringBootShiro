package cn.pfinfo.springbootshiro.service.user;

import java.util.List;

import cn.pfinfo.springbootshiro.entity.ArticleLabel;
import cn.pfinfo.springbootshiro.service.IBaseService;

public interface IArticleLabelService extends IBaseService<ArticleLabel>{

	void saveAllByStr(List<String> labels);
	void saveAllByEntity(List<ArticleLabel> labels);

}
