package cn.pfinfo.springbootshiro.entity;

import java.io.Serializable;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the organization database table.
 * 
 */
@Entity
@Setter
@Getter
@NamedQuery(name="Organization.findAll", query="SELECT o FROM Organization o")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ORGANIZATION_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORGANIZATION_ID_GENERATOR")
	private Long id;

	private String address;

	private String name;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Organization pOrganization;

	//bi-directional many-to-one association to Organization
	@OneToMany(mappedBy="pOrganization",fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Organization> organizations;
	
	@OneToMany(mappedBy="organization" ,fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<User> users;

	public Organization addOrganization(Organization organization) {
		getOrganizations().add(organization);
		organization.setPOrganization(this);

		return organization;
	}

	public Organization removeOrganization(Organization organization) {
		getOrganizations().remove(organization);
		organization.setPOrganization(null);

		return organization;
	}

}