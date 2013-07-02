
package com.online.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.gson.annotations.Expose;


@Entity
@Table( name="PRODUCTS" )
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	@Expose
	private String				name;

	private String				descripcio;
	
	private Company				company;
	
	private Image				image;
	
	//private Video				video;
	
	private Category			category;
	
	private SubCategory			subCategory;
	
	private SubSubCategory		subsubCategory;

	private Idioma				idioma;	
	
	private Image				img;
	
	private Image				img2;
	
	private String 				html;
	
	// CONSTRUCTORS
	public Product() {
	}

	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_ID", unique = true, nullable = false, columnDefinition="BIGINT" )
	public Long getId(){

		return id;
	}

	public void setId( Long id ){

		this.id = id;
	}

	@Column(name = "PRODUCT_NAME", unique = false, nullable = true, length = 50)
	public String getName(){
	
		return name;
	}

	public void setName( String name ){
	
		this.name = name;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	public Image getImage(){
	
		return image;
	}

	public void setImage( Image image ){
	
		this.image = image;
	}

//	@OneToOne(fetch = FetchType.EAGER)
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

	@Column(name = "PRODUCT_DESC", unique = false, nullable = true, length = 200)
	public String getDescripcio(){
	
		return descripcio;
	}

	public void setDescripcio( String descripcio ){
	
		this.descripcio = descripcio;
	}

	@ManyToOne
	@Cascade({})
	public Company getCompany(){
	
		return company;
	}

	public void setCompany( Company company ){
	
		this.company = company;
	}


	@ManyToOne
	@Cascade({})
	public Category getCategory(){
	
		return category;
	}

	
	public void setCategory( Category category ){
	
		this.category = category;
	}

	@ManyToOne
	@Cascade({})
	public SubCategory getSubCategory(){
	
		return subCategory;
	}

	public void setSubCategory( SubCategory subCategory ){
	
		this.subCategory = subCategory;
	}

	@ManyToOne
	@Cascade({})
	public SubSubCategory getSubsubCategory(){
	
		return subsubCategory;
	}

	public void setSubsubCategory( SubSubCategory subsubCategory ){
	
		this.subsubCategory = subsubCategory;
	}

	@ManyToOne
	@Cascade({})
	public Idioma getIdioma(){
	
		return idioma;
	}

	public void setIdioma( Idioma idioma ){
	
		this.idioma = idioma;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	public Image getImg(){
	
		return img;
	}

	public void setImg( Image img ){	
		this.img = img;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	public Image getImg2(){
	
		return img2;
	}

	public void setImg2( Image img2 ){	
		this.img2 = img2;
	}

	@Column(name = "COMPANY_HTML", unique = false, nullable = true, length = 4000)
	public String getHtml(){
	
		return html;
	}

	public void setHtml( String html ){
	
		this.html = html;
	}
	
}
