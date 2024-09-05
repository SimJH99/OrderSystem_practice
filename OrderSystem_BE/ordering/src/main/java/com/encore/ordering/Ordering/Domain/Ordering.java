package com.encore.ordering.Ordering.Domain;

import com.encore.ordering.Member.Domain.Member;
import com.encore.ordering.OrderItem.Domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Ordering {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //회원 객체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    //주문 상태
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    //생성 시간
    @CreationTimestamp
    private LocalDateTime createdTime;

    //수정 시간 : 상품 취소할때 update기록
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public void OrderCanceled() {
        this.orderStatus = OrderStatus.CANCELED;
    }

    public Ordering(Member member){
        this.member = member;
    }
}
