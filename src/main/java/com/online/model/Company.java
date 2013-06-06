
package com.online.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table( name="COMPANIES" )
public class Company implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	@Expose
	private String				name;

	private String				desc_es;
	
	private String				desc_en;
	
	private String				desc_ca;
	
	private Image				image;
	
	private Video				video;

	// CONSTRUCTORS
	public Company() {
	}

	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COMPANY_ID", unique = true, nullable = false)
	public Long getId(){

		return id;
	}

	public void setId( Long id ){

		this.id = id;
	}

	@Column(name = "COMPANY_NAME", unique = false, nullable = false, length = 45)
	public String getName(){
	
		return name;
	}

	public void setName( String name ){
	
		this.name = name;
	}

	@Column(name = "COMPANY_DESC_ES", unique = false, nullable = true, length = 4000)
	public String getDesc_es(){
	
		return desc_es;
	}

	
	public void setDesc_es( String desc_es ){
	
		this.desc_es = desc_es;
	}

	@Column(name = "COMPANY_DESC_EN", unique = false, nullable = true, length = 4000)
	public String getDesc_en(){
	
		return desc_en;
	}

	public void setDesc_en( String desc_en ){
	
		this.desc_en = desc_en;
	}

	@Column(name = "COMPANY_DESC_CA", unique = false, nullable = true, length = 4000)
	public String getDesc_ca(){
	
		return desc_ca;
	}

	public void setDesc_ca( String desc_ca ){
	
		this.desc_ca = desc_ca;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public Image getImage(){
	
		return image;
	}

	public void setImage( Image image ){
	
		this.image = image;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public Video getVideo(){
	
		return video;
	}

	public void setVideo( Video video ){
	
		this.video = video;
	}
	
}
