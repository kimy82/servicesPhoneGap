
package com.online.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table( name="SUBSUBCATEGORIES" )
public class SubSubCategory implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	private String				name;
	
	private SubCategory			subCategory;


	// CONSTRUCTORS
	public SubSubCategory() {
	}
	

	public SubSubCategory( String name ) {

		super();
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
	@JoinColumn(name = "SUBCATEGORY_ID", nullable = false)
	public SubCategory getSubCategory(){
	
		return subCategory;
	}

	public void setSubCategory( SubCategory subCategory ){
	
		this.subCategory = subCategory;
	}



	
}
