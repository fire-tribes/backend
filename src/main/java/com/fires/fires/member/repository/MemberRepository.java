package com.fires.fires.member.repository;

import com.fires.fires.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderUserId(String providerUserId);
}
