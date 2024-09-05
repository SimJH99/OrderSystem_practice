package com.encore.ordering.Member.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    //시,군,구
    private String city;
    //~로,
    private String street;
    //우편번호
    private String zipcode;
}
