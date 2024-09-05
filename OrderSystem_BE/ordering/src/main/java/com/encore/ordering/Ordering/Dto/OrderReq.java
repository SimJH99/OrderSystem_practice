package com.encore.ordering.Ordering.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReq {
    private Long itemId;
    private int count;
}


/*
    "orderReqItemDtos" : [
    {"itemId" : 1, "count" : 10}
    {"itemId" : 2, "count" : 20}
    ]
 */

// 예시데이터
/*
{
    "itemId" : [1,2,3], "count" : [10,20,25]
}
 */
