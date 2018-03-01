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
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Setter
@Getter
@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MENU_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MENU_ID_GENERATOR")
	private Long id;

	private String iconcls;

	private Long seq;

	private String text;

	private String url;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="parent_id")
	@JsonBackReference
	private Menu menu;

	//bi-directional many-to-one association to Menu
	@OneToMany(mappedBy="menu",fetch = FetchType.EAGER)
	private Set<Menu> menus;

	public Menu addMenus(Menu menus) {
		getMenus().add(menus);
		menus.setMenu(this);

		return menus;
	}

	public Menu removeMenus(Menu menus) {
		getMenus().remove(menus);
		menus.setMenu(null);

		return menus;
	}

}