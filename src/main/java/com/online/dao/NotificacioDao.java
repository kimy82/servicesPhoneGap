package com.online.dao;

import java.util.List;

import com.online.model.Notificacio;


public interface NotificacioDao {
	
	void save(Notificacio notificacio) ;
	
	void update(Notificacio notificacio) ;
	
	List<Notificacio> findByUsername(String username);
	
	List<Notificacio> getAll();
	
	Notificacio load( Long id );

}
