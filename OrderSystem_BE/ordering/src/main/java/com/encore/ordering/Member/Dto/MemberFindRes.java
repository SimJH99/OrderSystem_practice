package com.encore.ordering.Member.Dto;

import com.encore.ordering.Member.Domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberFindRes {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String address;

    private Role role;

    private LocalDateTime createdTime;

    private int orderCount;
}
