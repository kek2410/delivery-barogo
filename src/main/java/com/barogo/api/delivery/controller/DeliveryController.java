package com.barogo.api.delivery.controller;

import com.barogo.api.delivery.dto.DeliveryResponse;
import com.barogo.api.delivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "배송정보 관리 API")
@PreAuthorize("hasAnyAuthority('DELIVER')")
public class DeliveryController {

  private final DeliveryService deliveryService;

  @GetMapping("/{id}")
  @Operation(description = "ID 로 조회")
  public DeliveryResponse get(@PathVariable Long id) {
    return deliveryService.get(id);
  }

  @PutMapping("/{id}/pick-up")
  @Operation(description = "배송상품 픽업")
  public void pickUp(@PathVariable Long id) {
    deliveryService.pickUp(id);
  }

  @PutMapping("/{id}/delivering")
  @Operation(description = "배송중으로 상태 변경")
  public void delivering(@PathVariable Long id) {
    deliveryService.delivering(id);
  }

  @PutMapping("/{id}/delivered")
  @Operation(description = "배송완료로 상태 변경")
  public void delivered(@PathVariable Long id) {
    deliveryService.delivered(id);
  }

}
