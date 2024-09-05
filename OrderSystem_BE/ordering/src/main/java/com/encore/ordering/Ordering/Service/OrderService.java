package com.encore.ordering.Ordering.Service;


import com.encore.ordering.Item.Domain.Item;
import com.encore.ordering.Item.Repository.ItemRepository;
import com.encore.ordering.Member.Domain.Member;
import com.encore.ordering.Member.Repository.MemberRepository;
import com.encore.ordering.OrderItem.Domain.OrderItem;
import com.encore.ordering.OrderItem.Repository.OrderItemRepository;
import com.encore.ordering.Ordering.Domain.Ordering;
import com.encore.ordering.Ordering.Dto.OrderReq;
import com.encore.ordering.Ordering.Dto.OrderResDto;
import com.encore.ordering.Ordering.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private  final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }




    public List<OrderResDto> orderList(OrderResDto orderResDto){
        List<Ordering> orderings = orderRepository.findAll();
        return orderings.stream().map(o->OrderResDto.toDto(o)).collect(Collectors.toList());
    }

    public Ordering create(List<OrderReq> orderReqs) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("not found email"));
        Ordering ordering = Ordering.builder().member(member).build();
        for(OrderReq dto : orderReqs){
            Item item = itemRepository.findById(dto.getItemId()).orElseThrow(()->new EntityNotFoundException("not found item"));
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .ordering(ordering)
                    .quantity(dto.getCount())
                    .build();
            ordering.getOrderItems().add(orderItem);
            if(item.getStockQuantity() - dto.getCount() < 0) {
                throw new IllegalArgumentException("재고 부족");
            }
            orderItem.getItem().updateStockQuantity(item.getStockQuantity() - dto.getCount());
        }
        return orderRepository.save(ordering);
    }

    public Ordering orderCancel(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Ordering ordering = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found order"));
        if (!ordering.getMember().getEmail().equals(email) && !authentication.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        ordering.OrderCanceled();
        for(OrderItem orderItem : ordering.getOrderItems()){
            orderItem.getItem().updateStockQuantity(orderItem.getItem().getStockQuantity() + orderItem.getQuantity());
        }

        return ordering;
    }

    public List<OrderResDto> findByMember(Long id){
//        Member member = memberRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("not found member"));
//        List<Ordering> orderings = member.getOrderings();
        List<Ordering> orderings = orderRepository.findByMemberId(id);
        return orderings.stream().map(o->OrderResDto.toDto(o)).collect(Collectors.toList());
    }

    public List<OrderResDto> findMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("not found order"));
        List<Ordering> orderings = member.getOrderings();
        return orderings.stream().map(o->OrderResDto.toDto(o)).collect(Collectors.toList());
    }
}