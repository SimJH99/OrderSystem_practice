package com.encore.ordering.Item.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemReqDto {
    private String name;
    private String category;
    private int price;
    private int stockQuantity;
    private MultipartFile imagePath;
}
