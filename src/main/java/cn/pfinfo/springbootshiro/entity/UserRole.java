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
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Setter
@Getter
@Table(name="user_role")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USER_ROLE_URID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_ROLE_URID_GENERATOR")
	private Long id;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JsonBackReference
	private Role role;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JsonBackReference
	private User user;


}