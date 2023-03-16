package com.barogo.api.delivery.controller;

import com.barogo.api.delivery.dto.DeliveryResponse;
import com.barogo.api.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

  private final DeliveryService deliveryService;

  @GetMapping("/{id}")
  public DeliveryResponse get(@PathVariable Long id) {
    return deliveryService.get(id);
  }

  @PutMapping("/{id}/pick-up")
  public void pickUp(@PathVariable Long id) {
    deliveryService.pickUp(id);
  }

  @PutMapping("/{id}/delivering")
  public void delivering(@PathVariable Long id) {
    deliveryService.delivering(id);
  }

  @PutMapping("/{id}/delivered")
  public void delivered(@PathVariable Long id) {
    deliveryService.delivered(id);
  }

}
