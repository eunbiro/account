package com.account.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.account.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
	
	
	List<SubCategory> findBymainCategory(String mainCtgId);
	

}
