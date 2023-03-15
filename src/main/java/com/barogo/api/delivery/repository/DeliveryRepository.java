package com.barogo.api.delivery.repository;

import com.barogo.api.delivery.code.DeliveryStatus;
import com.barogo.api.delivery.entity.Delivery;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

  List<Delivery> findAllByDeliveryDateAfterAndDeliveryDateBeforeAndStatus(LocalDateTime fromDate, LocalDateTime toDate,
      DeliveryStatus status);
}
