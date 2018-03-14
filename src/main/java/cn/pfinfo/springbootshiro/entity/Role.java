package cn.pfinfo.springbootshiro.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Setter
@Getter
@Table(name="SYSROLE")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ROLE_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROLE_ID_GENERATOR")
	private Long id;

	private byte available;

	private String description;

	private String role;

	//bi-directional many-to-one association to RolePermission
	@OneToMany(mappedBy="role",fetch = FetchType.EAGER)
	private Set<RolePermission> rolePermissions = new HashSet<>();

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="role",fetch = FetchType.EAGER)
	private Set<UserRole> userRoles = new HashSet<>();


	public RolePermission addRolePermission(RolePermission rolePermission) {
		getRolePermissions().add(rolePermission);
		rolePermission.setRole(this);
		return rolePermission;
	}

	public RolePermission removeRolePermission(RolePermission rolePermission) {
		getRolePermissions().remove(rolePermission);
		rolePermission.setRole(null);
		return rolePermission;
	}


	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setRole(this);
		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setRole(null);
		return userRole;
	}

}