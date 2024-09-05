package com.encore.ordering.Member.Domain;

import com.encore.ordering.Member.Dto.MemberSaveReq;
import com.encore.ordering.Ordering.Domain.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member{
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //회원 이름
    @Column(nullable = false)
    private String name;

    //이메일
    @Column(nullable = false, unique = true)
    private String email;

    //비밀번호
    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    //회원 권환(관리자, 회원)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    //생성시간
    @CreationTimestamp
    private LocalDateTime createdTime;

    //수정 시간
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    //주문 객체
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ordering> orderings;

    public static Member toEntity(MemberSaveReq memberSaveReq){
        Address address = new Address(memberSaveReq.getCity(), memberSaveReq.getStreet(), memberSaveReq.getZipcode());
            Member member = Member.builder()
            .name(memberSaveReq.getName())
            .email(memberSaveReq.getEmail())
            .password(memberSaveReq.getPassword())
            .address(address)
            .role(Role.USER)
            .build();
        return member;
    }



}
