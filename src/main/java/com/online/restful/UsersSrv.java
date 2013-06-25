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

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.apache.wink.common.annotations.Workspace;
import org.apache.wink.common.internal.utils.MediaTypeUtils;
import org.apache.wink.common.model.multipart.InMultiPart;
import org.apache.wink.common.model.multipart.InPart;
import org.apache.wink.server.utils.LinkBuilders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.dao.CategoryDao;
import com.online.dao.CompanyDao;
import com.online.dao.IdiomaDao;
import com.online.dao.NotificacioDao;
import com.online.dao.UsersDao;
import com.online.model.Category;
import com.online.model.Company;
import com.online.model.Image;
import com.online.model.Notificacio;
import com.online.model.SubCategory;
import com.online.model.SubSubCategory;
import com.online.model.UserRole;
import com.online.model.Users;
import com.online.pojos.CategoriesTO;
import com.online.pojos.NotificacioTO;
import com.online.pojos.UsersTO;
import com.online.utils.Constants;
import com.online.utils.Utils;

@Workspace(workspaceTitle = "services", collectionTitle = "userSrv")
@Path("/service/userSrv")
public class UsersSrv extends HibernateDaoSupport {
	
	UsersDao usersDao;
	IdiomaDao idiomaDao;
	CategoryDao categoryDao;
	CompanyDao companyDao;
	NotificacioDao notificacioDao;
   
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
		List<UsersTO> userTOList = new ArrayList<UsersTO>();
		if(userFound.getUserRole().getRole().equals("ROLE_SUPER_ADMIN")){
			userList = (List<Users>) session.createQuery("from Users").list();		
		}else if (userFound.getUserRole().getRole().equals("ROLE_ADMIN")){
			Integer idCompany = userFound.getCompany().getId();
			userList = (List<Users>) session.createCriteria(Users.class).add(Restrictions.eq("company.id", idCompany)).add(Restrictions.ne("userRole.role","ROLE_SUPER_ADMIN")).list();
		}else if(userFound.getUserRole().getRole().equals("ROLE_CLIENT")) {
			Integer idCompany = userFound.getCompany().getId();
			userList = (List<Users>) session.createCriteria(Users.class).add(Restrictions.eq("company.id", idCompany)).add(Restrictions.ne("userRole.role","ROLE_CLIENT")).list();
		}
		for(Users user : userList){
			userTOList.add(new UsersTO(user.getUsername(),user.getUserRole().getRole()));
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		json = gson.toJson(userTOList);
		
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

		user.setUserRole(userRole);
		getHibernateTemplate().save(user);
		
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
				Integer companyId = user.getCompany().getId();
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
    @Path("/categoryFill")
    public String categoryFill(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        String json="";
	        String companyId = uriInfo.getQueryParameters().get("companyId").get(0);
	        
	        if(companyId==null || companyId.equals("") || companyId.equals("null")|| companyId.equals("standard") ){
	        	return "{\"ok\":\"ko\"}";
	        }
	        Session session = this.getSessionFactory().openSession();
			session.beginTransaction();
			
			List<Category> categoryList = session.createCriteria(Category.class).add(Restrictions.eq("company.id", Integer.parseInt(companyId))).list();
			
			
			if(categoryList==null || categoryList.isEmpty()){
				return "{}";
			}else{
				List<CategoriesTO> categoriesTOList = new ArrayList<CategoriesTO>();
				for(Category category : categoryList){
					CategoriesTO catTo = new CategoriesTO(category.getName(),"N0",null,category.getId());
					categoriesTOList.add(catTo);
					getHibernateTemplate().initialize(category.getSubCategories());
					for(SubCategory subCategory : category.getSubCategories()){
						CategoriesTO catSubTo = new CategoriesTO(subCategory.getName(),"N1",category.getId().toString(),subCategory.getId());
						categoriesTOList.add(catSubTo);
						getHibernateTemplate().initialize(subCategory.getSubSubCategories());
						for(SubSubCategory subSubCategory : subCategory.getSubSubCategories()){
							CategoriesTO catSubSubTo = new CategoriesTO(subSubCategory.getName(),"N2",subCategory.getId().toString(),subSubCategory.getId());
							categoriesTOList.add(catSubSubTo);														
						}
					}
					
				}	
				
				Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
				json = gson.toJson(categoriesTOList);
			}									
			session.close();
			
	        return json;
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		}
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/companyFill")
    public String companyFill(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        String json="";
	        
	        Session session = this.getSessionFactory().openSession();
			session.beginTransaction();
			
			List<Company> companyList = this.companyDao.getAll();
			
			
			if(companyList==null || companyList.isEmpty()){
				return "{}";
			}else{								
				Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
				json = gson.toJson(companyList);
			}									
			session.close();
			
	        return json;
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		}
    }
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/deleteCompany")
    public String deleteCompany(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        String json="";
	        
	        String companyId = uriInfo.getQueryParameters().get("companyId").get(0);
	        
	        Session session = this.getSessionFactory().openSession();
			session.beginTransaction();
			
			Company company = this.companyDao.load(Integer.parseInt(companyId));
			
			this.companyDao.delete(company);
										
			session.close();
			
			return "{\"ok\":\"ok\"}";
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		}
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/creaCompany")
    public String creaCompany(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        String json="";
	        
	        String name = uriInfo.getQueryParameters().get("name").get(0);
	        
	        Session session = this.getSessionFactory().openSession();
			session.beginTransaction();
			
			Company company = new Company();
			company.setName(name);
			company.setDesc_ca("");
			company.setDesc_en("");
			company.setDesc_es("");
					
			this.companyDao.save(company);
			
			
										
			session.close();
			
			return "{\"ok\":\"ok\" , \"id\":\""+company.getId()+"\"}";
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		}
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/getNotificacions")
    public String getNotificacions(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        String json="";
	        
	        String user = uriInfo.getQueryParameters().get("user").get(0);
	        
	       List<Notificacio> notifList = this.notificacioDao.findByUsername(user);
	       List<NotificacioTO> notifTOList = new ArrayList<NotificacioTO>();
	       
	       for(Notificacio notif : notifList){
	    	   if(user.equals(notif.getUser2().getUsername()) && notif.isRecieved()==false){
	    		   notif.setRecieved(true);
	    		   this.notificacioDao.update(notif);
	    	   }
	    	   NotificacioTO notiTO = new NotificacioTO(notif.getId(),notif.getUser1().getUsername(),notif.getUser2().getUsername(),notif.getNotificacio(),notif.isSent(),notif.isRecieved(),Utils.formatDateDDMMYYYY(notif.getData()));
	    	   notifTOList.add(notiTO);
	       }
	       Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
			json = gson.toJson(notifTOList);
			return json;
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		}
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/sentNotif")
    public String sentNotif(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        String json="";
	        
	        String userS = uriInfo.getQueryParameters().get("user").get(0);
	        String userToS = uriInfo.getQueryParameters().get("userTo").get(0);
	        String notif = uriInfo.getQueryParameters().get("notif").get(0);
	        
	        Notificacio noti = new Notificacio();
	        noti.setData(new Date());
	        noti.setNotificacio(notif);
	        noti.setSent(true);
	        noti.setRecieved(false);
	        noti.setUser1(this.usersDao.findByUsername(userS));
	        noti.setUser2(this.usersDao.findByUsername(userToS));
	        
	        this.notificacioDao.save(noti);
	        
	   
	        NotificacioTO notiTO = new NotificacioTO(noti.getId(),userS,userToS,notif,true,false,Utils.formatDateDDMMYYYY(noti.getData()));
	        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
			json = gson.toJson(notiTO);
			return json;
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		}
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/createCategory")
    public String createCategory(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        
	        String companyId = uriInfo.getQueryParameters().get("companyId").get(0);
	        String name = uriInfo.getQueryParameters().get("name").get(0);
	        if(name==null || name.equals("") || companyId==null || companyId.equals("")){
	        	return "{\"ok\":\"ko\"}";
	        }
	        	        
			Company company = this.companyDao.load(Integer.parseInt(companyId));
			Category category = new Category(company,name);
			this.categoryDao.save(category);
			
			
			return "{\"ok\":\"ok\", \"id\":\""+category.getId()+"\"}";
			
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		} 
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/createSubCategory")
    public String createSubCategory(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        
	        String companyId = uriInfo.getQueryParameters().get("companyId").get(0);
	        String name = uriInfo.getQueryParameters().get("name").get(0);
	        String namesuper = uriInfo.getQueryParameters().get("namesuper").get(0);
	        
	        if(name==null || name.equals("") || companyId==null || companyId.equals("")){
	        	return "{\"ok\":\"ko\"}";
	        }
	        
	        SubCategory subCategory = new SubCategory(name);
			Company company = this.companyDao.load(Integer.parseInt(companyId));
			Category category = this.categoryDao.load(new Long(namesuper));
			subCategory.setCategory(category);
			this.categoryDao.saveSubCategory(subCategory);
			
			return "{\"ok\":\"ok\",  \"id\":\""+subCategory.getId()+"\"}";
			
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		} 
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/createSubSubCategory")
    public String createSubSubCategory(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        
	        String companyId = uriInfo.getQueryParameters().get("companyId").get(0);
	        String name = uriInfo.getQueryParameters().get("name").get(0);
	        String namesuper = uriInfo.getQueryParameters().get("namesuper").get(0);
	        
	        if(name==null || name.equals("") || companyId==null || companyId.equals("")){
	        	return "{\"ok\":\"ko\"}";
	        }
	        
	        SubSubCategory subSubCategory = new SubSubCategory(name);
			Company company = this.companyDao.load(Integer.parseInt(companyId));
			SubCategory subCategory = this.categoryDao.loadSubCat(new Long(namesuper));
			subSubCategory.setSubCategory(subCategory);
			this.categoryDao.saveSubSubCategory(subSubCategory);
			
			return "{\"ok\":\"ok\",  \"id\":\""+subSubCategory.getId()+"\"}";
			
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		} 
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/deleteSubSubCategory")
    public String deleteSubSubCategory(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        
    		String idSubSubCategory = uriInfo.getQueryParameters().get("idSubSubCategory").get(0);
	        
	        if(idSubSubCategory==null || idSubSubCategory.equals("")){
	        	return "{\"ok\":\"ko\"}";
	        }
	        
	        
	    	SubSubCategory subSubCategory = this.categoryDao.loadSubSubCat(new Long(idSubSubCategory));
	    	
	    	this.categoryDao.deleteSubSubCategory(subSubCategory);	    		    		    	
	    	
			return "{\"ok\":\"ok\"}";
			
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		} 
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/deleteSubCategory")
    public String deleteSubCategory(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        
    		String idSubCategory = uriInfo.getQueryParameters().get("idSubCategory").get(0);
	        
	        if(idSubCategory==null || idSubCategory.equals("")){
	        	return "{\"ok\":\"ko\"}";
	        }
	        
	        
	    	SubCategory subCategory = this.categoryDao.loadSubCat(new Long(idSubCategory));
	    	this.categoryDao.deleteSubCategory(subCategory);
	      
			return "{\"ok\":\"ok\"}";
			
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		} 
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/deleteCategory")
    public String deleteCategory(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
    	try {
	        
	        
	        String idCategory = uriInfo.getQueryParameters().get("idCategory").get(0);
	        
	        
	        if(idCategory==null || idCategory.equals("") ){
	        	return "{\"ok\":\"ko\"}";
	        }
	        
	        
	        Category category = this.categoryDao.load(new Long(idCategory));
	        this.categoryDao.delete(category);
	      
			return "{\"ok\":\"ok\"}";
			
    	} catch (HibernateException e) {			
			e.printStackTrace();
			return "{\"ok\":\"ko\"}";
		} 
    }
    
    
    @POST
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/video")
    public String video(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
        
        String json="";
        
		
        return "{\"ok\":\"ok\"}";
    }
    
    @POST
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Consumes( MediaTypeUtils.MULTIPART_FORM_DATA)
    @Path("/uploadFoto")
    public String uploadFoto(InMultiPart inMP) throws IOException {
        
    	 while (inMP.hasNext()) {
    	        InPart part = inMP.next();
    	        MultivaluedMap<String, String> heades = part.getHeaders();
    	        String CDHeader = heades.getFirst("Content-Disposition");
    	        InputStream is = part.getBody(InputStream.class, null);
    	       
    	      
    	        byte[] bytes = IOUtils.toByteArray(is);    	        
    	        Image img = new Image();
    	        img.setImage(bytes);
    	        img.setName("comapny_pre");
    	        // use the input stream to read the part body
    	    	Session session = this.getSessionFactory().openSession();
    			session.beginTransaction();
    			session.save(img);		    			
    			session.getTransaction().commit();
    			session.close();
    			
    	    }

		
        return "{\"ok\":\"ok\"}";
    }

	public void setUsersDao( UsersDao usersDao ){
	
		this.usersDao = usersDao;
	}

	public void setIdiomaDao( IdiomaDao idiomaDao ){
	
		this.idiomaDao = idiomaDao;
	}

	public void setCategoryDao( CategoryDao categoryDao ){
	
		this.categoryDao = categoryDao;
	}

	public void setCompanyDao( CompanyDao companyDao ){
	
		this.companyDao = companyDao;
	}

	public void setNotificacioDao( NotificacioDao notificacioDao ){
	
		this.notificacioDao = notificacioDao;
	}        
	
	
	
}
