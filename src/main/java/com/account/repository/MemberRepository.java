package com.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.account.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByUserId(String userId);
}
