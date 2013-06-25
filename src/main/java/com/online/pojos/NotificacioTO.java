package com.online.pojos;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class NotificacioTO implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	
	@Expose
	private Long				id				 	= null;
	
	@Expose
	private String				user1				= "";
	
	@Expose
	private String				user2				= "";
	
	@Expose
	private String				notif	    		= "";
	
	@Expose
	private String				date	    		= "";
	
	@Expose
	private boolean				sent	   			= false;
	
	@Expose
	private boolean				recieved	    	= false;
	
	@Expose
	private String				ok	    		    = "ok";
	

	// CONSTRUCTORS
	public NotificacioTO() {

		super();
	}

	

	public NotificacioTO( Long id, String user1, String user2, String notif, boolean sent, boolean recieved,String date ) {

		super();
		this.id = id;
		this.user1 = user1;
		this.user2 = user2;
		this.notif = notif;
		this.sent = sent;
		this.recieved = recieved;
		this.date=date;
		this.ok="ok";
	}



	public Long getId(){
	
		return id;
	}


	public void setId( Long id ){
	
		this.id = id;
	}


	public String getUser1(){
	
		return user1;
	}


	public void setUser1( String user1 ){
	
		this.user1 = user1;
	}


	public String getUser2(){
	
		return user2;
	}


	public void setUser2( String user2 ){
	
		this.user2 = user2;
	}


	public String getNotif(){
	
		return notif;
	}


	public void setNotif( String notif ){
	
		this.notif = notif;
	}


	public boolean isSent(){
	
		return sent;
	}


	public void setSent( boolean sent ){
	
		this.sent = sent;
	}


	public boolean isRecieved(){
	
		return recieved;
	}


	public void setRecieved( boolean recieved ){
	
		this.recieved = recieved;
	}



	public String getDate(){
	
		return date;
	}



	public void setDate( String date ){
	
		this.date = date;
	}



	public String getOk(){
	
		return ok;
	}



	public void setOk( String ok ){
	
		this.ok = ok;
	}

	
}
