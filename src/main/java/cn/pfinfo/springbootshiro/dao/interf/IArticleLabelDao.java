package cn.pfinfo.springbootshiro.dao.interf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.pfinfo.springbootshiro.dao.base.ICommonDao;
import cn.pfinfo.springbootshiro.entity.ArticleLabel;

/**
 * Created by panfei on 2018/3/06.
 */
@Repository
public interface IArticleLabelDao extends JpaRepository<ArticleLabel, Long>,JpaSpecificationExecutor<ArticleLabel>,ICommonDao<ArticleLabel, Long> {

}
 