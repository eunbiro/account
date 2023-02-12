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

	@Query(value = "select * from accounts a where a.member_id = :MemberId and a.acc_date like :accDate% and not acc_status = 1", nativeQuery = true)
	List<AccountBook> findByResult(LocalDate accDate, String mainCtgId);
}
