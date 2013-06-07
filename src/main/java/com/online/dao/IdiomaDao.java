package com.online.dao;

import java.util.List;

import com.online.model.Idioma;


public interface IdiomaDao {
	
	void save(Idioma idioma) ;
	
	List<Idioma> getAll();
	
	Idioma load( Long id );
	
}
