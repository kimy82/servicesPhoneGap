
package com.online.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table( name="SUBCATEGORIES" )
public class SubCategory implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	private String				name;
	
	private Category			category;
	
	private List<SubSubCategory> subSubCategories;


	// CONSTRUCTORS
	public SubCategory() {
	}

	
	public SubCategory( String name ) {

		super();
		this.name = name;
	}


	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUBCATEGORY_ID", unique = true, nullable = false)
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
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	public Category getCategory(){
	
		return category;
	}

	public void setCategory( Category category ){
	
		this.category = category;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subCategory")
	@Cascade({CascadeType.ALL})
	public List<SubSubCategory> getSubSubCategories(){
	
		return subSubCategories;
	}

	public void setSubSubCategories( List<SubSubCategory> subSubCategories ){
	
		this.subSubCategories = subSubCategories;
	}
	
	

	
}
