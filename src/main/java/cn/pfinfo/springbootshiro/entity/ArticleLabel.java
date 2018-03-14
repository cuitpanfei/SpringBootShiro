package cn.pfinfo.springbootshiro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Setter
@Getter
@NamedQuery(name="ArticleLabel.findAll", query="SELECT a FROM ArticleLabel a")
public class ArticleLabel {
	

	/**
	 * id
	 */
	@Id
	@SequenceGenerator(name="ARTICLELABEL_ID_GENERATOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARTICLELABEL_ID_GENERATOR")
	private Long id;
	
	/**
	 * 云标签名称
	 */
	@Column(unique=true)
	private String name;
	
	/**
	 * 云标签引用次数
	 */
	@Column(nullable=false,columnDefinition="NUMBER(19,0) default 0")
	private Long times;

	public ArticleLabel(){
		
	}

	public ArticleLabel(String name) {
		this(name,0L);
	}

	public ArticleLabel(String name, Long times) {
		super();
		this.name = name;
		this.times = times;
	}
	
}
