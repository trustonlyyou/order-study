package com.lunghwan.smaple.kcporder.repository;

import com.lunghwan.smaple.kcporder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
