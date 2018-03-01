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
 * The persistent class for the access_control database table.
 * 
 */
@Entity
@Setter
@Getter
@Table(name="access_control")
@NamedQuery(name="AccessControl.findAll", query="SELECT a FROM AccessControl a")
public class AccessControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACCESSCONTROL_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACCESSCONTROL_ID_GENERATOR")
	private Long id;

	private int code;

	//bi-directional many-to-one association to Module
	@ManyToOne
	@JsonBackReference
	private Module module;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JsonBackReference
	private Role role;

}