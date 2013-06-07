package com.online.crons;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.online.dao.CompanyDao;
import com.online.dao.IdiomaDao;
import com.online.dao.UsersDao;
import com.online.model.Company;
import com.online.model.Idioma;
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
			}			
	
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}	
	
	
}
