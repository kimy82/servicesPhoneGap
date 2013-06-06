
package com.online.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
	
	private Video				video;
	
	private Category			n0Category;
	
	private Category			n1Category;
	
	private Category			n2Category;

	private Idioma				idioma;
	
	private String				config;
	
	// CONSTRUCTORS
	public Product() {
	}

	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_ID", unique = true, nullable = false)
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

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	public Video getVideo(){
	
		return video;
	}

	public void setVideo( Video video ){
	
		this.video = video;
	}

	@Column(name = "PRODUCT_DESC", unique = false, nullable = false, length = 200)
	public String getDescripcio(){
	
		return descripcio;
	}

	public void setDescripcio( String descripcio ){
	
		this.descripcio = descripcio;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Company getCompany(){
	
		return company;
	}

	public void setCompany( Company company ){
	
		this.company = company;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Category getN0Category(){
	
		return n0Category;
	}

	public void setN0Category( Category n0Category ){
	
		this.n0Category = n0Category;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Category getN1Category(){
	
		return n1Category;
	}

	public void setN1Category( Category n1Category ){
	
		this.n1Category = n1Category;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Category getN2Category(){
	
		return n2Category;
	}

	public void setN2Category( Category n2Category ){
	
		this.n2Category = n2Category;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Idioma getIdioma(){
	
		return idioma;
	}

	public void setIdioma( Idioma idioma ){
	
		this.idioma = idioma;
	}

	@Column(name = "PRODUCT_CONFIG_VIEW", unique = false, nullable = false, length = 4000)
	public String getConfig(){
	
		return config;
	}

	public void setConfig( String config ){
	
		this.config = config;
	}
	
}
