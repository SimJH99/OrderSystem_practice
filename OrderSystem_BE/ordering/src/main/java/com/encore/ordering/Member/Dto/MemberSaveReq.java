package com.encore.ordering.Member.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveReq {
    //회원 이름
    @NotEmpty(message = "name is essential")
    private String name;

    //이메일
    @Email(message = "email is not valid")
    @NotEmpty(message = "email is essential")
    private String email;

    //비밀번호
    @Size(min = 4, message = "minimum length is 4")
    @NotEmpty(message = "password is essential")
    private String password;

    private String city;

    private String street;

    private String zipcode;
}
