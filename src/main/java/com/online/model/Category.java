
package com.online.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	private String				level="N0";

	// CONSTRUCTORS
	public Category() {
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

	@ManyToOne(cascade = CascadeType.ALL)
	public Company getCompany(){
	
		return company;
	}

	public void setCompany( Company company ){
	
		this.company = company;
	}

	@Column(name = "CATEGORY_LEVEL", unique = false, nullable = false, length = 10)
	public String getLevel(){
	
		return level;
	}

	public void setLevel( String level ){
	
		this.level = level;
	}	
}
