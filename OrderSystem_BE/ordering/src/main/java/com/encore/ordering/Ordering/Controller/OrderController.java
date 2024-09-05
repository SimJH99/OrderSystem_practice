package com.encore.ordering.Ordering.Controller;


import com.encore.ordering.Common.CommonResponse;
import com.encore.ordering.Ordering.Domain.Ordering;
import com.encore.ordering.Ordering.Dto.OrderReq;
import com.encore.ordering.Ordering.Dto.OrderResDto;
import com.encore.ordering.Ordering.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    //주문등록
    @PostMapping("/order/create")
    public ResponseEntity<CommonResponse> orderCreate(@RequestBody List<OrderReq> orderReqs){
        Ordering ordering = orderService.create(orderReqs);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED, "order successfully created", ordering.getId()), HttpStatus.CREATED);
    }
    
    //주문취소
//    @PreAuthorize("hasRole('ADMIN) or #email == authentication.principal.username")
    @DeleteMapping("/order/{id}/cancel")
    public ResponseEntity<CommonResponse> orderCancel(@PathVariable Long id){
        Ordering ordering =  orderService.orderCancel(id);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK, "order successfully canceled", ordering.getId()), HttpStatus.OK);
    }

    //주문목록조회
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public List<OrderResDto> orderList(OrderResDto orderResDto){
        List<OrderResDto> orderResDtos = orderService.orderList(orderResDto);
        return orderResDtos;
    }
}
