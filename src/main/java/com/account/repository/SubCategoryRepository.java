package com.account.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.account.dto.SubCategoryDto;
import com.account.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
	
	@Query(value = "select * from sub_category s where s.main_ctg_id = :mainCtgId", nativeQuery = true)
	List<SubCategory> findByMainCategory(@Param("mainCtgId") String mainCtgId);
	
	@Query(value = "select * from main_category m, sub_category s "
			+ "where m.main_ctg_id = s.main_ctg_id and s.sub_ctg_id = :subId", nativeQuery = true)
	SubCategory findBySubId(@Param("subId") Long subId);

}
