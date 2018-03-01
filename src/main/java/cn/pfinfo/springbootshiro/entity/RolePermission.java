package cn.pfinfo.springbootshiro.entity;

import java.io.Serializable;

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


/**
 * The persistent class for the role_permission database table.
 * 
 */
@Entity
@Setter
@Getter
@Table(name="role_permission")
@NamedQuery(name="RolePermission.findAll", query="SELECT r FROM RolePermission r")
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ROLE_PERMISSION_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROLE_PERMISSION_ID_GENERATOR")
	private Long id;

	//bi-directional many-to-one association to Permission
	@ManyToOne
	@JsonBackReference
	private Permission permission;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JsonBackReference
	private Role role;

}