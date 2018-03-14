package cn.pfinfo.springbootshiro.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
public class ArticleStaticInfo {
	
	/**
	 *id
	 */
	@Id
	@SequenceGenerator(name="ARTICLESTATIC_ID_GENERATOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARTICLESTATIC_ID_GENERATOR")
	private Long id;
	
	/**
	 * 静态化地址
	 */
	private String url;
	/**
	 * 静态化真实地址
	 */
	private String path;
	/**
	 * 静态化时间
	 */
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date staticTime;
	
	@OneToOne(mappedBy="staticinfo")
	@JsonBackReference
	private Article article;
}
