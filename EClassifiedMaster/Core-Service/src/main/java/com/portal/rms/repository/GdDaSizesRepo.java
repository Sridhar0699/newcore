package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.da.entities.DaSizes;

public interface GdDaSizesRepo  extends CrudRepository<DaSizes, Integer>{

	@Query("from DaSizes ds where id = ?1 and markAsDelete = false ")
	DaSizes getSizeById(Integer id);
	
	@Query("from DaSizes ds where id in (?1) and markAsDelete = false ")
	List<DaSizes> getDaCategoryTypeBySizeId(List<Integer> sizeIds);
}
