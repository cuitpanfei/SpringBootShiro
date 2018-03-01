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


@Entity
@Setter
@Getter
@Table(name="SCHEDULE")
@NamedQuery(name="Schedule.findAll", query="SELECT u FROM Schedule u")
public class Schedule implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5027601402266748219L;
	@Id
	@SequenceGenerator(name="SCHEDULE_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SCHEDULE_ID_GENERATOR")
	private Long scheduleid;
	
	@ManyToOne
	@JsonBackReference
	private User user;
	
	private String schedulecontent;

	private String creatdate;
}
