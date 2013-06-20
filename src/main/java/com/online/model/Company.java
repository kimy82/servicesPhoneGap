
package com.online.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.gson.annotations.Expose;

@Entity
@Table( name="COMPANIES" )
public class Company implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Expose
	private Integer				id;

	@Expose
	private String				name;

	private String				desc_es;
	
	private String				desc_en;
	
	private String				desc_ca;
	
	private List<Category>      categories;


	// CONSTRUCTORS
	public Company() {
	}

	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COMPANY_ID", unique = true, nullable = false)
	public Integer getId(){

		return id;
	}

	public void setId( Integer id ){

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	@Cascade({CascadeType.ALL})
	public List<Category> getCategories(){
	
		return categories;
	}

	public void setCategories( List<Category> categories ){
	
		this.categories = categories;
	}
	
	

//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@PrimaryKeyJoinColumn
//	public Image getImage(){
//	
//		return image;
//	}
//
//	public void setImage( Image image ){
//	
//		this.image = image;
//	}
//
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@PrimaryKeyJoinColumn
//	public Video getVideo(){
//	
//		return video;
//	}
//
//	public void setVideo( Video video ){
//	
//		this.video = video;
//	}
	
}
