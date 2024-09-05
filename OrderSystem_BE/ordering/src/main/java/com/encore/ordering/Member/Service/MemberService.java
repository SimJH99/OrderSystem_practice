package com.encore.ordering.Member.Service;


import com.encore.ordering.Member.Domain.Member;
import com.encore.ordering.Member.Dto.*;
import com.encore.ordering.Member.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //로그인
    public Member login(LoginReq loginReq) throws IllegalArgumentException {
        //email존재여부 확인
        Member member = memberRepository.findByEmail(loginReq.getEmail()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        //password일치여부
        if(!passwordEncoder.matches(loginReq.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("일치하지 않는 비밀번호 입니다.");
        }
        return member;
    }

    //회원가입 서비스
    public Member memberSave(MemberSaveReq memberSaveReq) {
        if (memberRepository.findByEmail(memberSaveReq.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
        memberSaveReq.setPassword(passwordEncoder.encode(memberSaveReq.getPassword()));
        Member member = Member.toEntity(memberSaveReq);
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return member;
    }

    public MemberFindRes findDetail(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        MemberFindRes memberFindRes = MemberFindRes.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .createdTime(member.getCreatedTime())
                .role(member.getRole())
                .orderCount(member.getOrderings().size())
                .build();
        return memberFindRes;
    }

    //회원 목록 조회
    public List<MemberResponseDto> findAll(MemberListRes memberListRes) {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(m->MemberResponseDto.toMemberResponseDto(m)).collect(Collectors.toList());
    }

    public MemberResponseDto findMyInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return MemberResponseDto.toMemberResponseDto(member);
    }

}
