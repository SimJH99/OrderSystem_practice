package com.encore.ordering.OrderItem.Controller;


import com.encore.ordering.OrderItem.Dto.OrderItemListRes;
import com.encore.ordering.OrderItem.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/orderitem/{id}")
    public List<OrderItemListRes> OrderItemList(@PathVariable Long id){
        return orderItemService.orderItemList(id);
    }
}