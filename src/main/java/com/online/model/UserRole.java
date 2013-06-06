package com.online.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table( name="USER_ROLES" )
public class UserRole implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	
	private Long				id;
	
	
	private String				role;
	
	private Users				user;
	

	
	
	
	//CONSTRUCTORS
	public UserRole( ) {
		super();	
	}





	//GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ROLE_ID", unique = true, nullable = false)
	public Long getId(){
	
		return id;
	}
	public void setId( Long id ){
	
		this.id = id;
	}

	@Column(name = "AUTHORITY", unique = false, nullable = false, length = 45)
	public String getRole(){
	
		return role;
	}

	public void setRole( String role ){
	
		this.role = role;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Users getUser(){
	
		return user;
	}

	public void setUser( Users user ){
	
		this.user = user;
	}
}
