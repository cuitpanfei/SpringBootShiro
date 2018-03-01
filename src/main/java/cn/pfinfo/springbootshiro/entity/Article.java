package cn.pfinfo.springbootshiro.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name="article")
@NamedQuery(name="Article.findAll", query="SELECT a FROM Article a")
public class Article {

	/**
	 *id
	 */
	@Id
	@SequenceGenerator(name="ARTICLE_ID_GENERATOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARTICLE_ID_GENERATOR")
	private Long id;

	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章分类
	 */
	private String type;
	/**
	 * 文章作者
	 */
	private String author;

	/**
	 * 标签云
	 */
	private String label;
	/**
	 * 文章描述
	 */
	private String description;

	/**
	 * 文章备注
	 */
	private String remark;
	/**
	 * 状态,0：正常；1：删除
	 */
	private Integer status;
	
	/**
	 * 注解@Transient 表示忽略这个字段对数据库的映射，
	 * 也就是说，数据库中不存在该字段，它可能是其他数据计算或者构造的结果
	 */
	@Transient
	private String sortName;
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

}
