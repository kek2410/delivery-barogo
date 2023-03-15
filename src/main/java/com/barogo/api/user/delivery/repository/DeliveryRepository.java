package com.barogo.api.user.delivery.repository;

import com.barogo.api.user.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
