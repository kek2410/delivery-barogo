package com.barogo.api.delivery.service;

import com.barogo.api.delivery.code.DeliveryStatus;
import com.barogo.api.delivery.dto.DeliverGetRequest;
import com.barogo.api.delivery.dto.DeliveryResponse;
import com.barogo.api.delivery.dto.DeliverySaveRequest;
import com.barogo.api.delivery.entity.Delivery;
import com.barogo.api.delivery.repository.DeliveryRepository;
import com.barogo.api.user.service.UserService;
import com.barogo.common.security.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final UserService userService;

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

  @Transactional(readOnly = true)
  public List<DeliveryResponse> list(DeliverGetRequest request) {
    return deliveryRepository.findAllByDeliveryDateAfterAndDeliveryDateBeforeAndStatus(request.getFromDate(),
            request.getToDate(), DeliveryStatus.READY)
        .stream()
        .map(Delivery::toResponse)
        .toList();
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
