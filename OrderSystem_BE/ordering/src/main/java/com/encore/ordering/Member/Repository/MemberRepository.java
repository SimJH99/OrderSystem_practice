package com.encore.ordering.Member.Repository;

import com.encore.ordering.Member.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional <Member> findByEmail(String email);
}
