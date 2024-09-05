package com.encore.ordering.Item.Dto;

import lombok.Data;

@Data
public class ItemListRes {
    private String name;

    private int price;

    private int stockQuantity;
}
