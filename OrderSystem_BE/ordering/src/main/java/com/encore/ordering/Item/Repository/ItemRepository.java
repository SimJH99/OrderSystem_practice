package com.encore.ordering.Item.Repository;

import com.encore.ordering.Item.Domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Specification<Item> specification, Pageable pageable);

//    Page<Item> findAllByDelYnAndCategoryLikeOrNameLike(String delYn, String category, String name, Pageable pageable);
}
