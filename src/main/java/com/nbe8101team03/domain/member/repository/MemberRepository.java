package com.nbe8101team03.domain.member.repository;

import com.nbe8101team03.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
