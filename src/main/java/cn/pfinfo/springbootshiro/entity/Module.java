package cn.pfinfo.springbootshiro.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the module database table.
 * 
 */
@Entity
@Setter
@Getter
@NamedQuery(name="Module.findAll", query="SELECT m FROM Module m")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MODULE_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MODULE_ID_GENERATOR")
	private Long id;

	private String name;

	private String url;

	//bi-directional many-to-one association to AccessControl
	@OneToMany(mappedBy="module",fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<AccessControl> accessControls;

	public AccessControl addAccessControl(AccessControl accessControl) {
		getAccessControls().add(accessControl);
		accessControl.setModule(this);

		return accessControl;
	}

	public AccessControl removeAccessControl(AccessControl accessControl) {
		getAccessControls().remove(accessControl);
		accessControl.setModule(null);

		return accessControl;
	}

}