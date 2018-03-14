package cn.pfinfo.springbootshiro.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="SYSRESOURCE")
@NamedQuery(name="Resource.findAll", query="SELECT p FROM Resource p")
public class Resource {

	@Id
	@SequenceGenerator(name="RESOURCE_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESOURCE_ID_GENERATOR")
	private Long id;
	
	// 父菜单ID，一级菜单为0
	@ManyToOne
	@JsonBackReference
	private Resource parent;
	//子菜单
	@OneToMany(mappedBy="parent" ,fetch = FetchType.EAGER)
	private Set<Resource> children = new HashSet<>();
	// 菜单名称
	private String name;
	// 菜单URL
	private String url;
	// 授权
	@OneToOne(cascade={CascadeType.ALL})
	private Permission perms;
	// 类型 0：目录 1：菜单 2：按钮
	private Integer type;
	// 菜单图标
	private String icon;
	// 排序
	private Integer orderNum;
	// 创建时间
	private Date gmtCreate;
	// 修改时间
	private Date gmtModified;
	
	
}
