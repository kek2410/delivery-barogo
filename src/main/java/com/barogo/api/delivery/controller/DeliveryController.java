package com.barogo.api.delivery.controller;

import com.barogo.api.delivery.service.DeliveryService;
import com.barogo.api.delivery.dto.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

  private final DeliveryService deliveryService;

  @GetMapping("{id}")
  public DeliveryResponse get(@PathVariable Long id) {
    return deliveryService.get(id);
  }

}
