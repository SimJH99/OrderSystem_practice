package com.encore.ordering.OrderItem.Service;


import com.encore.ordering.OrderItem.Domain.OrderItem;
import com.encore.ordering.OrderItem.Dto.OrderItemListRes;
import com.encore.ordering.OrderItem.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;


    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    //주문 번호에 맞는 주문상품조회리스트
    public List<OrderItemListRes> orderItemList(Long id) {
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderingId(id);
        List<OrderItemListRes> orderItemListRes = new ArrayList<>();
        for(OrderItem o : orderItems){
            OrderItemListRes orderItemListRes1 = OrderItemListRes.builder()
                    .orderId(o.getOrdering().getId())
                    .itemId(o.getItem().getId())
                    .count(o.getQuantity())
                    .build();
            orderItemListRes.add(orderItemListRes1);
        }
        return orderItemListRes;
    }
}
