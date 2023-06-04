package com.bulletinboard.member.repository;

import com.bulletinboard.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select count(*) > 0 from Member m where m.email.value = :email")
    boolean existsByEmail(@Param("email") String email);

    Optional<Member> findByEmailValue(String email);
}
