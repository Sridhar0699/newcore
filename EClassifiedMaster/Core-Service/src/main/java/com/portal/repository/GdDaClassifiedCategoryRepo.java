package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdDaClassifiedCategory;


public interface GdDaClassifiedCategoryRepo extends CrudRepository<GdDaClassifiedCategory, Integer>{

	
	@Query("from GdDaClassifiedCategory where UPPER(categoryType) = UPPER(?1) and markAsDelete = false") 
	GdDaClassifiedCategory getDaCategoryType(String categoryType);
	
	
	@Query("from GdDaClassifiedCategory where id =?1 and markAsDelete = false") 
	GdDaClassifiedCategory getDaCategoryById(Integer id);
	
	@Query("from GdDaClassifiedCategory where  markAsDelete = false") 
	List<GdDaClassifiedCategory> getAllCategories();
	
	
	@Query("from GdDaClassifiedCategory where id in (?1) and markAsDelete = false")
	List<GdDaClassifiedCategory> getCategoriesByIds(List<Integer> catgeoryIds);
}
