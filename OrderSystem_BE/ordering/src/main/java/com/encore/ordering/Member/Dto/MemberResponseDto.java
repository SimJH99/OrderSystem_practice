package com.encore.ordering.Member.Dto;

import com.encore.ordering.Member.Domain.Address;
import com.encore.ordering.Member.Domain.Member;
import com.encore.ordering.Member.Domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
    private Role role;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private int orderCount;

    public static MemberResponseDto toMemberResponseDto(Member member){
        MemberResponseDtoBuilder memberResponseDtoBuilder = MemberResponseDto.builder();
        memberResponseDtoBuilder.id(member.getId());
        memberResponseDtoBuilder.name(member.getName());
        memberResponseDtoBuilder.email(member.getEmail());
        memberResponseDtoBuilder.orderCount(member.getOrderings().size());
        Address address = member.getAddress();
        if(address != null){
            builder().city(address.getCity());
            builder().street(address.getStreet());
            builder().zipcode(address.getZipcode());
        }
        return memberResponseDtoBuilder.build();
    }
}
