package com.account.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.account.entity.MainCategory;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {

}
