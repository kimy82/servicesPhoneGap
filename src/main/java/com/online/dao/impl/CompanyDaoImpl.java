package com.online.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.online.dao.CompanyDao;
import com.online.model.Company;
import com.online.model.Users;

public class CompanyDaoImpl extends HibernateDaoSupport implements CompanyDao{

	
	@Transactional
	public List<Company> getAll(){

		return getHibernateTemplate().loadAll(Company.class);
	}

	public void save( Company company ){

		getHibernateTemplate().save(company);
		
	}

	public void update( Company company ){

		// TODO Auto-generated method stub
		
	}

	public void delete( Company company ){

		// TODO Auto-generated method stub
		
	}

	public Company load( Long id ){

		// TODO Auto-generated method stub
		return null;
	}

}
