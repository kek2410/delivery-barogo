package com.barogo.api.user.delivery.service;

import com.barogo.api.user.delivery.dto.DeliveryResponse;
import com.barogo.api.user.delivery.dto.DeliverySaveRequest;
import com.barogo.api.user.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;

  @Transactional
  public void save(DeliverySaveRequest request) {
    deliveryRepository.save(request.toEntity());
  }

  @Transactional(readOnly = true)
  public DeliveryResponse get(Long id) {
    return deliveryRepository.findById(id)
        .orElseThrow()
        .toResponse();
  }
}
