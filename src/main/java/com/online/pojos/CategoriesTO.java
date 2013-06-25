package com.online.pojos;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class CategoriesTO implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Expose
	private String				name				= "";
	
	@Expose
	private Long				id				= null;
	
	@Expose
	private String				type				= "";
	
	@Expose
	private String				superCategoryName	    = "";
	

	// CONSTRUCTORS
	public CategoriesTO() {

		super();
	}

	public CategoriesTO( String name, String type, String superCategoryName, Long id ) {

		super();
		this.name = name;
		this.type = type;
		this.superCategoryName = superCategoryName;
		this.id=id;
	}

	// GETTERS i SETTERS
	public String getName(){
	
		return name;
	}


	public void setName( String name ){
	
		this.name = name;
	}


	public String getType(){
	
		return type;
	}


	public void setType( String type ){
	
		this.type = type;
	}


	public String getSuperCategoryName(){
	
		return superCategoryName;
	}


	public void setSuperCategoryName( String superCategoryName ){
	
		this.superCategoryName = superCategoryName;
	}

	public Long getId(){
	
		return id;
	}

	public void setId( Long id ){
	
		this.id = id;
	}
	
	

	

}
