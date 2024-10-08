package com.encore.ordering.Common;

import com.encore.ordering.Member.Domain.Member;
import com.encore.ordering.Member.Domain.Role;
import com.encore.ordering.Member.Dto.MemberResponseDto;
import com.encore.ordering.Member.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {
//    CommandLineRunner를 통해 스프링 빈으로 등록되는 시점에 run 메서드 실행
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialDataLoader(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(memberRepository.findByEmail("admin@test.com").isEmpty()){
            Member adminMember = Member.builder()
                    .name("admin")
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .build();
            memberRepository.save(adminMember);
        }
    }
}
