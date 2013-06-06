
package com.online.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="IMAGES" )
public class Image implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	private byte[]	        	image;

	private String				name;

	// CONSTRUCTORS
	public Image() {
	}

	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IMAGE_ID", unique = true, nullable = false)
	public Long getId(){

		return id;
	}

	public void setId( Long id ){

		this.id = id;
	}
	
	@Column(name = "IMAGE", unique = false, nullable = false)
	public byte[] getImage(){
	
		return image;
	}

	public void setImage( byte[] image ){
	
		this.image = image;
	}

	@Column(name = "IMAGE_NAME", unique = false, nullable = false, length = 200)
	public String getName(){
	
		return name;
	}

	public void setName( String name ){
	
		this.name = name;
	}
}
