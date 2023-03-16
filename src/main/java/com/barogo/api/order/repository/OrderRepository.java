package com.barogo.api.order.repository;

import com.barogo.api.order.code.OrderStatus;
import com.barogo.api.order.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

//  List<Order> findAllBy

  List<Order> findAllByDeliveryRequestedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);

  List<Order> findAllByDeliveryRequestedAtBetweenAndStatus(LocalDateTime fromDate, LocalDateTime toDate,
      OrderStatus status);

}
