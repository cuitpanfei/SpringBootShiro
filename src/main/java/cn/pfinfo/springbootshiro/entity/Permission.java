package cn.pfinfo.springbootshiro.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the permission database table.
 * 
 */
@Entity
@Setter
@Getter
@Table(name="SYSPERMISSION")
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERMISSION_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERMISSION_ID_GENERATOR")
	private Long id;

	private byte available;

	private String name;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="parent_ids")
	private String parentIds;

	private String permission;

	@Column(name="resource_type")
	private String resourceType;

	private String url;

	//bi-directional many-to-one association to RolePermission
	@OneToMany(mappedBy="permission",fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<RolePermission> rolePermissions;

	public RolePermission addRolePermission(RolePermission rolePermission) {
		getRolePermissions().add(rolePermission);
		rolePermission.setPermission(this);

		return rolePermission;
	}

	public RolePermission removeRolePermission(RolePermission rolePermission) {
		getRolePermissions().remove(rolePermission);
		rolePermission.setPermission(null);

		return rolePermission;
	}

}