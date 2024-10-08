package com.encore.ordering.Ordering.Repository;


import com.encore.ordering.Ordering.Domain.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Ordering, Long> {
    List<Ordering> findByMemberId(Long id);
}
