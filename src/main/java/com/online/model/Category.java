
package com.online.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table( name="CATEGORIES" )
public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	private Company	        	company;

	private String				name;
	
	private List<SubCategory>   subCategories;
	



	// CONSTRUCTORS
	public Category() {
	}
	
	

	public Category( Company company, String name ) {

		super();
		this.company = company;
		this.name = name;
	}



	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CATEGORY_ID", unique = true, nullable = false)
	public Long getId(){

		return id;
	}

	public void setId( Long id ){

		this.id = id;
	}

	@Column(name = "CATEGORY_NAME", unique = false, nullable = false, length = 45)
	public String getName(){
	
		return name;
	}

	public void setName( String name ){
	
		this.name = name;
	}

	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	public Company getCompany(){
	
		return company;
	}

	public void setCompany( Company company ){
	
		this.company = company;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	@Cascade({CascadeType.ALL})
	public List<SubCategory> getSubCategories(){
	
		return subCategories;
	}

	public void setSubCategories( List<SubCategory> subCategories ){
	
		this.subCategories = subCategories;
	}
	
	
}
