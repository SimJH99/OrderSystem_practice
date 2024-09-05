package com.encore.ordering.Ordering.Dto;

import com.encore.ordering.OrderItem.Domain.OrderItem;
import com.encore.ordering.Ordering.Domain.Ordering;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResDto {

    private Long id;

    private String memberEmail;

    private String orderStatus;

    private String createdTime;

    private List<OrderResDto.OrderResItemDto> OrderItems;
    @Data
    public static class OrderResItemDto{
        private Long id;
        private String itemName;
        private int count;
    }

    public static OrderResDto toDto(Ordering ordering){
        OrderResDto orderResDto = new OrderResDto();
        orderResDto.setId(ordering.getId());
        orderResDto.setMemberEmail(ordering.getMember().getEmail());
        orderResDto.setOrderStatus(ordering.getOrderStatus().toString());
        orderResDto.setCreatedTime(ordering.getCreatedTime().toString());
        List<OrderResDto.OrderResItemDto> orderResItemDtos = new ArrayList<>();
        for (OrderItem orderItem : ordering.getOrderItems()){
            OrderResDto.OrderResItemDto dto = new OrderResItemDto();
            dto.setId(orderItem.getItem().getId());
            dto.setItemName(orderItem.getItem().getName());
            dto.setCount(orderItem.getQuantity());
            orderResItemDtos.add(dto);
        }
        orderResDto.setOrderItems(orderResItemDtos);
        return orderResDto;
    }


}
