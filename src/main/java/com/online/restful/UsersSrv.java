/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *  
 *   http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *  
 *******************************************************************************/

package com.online.restful;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.wink.common.annotations.Workspace;
import org.apache.wink.server.utils.LinkBuilders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.dao.IdiomaDao;
import com.online.dao.UsersDao;
import com.online.model.UserRole;
import com.online.model.Users;
import com.online.utils.Constants;
import com.online.utils.Utils;

@Workspace(workspaceTitle = "services", collectionTitle = "userSrv")
@Path("/service/userSrv")
public class UsersSrv extends HibernateDaoSupport {
	
	UsersDao usersDao;
	IdiomaDao idiomaDao;
   
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    public String getUsers(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
        
        String json="";
        
        String userName = uriInfo.getQueryParameters().get("user").get(0);
        if(userName==null || userName.equals("null"))return "{}";
        
    	List<Users> userFounds = (List<Users>) getHibernateTemplate().find("from Users u where u.username = ?", userName);
		if (userFounds==null || userFounds.isEmpty())
			return "{}";
		Users userFound = userFounds.get(0);
		
        
        Session session = this.getSessionFactory().openSession();
		session.beginTransaction();
		List<Users> userList = new ArrayList<Users>();
		if(userFound.getUserRole().getRole().equals("ROLE_SUPER_ADMIN")){
			userList = (List<Users>) session.createQuery("from Users").list();		
		}else if (userFound.getUserRole().getRole().equals("ROLE_ADMIN")){
			Long idCompany = userFound.getCompany().getId();
			userList = (List<Users>) session.createCriteria(Users.class).add(Restrictions.eq("company.id", idCompany)).add(Restrictions.ne("userRole.role","ROLE_SUPER_ADMIN")).list();
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		json = gson.toJson(userList);
		
		session.close();
		
        return json;
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/delete")
    public String delete(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
        
        String json="";
        String userName = uriInfo.getQueryParameters().get("user").get(0);
    
		
		List<Users> userFounds = (List<Users>) getHibernateTemplate().find("from Users u where u.username = ?", userName);
		if (userFounds==null || userFounds.isEmpty())
			return null;
		Users userFound = userFounds.get(0);
		
		
		Session session = this.getSessionFactory().openSession();
		session.beginTransaction();
		Users userToDelete = (Users) session.load(Users.class, userFound.getId());
		session.delete(userToDelete.getUserRole());
		session.delete(userToDelete);
		session.getTransaction().commit();
		session.close();
		
		
        return "{\"ok\":\"ok\"}";
    }
    
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/insert")
    public String insert(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
        
        String json="";
        String userName = uriInfo.getQueryParameters().get("user").get(0);
        String userInsertName = uriInfo.getQueryParameters().get("userinsert").get(0);
        String password = uriInfo.getQueryParameters().get("pass").get(0);
        String tel = uriInfo.getQueryParameters().get("tel").get(0);
        String idioma = uriInfo.getQueryParameters().get("idioma").get(0);        
        String role = uriInfo.getQueryParameters().get("role").get(0);
        
        try {
        	password = Utils.createSHA(password);
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
			 return "{\"ok\":\"ko\"}";
		}
        
        List<Users> userFounds = (List<Users>) getHibernateTemplate().find("from Users u where u.username = ?", userName);
		if (userFounds==null || userFounds.isEmpty())
			return "{}";
		Users userFound = userFounds.get(0);

        Users user = new Users();
        user.setPassword(password);
        user.setUsername(userInsertName);
        user.setCompany(userFound.getCompany());
        user.setTelNumber(tel);        
        user.setIdioma(idiomaDao.load(idioma));
        
        getHibernateTemplate().save(user);
    	
		UserRole userRole = new UserRole();
		if(role==null || role.equals("null")){
			userRole = usersDao.loadRole(Constants.ROLE_CLIENT);
		}else if ( role.equals("ROLE_SUPER_ADMIN")){
			userRole = usersDao.loadRole(Constants.ROLE_SUPER_ADMIN);
		}else if(role.equals("ROLE_ADMIN")){
			userRole = usersDao.loadRole(Constants.ROLE_ADMIN);
		}else{
			userRole = usersDao.loadRole(Constants.ROLE_CLIENT);
		}

		getHibernateTemplate().save(userRole);
		
        return "{\"ok\":\"ok\"}";
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/login")
    public String login(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        String json="";
	        String userName = uriInfo.getQueryParameters().get("user").get(0);
	        String password = uriInfo.getQueryParameters().get("pass").get(0);
	        if(userName==null || userName.equals("") || password==null || password.equals("") || userName.equals("null")|| password.equals("null") ){
	        	return "{\"ok\":\"ko\"}";
	        }
	        Session session = this.getSessionFactory().openSession();
			session.beginTransaction();
			
			List<Users> userList = session.createQuery("from Users usu where usu.username='"+userName+"' and usu.password='"+Utils.createSHA(password)+"'").list();
			
			
			if(userList==null || userList.isEmpty()){
				return "{\"ok\":\"ko\"}";
			}else{
				
				Users user = userList.get(0);
				
				String role = user.getUserRole()==null? "ROLE_CLIENT" : user.getUserRole().getRole();
				String company = user.getCompany().getName();
				Long companyId = user.getCompany().getId();
				String idioma = user.getIdioma()==null? "CA":user.getIdioma().getName();
				
				json="{\"ok\":\"ok\" , \"username\" : \""+userName+"\" , \"password\" : \""+password+"\", \"role\" : \""+role+"\", \"companyName\":\""+company+"\", \"companyId\":\""+companyId+"\", \"idioma\":\""+idioma+"\"}";
			}									
			session.close();
			
	        return json;
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		}
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/video")
    public String video(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
        
        String json="";
        
		
        return "{\"ok\":\"ok\"}";
    }

	public void setUsersDao( UsersDao usersDao ){
	
		this.usersDao = usersDao;
	}
    
    

}
