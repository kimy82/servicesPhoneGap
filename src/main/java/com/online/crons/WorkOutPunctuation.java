package com.online.crons;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.online.dao.CategoryDao;
import com.online.dao.CompanyDao;
import com.online.dao.IdiomaDao;
import com.online.dao.UsersDao;
import com.online.model.Category;
import com.online.model.Company;
import com.online.model.Idioma;
import com.online.model.SubCategory;
import com.online.model.SubSubCategory;
import com.online.model.UserRole;
import com.online.model.Users;



public class WorkOutPunctuation implements Job
{
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		//Crea elements necessaris per a la prova de l'app.
		System.out.println("Hello Quartz! Creem inserts BBDD");
		UsersDao usersDao = (UsersDao)context.getJobDetail().getJobDataMap().get("usersDao");
		CompanyDao companyDao = (CompanyDao)context.getJobDetail().getJobDataMap().get("companyDao");
		IdiomaDao idiomaDao = (IdiomaDao)context.getJobDetail().getJobDataMap().get("idiomaDao");
		CategoryDao categoryDao = (CategoryDao)context.getJobDetail().getJobDataMap().get("categoryDao");
		
		String entorn = (String)context.getJobDetail().getJobDataMap().get("entorn");
		
		
		try {
			//creem els tres idiomes
			Idioma idiomaCA = new Idioma("CA");
			Idioma idiomaES = new Idioma("ES");
			Idioma idiomaEN = new Idioma("EN");
			idiomaDao.save(idiomaCA);
			idiomaDao.save(idiomaES);
			idiomaDao.save(idiomaEN);
			
			//creem els roles 
			UserRole roleSA = new UserRole("ROLE_SUPER_ADMIN");
			UserRole roleA = new UserRole("ROLE_ADMIN");
			UserRole roleC = new UserRole("ROLE_CLIENT");
			usersDao.saveRole(roleSA);
			usersDao.saveRole(roleA);
			usersDao.saveRole(roleC);
			
			
			if(entorn.equals("local")){
				//Creem una empresa.
				Company company = new Company();
				company.setDesc_ca("És la millor que existeix");
				company.setDesc_en("It's the best in the market");
				company.setDesc_es("Es la mejor que existe");
				company.setName("ALEXMANY");
				
				companyDao.save(company);
				
				//Creem una segona empresa.
				Company company2 = new Company();
				company2.setDesc_ca("(BIS) És la millor que existeix");
				company2.setDesc_en("(BIS) It's the best in the market");
				company2.setDesc_es("(BIS) Es la mejor que existe");
				company2.setName("ALEXMANY_bis");
				
				companyDao.save(company2);
				
				//Creem els usuaris.
				Users userKim = new Users();
				userKim.setCompany(company);			
				userKim.setIdioma(idiomaCA);
				userKim.setPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");
				userKim.setTelNumber("600 29 52 37");
				userKim.setUsername("joaquim.orra@gmail.com");
								
				userKim.setUserRole(roleSA);
				usersDao.save(userKim);
				
				Users userAlex = new Users();
				userAlex.setCompany(company);				
				userAlex.setIdioma(idiomaCA);
				userAlex.setPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");
				userAlex.setTelNumber("600 29 52 37");
				userAlex.setUsername("adp.alex@gmail.com");	
				userAlex.setUserRole(roleSA);
				usersDao.save(userAlex);
				
				Users userAl = new Users();
				userAl.setCompany(company);			
				userAl.setIdioma(idiomaCA);
				userAl.setPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");
				userAl.setTelNumber("600 29 52 37");
				userAl.setUsername("joaquim.orra2@gmail.com");
				userAl.setUserRole(roleC);
				usersDao.save(userAl);
				
				Users userAl1menu = new Users();
				userAl1menu.setCompany(company);			
				userAl1menu.setIdioma(idiomaCA);
				userAl1menu.setPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");
				userAl1menu.setTelNumber("600 25 52 37");
				userAl1menu.setUsername("joaquim.orra3@gmail.com");
				userAl1menu.setUserRole(roleC);
				usersDao.save(userAl1menu);
				
				Users userAdminCompany = new Users();
				userAdminCompany.setCompany(company);			
				userAdminCompany.setIdioma(idiomaCA);
				userAdminCompany.setPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");
				userAdminCompany.setTelNumber("600 25 52 37");
				userAdminCompany.setUsername("user.admin.company@gmail.com");
				userAdminCompany.setUserRole(roleA);
				usersDao.save(userAdminCompany);
				
				//Creem categories 
				SubCategory subCategory11 = new SubCategory("subCategory11");	
				
				SubSubCategory subsubcat11 = new SubSubCategory("subsubcat11");
				subsubcat11.setSubCategory(subCategory11);
				SubSubCategory subsubcat12 = new SubSubCategory("subsubcat12");		
				subsubcat12.setSubCategory(subCategory11);
				List<SubSubCategory> subSubCategoryList = new ArrayList<SubSubCategory>();
				subSubCategoryList.add(subsubcat11);
				subSubCategoryList.add(subsubcat12);
				
				SubCategory subCategory12 = new SubCategory("subCategory12");
				SubSubCategory subsubcat13 = new SubSubCategory("subsubcat13");
				subsubcat13.setSubCategory(subCategory12);
				SubSubCategory subsubcat14 = new SubSubCategory("subsubcat14");	
				subsubcat14.setSubCategory(subCategory12);
				List<SubSubCategory> subSubCategoryList2 = new ArrayList<SubSubCategory>();
				subSubCategoryList2.add(subsubcat13);
				subSubCategoryList2.add(subsubcat14);
				
				Category category1 = new Category(company,"category1");
				
							
				subCategory11.setSubSubCategories(subSubCategoryList);
				subCategory11.setCategory(category1);
				
							
				subCategory12.setSubSubCategories(subSubCategoryList2);
				subCategory12.setCategory(category1);
				
				List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
				subCategoryList.add(subCategory11);
				subCategoryList.add(subCategory12);
				
				
				category1.setSubCategories(subCategoryList);
				
				Category category2 = new Category(company,"category2");
				
				
				categoryDao.save(category1);
				categoryDao.save(category2);
				
				
			}			
	
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}	
	
	
}
