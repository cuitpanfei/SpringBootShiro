package cn.pfinfo.springbootshiro.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ATTACHMENT")
@Setter
@Getter
@NamedQuery(name="Attachment.findAll", query="SELECT p FROM Attachment p")
public class Attachment {
	@Id
	@SequenceGenerator(name="Attachment_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Attachment_ID_GENERATOR")
	private Long id;
	/**
	 * 附件上传之后的名称
	 */
	private String newName;
	/**
	 * 附件的原始名称
	 */
	private String oldName;
	/**
	 * 附件的类型，这个类型和contentType类型一致
	 */
	private String type;
	/**
	 * 附件的后缀名
	 */
	private String suffix;
	/**
	 * 附件的url/path
	 */
	private String path;
	/**
	 * 附件的大小
	 */
	private Long fileSize;
	
	/**
	 * 附件附属文章id
	 */
	@ManyToOne
	@JsonBackReference
	private Article article;
	/**
	 * 附件上传者id
	 */
	private Long userId;

}