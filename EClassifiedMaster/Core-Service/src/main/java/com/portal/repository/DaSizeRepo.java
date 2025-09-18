package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.da.entities.DaSizes;

public interface DaSizeRepo extends CrudRepository<DaSizes, Integer>{
	
	@Query(value = "select ds.size_id,ds.size,ds.description,ds.erp_ref_code,ds.category_type,gdcc.category_type as gdcc_category_type from da_sizes ds inner join gd_da_classified_category gdcc on ds.category_type = gdcc.id where ds.mark_as_delete = false", nativeQuery = true)
	List<Object[]> getAllSizes();
	
	@Query("from DaSizes where size = ?1 and categoryType = ?2 and markAsDelete = false")
	DaSizes getDaSizes(String size, Integer categoryTypeId);

	@Query("from DaSizes where sizeId = ?1 and markAsDelete = false")
	DaSizes getDaSizesById(Integer sizeId);
}
