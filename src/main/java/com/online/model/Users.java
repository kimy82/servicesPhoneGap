
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
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table( name="USERS" )
public class Users implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	@Expose
	private String				username;

	private String				password;

	protected UserRole			userRole;

	private String				telNumber;
	
	private Company				company;
	
	private Idioma				idioma;

	// CONSTRUCTORS
	public Users( String username ) {

		super();
		this.username = username;		
	}

	public Users() {

		super();
	}

	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Long getId(){

		return id;
	}

	public void setId( Long id ){

		this.id = id;
	}

	@Column(name = "USERNAME", unique = true, nullable = false, length = 45)
	public String getUsername(){

		return username;
	}

	public void setUsername( String username ){

		this.username = username;
	}

	@Column(name = "PASSWORD", unique = false, nullable = false, length = 65)
	public String getPassword(){

		return password;
	}

	public void setPassword( String password ){

		this.password = password;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	public UserRole getUserRole(){
		return userRole;
	}

	public void setUserRole( UserRole userRole ){

		this.userRole = userRole;
	}

	@Column(name = "TELEFON", nullable = true, length = 45)
	public String getTelNumber(){
	
		return telNumber;
	}

	public void setTelNumber( String telNumber ){
	
		this.telNumber = telNumber;
	}
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Company getCompany(){
	
		return company;
	}

	public void setCompany( Company company ){
	
		this.company = company;
	}				
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Idioma getIdioma(){
	
		return idioma;
	}

	public void setIdioma( Idioma idioma ){
	
		this.idioma = idioma;
	}

}
