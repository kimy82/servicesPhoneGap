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
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.wink.common.annotations.Workspace;
import org.apache.wink.server.utils.LinkBuilders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.model.Users;
import com.online.utils.Utils;

@Workspace(workspaceTitle = "Demo Bookmarks Service", collectionTitle = "My Bookmarks")
@Path("/dao")
public class AccessDAO extends HibernateDaoSupport {

   
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    public String getBookmarks(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo) {
        
        String json="";
        Session session = this.getSessionFactory().openSession();
		session.beginTransaction();
		
		List<Users> userList = (List<Users>) session.createQuery("from Users where enabled=1").list();							
		
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		json = gson.toJson(userList);
		
		session.close();
		
        return json;
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/delete")
    public String delete(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo,@QueryParam("user") String userName) {
        
        String json="";
        Session session = this.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.createQuery("delete from Users usu where usu.userName="+userName).executeUpdate();							
		
		
		
		session.close();
		
        return "{\"ok\":\"ok\"}";
    }
    
    @GET
    @Produces( {MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON})
    @Path("/login")
    public String login(@Context LinkBuilders linkProcessor, @Context UriInfo uriInfo,@QueryParam("user") String userName,@QueryParam("pass") String password) {
    	try {
	        String json="";
	        if(userName==null || userName.equals("") || password==null || password.equals("") || userName.equals("null")|| password.equals("null") ){
	        	
	        }
	        Session session = this.getSessionFactory().openSession();
			session.beginTransaction();
			
			List<Users> userList = session.createQuery("select from Users usu where usu.userName="+userName+" and usu.password="+Utils.createSHA(password)).list();
			
			
			if(userList==null || userList.isEmpty()){
				return "{\"ok\":\"ko\"}";
			}else{
				Users user = userList.get(0);
				String role = user.getUserRole().getRole();
				json="{\"ok\":\"ok\" , \"username\" : \""+userName+"\", \"role\" : \""+role+"\"}";
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

}
