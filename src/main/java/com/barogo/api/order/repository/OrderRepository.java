package com.barogo.api.order.repository;

import com.barogo.api.order.code.OrderStatus;
import com.barogo.api.order.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findAllByDeliveryRequestedAtAfterAndDeliveryRequestedAtBefore(LocalDateTime fromDate, LocalDateTime toDate);

  List<Order> findAllByDeliveryRequestedAtAfterAndDeliveryRequestedAtBeforeAndStatus(LocalDateTime fromDate, LocalDateTime toDate,
      OrderStatus status);
}
