package cn.pfinfo.springbootshiro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Setter
@Getter
@Table(name="USERS")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static byte STATE_UNAVAILABLE = -1;

	public static byte STATE_LOCKE = 0;

	@Id
	@SequenceGenerator(name="USER_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_ID_GENERATOR")
	private Long id;

	private String name;

	private String password;

	private String salt;

	private byte state;

	@Column(name="user_name")
	private String userName;
	
	@ManyToOne
	@JoinColumn(name="organization_id")
	@JsonBackReference
	private Organization organization;


	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="user" ,fetch = FetchType.EAGER)
	private Set<UserRole> userRoles;
	
	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="user" ,fetch = FetchType.EAGER)
	private Set<Schedule> schedules;

	public Schedule addSchedule(Schedule schedules) {
		getSchedules().add(schedules);
		schedules.setUser(this);
		return schedules;
	}

	public Schedule removeSchedule(Schedule schedules) {
		getSchedules().remove(schedules);
		schedules.setUser(null);
		return schedules;
	}
	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setUser(this);
		
		return userRole;
	}
	
	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setUser(null);
		
		return userRole;
	}
	
	public List<Role> getRoles(){
		List<Role> roles = new ArrayList<Role>();
		userRoles.forEach(ur->{
			if(ur.getRole().getAvailable()!=0){
				roles.add(ur.getRole());
			}
		});
		return roles;
	}

	public String getSalt(){
		return salt==null?this.userName+"":this.userName+salt;
	}
	/**
	 * 用于加密的实际盐值
	 * @return
	 */
	public String getSaltTo(){
		return salt==null?this.userName+"":this.userName+salt;
	}
	
}