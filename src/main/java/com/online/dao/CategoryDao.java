package com.online.dao;

import java.util.List;

import com.online.model.Category;
import com.online.model.Company;
import com.online.model.SubCategory;
import com.online.model.SubSubCategory;


public interface CategoryDao {
	
	void save(Category category) ;
	
	void update(Category category);
	
	void delete(Category category);
	
	List<Category> getAll();
	
	Category load( Long id );
	
	Category loadByNameAndCompany(String name, Company company);
	
	SubCategory loadSubCategoryByNameAndCompany(String name, Company company);
	
	void updateSubCategory( SubCategory subCategory );
	
	void saveSubCategory( SubCategory subCategory );
	
	void deleteSubCategory( SubCategory subCategory );
	
	SubSubCategory loadSubSubCategoryByNameAndCompany(String name, Company company);
	
	void deleteSubSubCategory( SubSubCategory subSubCategory );
	
	SubSubCategory loadSubSubCat( Long id );
	
	SubCategory loadSubCat( Long id );
	
	void saveSubSubCategory( SubSubCategory subSubCategory );
	
}
