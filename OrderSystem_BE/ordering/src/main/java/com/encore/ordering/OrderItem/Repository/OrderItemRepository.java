package com.encore.ordering.OrderItem.Repository;


import com.encore.ordering.OrderItem.Domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderingId(Long id);
}
