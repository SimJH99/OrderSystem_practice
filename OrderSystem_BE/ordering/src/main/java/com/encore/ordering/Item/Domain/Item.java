package com.encore.ordering.Item.Domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Item {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "item_id")
    private Long id;

    //상품 이름
    @Column(nullable = false)
    private String name;
    
    //상품 카테고리
    private String category;
    
    //가격
    @Column(nullable = false)
    private int price;
    
    //상품 재고 갯수
    @Column(nullable = false)
    private int stockQuantity;
    
    //상품 이미지
    @Setter
    private String imagePath;
    
    //삭제 여부
    @Builder.Default
    private String delYn = "N" ;

    //생성 시간
    @CreationTimestamp
    private LocalDateTime createdTime;
    
    //수정 시간
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public void deleteItem(){
        this.delYn = "Y";
    }

    public void updateStockQuantity(int newQuantity){
        this.stockQuantity = newQuantity;
    }

    public void orderedQuantity (int stockQuantity){
        this.stockQuantity = this.stockQuantity - stockQuantity;
    }

    public void cancledQuantity (int stockQuantity){
        this.stockQuantity = this.stockQuantity + stockQuantity;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public void UpdateItem(String name, String category, int price, int stockQuantity, String imagePath){
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imagePath = imagePath;
    }
}