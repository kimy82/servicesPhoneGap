package com.online.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.online.dao.IdiomaDao;
import com.online.model.Idioma;

public class IdiomaDaoImpl extends HibernateDaoSupport implements IdiomaDao{

	
	
	@Transactional
	public List<Idioma> getAll(){

		return getHibernateTemplate().loadAll(Idioma.class);
	}

	public void save( Idioma idioma ){

		getHibernateTemplate().save(idioma);
		
	}

	public Idioma load( Long id ){

		Idioma idioma = getHibernateTemplate().load(Idioma.class, id);
		return idioma;
	}

}
