package com.barogo.api.delivery.service;

import com.barogo.api.delivery.dto.DeliveryResponse;
import com.barogo.api.delivery.repository.DeliveryRepository;
import com.barogo.api.user.service.UserService;
import com.barogo.common.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final UserService userService;

  @Transactional(readOnly = true)
  public DeliveryResponse get(Long id) {
    return deliveryRepository.findById(id)
        .orElseThrow()
        .toResponse();
  }

  @Transactional
  public void pickUp(Long id) {
    deliveryRepository.findById(id)
        .orElseThrow()
        .setDeliver(userService.getUserByUserId(SecurityUtil.getUserId()));
  }

  @Transactional
  public void delivering(Long id) {
    deliveryRepository.findById(id)
        .orElseThrow()
        .delivering();
  }

  @Transactional
  public void delivered(Long id) {
    deliveryRepository.findById(id)
        .orElseThrow()
        .delivered();
  }
}
