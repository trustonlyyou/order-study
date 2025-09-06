package com.lunghwan.smaple.kcporder.repository;

import com.lunghwan.smaple.kcporder.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
