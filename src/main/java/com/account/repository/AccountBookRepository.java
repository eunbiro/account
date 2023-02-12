package com.account.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.account.entity.AccountBook;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long>  {

	List<AccountBook> findByMemberId(Long memberId);
	
	@Query(value = "select a.acc_date as accDate from accounts a where a.member_id = :MemberId group by acc_date", nativeQuery = true)
	List<LocalDate> findAccDateByMemberId(@Param("MemberId") Long memberId);
	
	@Query(value = "select * from accounts a where a.member_id = :MemberId and a.acc_date = :accDate", nativeQuery = true)
	List<AccountBook> findbyAccDateAndMemberId(@Param("accDate") String accDate, @Param("MemberId") Long member_id);
	
	Optional<AccountBook> findById(Long accId);
	
	@Query(value = "select sum(money) as money from accounts a where a.member_id = :MemberId and a.acc_date like :accDate% and not acc_status = 1", nativeQuery = true)
	Long findByMoney(@Param("accDate") String date, @Param("MemberId") Long memberId);

	@Query(value = "select m.main_ctg_name, a.acc_date, a.acc_title, a.money"
			+ " from main_category m, sub_category s, accounts a"
			+ " where m.main_ctg_id = s.main_ctg_id and a.sub_ctg_id = s.sub_ctg_id"
			+ " and m.main_ctg_id = :mainCtgId and a.acc_date > :accDate", nativeQuery = true)
	List<AccountBook> findByResult(@Param("accDate") LocalDate accDate, @Param("mainCtgId") String mainCtgId);
}
