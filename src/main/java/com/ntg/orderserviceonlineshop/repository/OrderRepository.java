package com.ntg.orderserviceonlineshop.repository;

import com.ntg.orderserviceonlineshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
