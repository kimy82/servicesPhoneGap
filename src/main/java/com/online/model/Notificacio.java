
package com.online.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.gson.annotations.Expose;


@Entity
@Table( name="NOTIFICACIONS" )
public class Notificacio implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				id;

	
	private Users				user1;
	
	private Users				user2;
	
	private Date				data;

	
	private String 				notificacio;
	
	
	private boolean				sent;
	
	
	private boolean				recieved;
	
	// CONSTRUCTORS
	public Notificacio(){
	}

	// GETTERS i SETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NOTIFICACIO_ID", unique = true, nullable = false)
	public Long getId(){

		return id;
	}

	public void setId( Long id ){

		this.id = id;
	}

	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	public Users getUser1(){
	
		return user1;
	}

	public void setUser1( Users user1 ){
	
		this.user1 = user1;
	}
	
	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	public Users getUser2(){
	
		return user2;
	}

	public void setUser2( Users user2 ){
	
		this.user2 = user2;
	}

	@Column(name = "NOTIFICACIO", unique = false, nullable = false, length = 200)
	public String getNotificacio(){
	
		return notificacio;
	}

	public void setNotificacio( String notificacio ){
	
		this.notificacio = notificacio;
	}

	@Column(name = "NOTIF_DATE", unique = false, nullable = false)
	public Date getData(){
	
		return data;
	}

	public void setData( Date data ){
	
		this.data = data;
	}


	@Column(name = "NOTIF_SENT", unique = false, nullable = false)
	public boolean isSent(){
	
		return sent;
	}

	public void setSent( boolean sent ){
	
		this.sent = sent;
	}

	@Column(name = "NOTIF_RECIEVED", unique = false, nullable = false)
	public boolean isRecieved(){
	
		return recieved;
	}

	public void setRecieved( boolean recieved ){
	
		this.recieved = recieved;
	}			
	
}
