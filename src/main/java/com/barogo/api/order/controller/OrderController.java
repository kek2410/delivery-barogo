package com.barogo.api.order.controller;

import com.barogo.api.order.dto.OrderResponse;
import com.barogo.api.order.dto.OrderSaveRequest;
import com.barogo.api.order.dto.OrderSearchRequest;
import com.barogo.api.order.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/{id}")
  public OrderResponse get(@PathVariable Long id) {
    return orderService.get(id);
  }

  @PostMapping
  public Long save(@Valid OrderSaveRequest request) {
    return orderService.save(request);
  }

  @GetMapping
  public List<OrderResponse> list(@Valid OrderSearchRequest request) {
    return orderService.list(request);
  }

  @PutMapping("/{id}")
  public void update(@PathVariable Long id, @Valid OrderSaveRequest request) {
    orderService.update(id, request);
  }

  @PutMapping("/{id}/pay")
  public void paid(@PathVariable Long id) {
    orderService.paid(id);
  }

  @PutMapping("/{id}/delivery-ready")
  public void deliveryReady(@PathVariable Long id) {
    orderService.deliveryReady(id);
  }

  @GetMapping("/delivery-ready")
  public List<OrderResponse> deliveryReadyList(@Valid OrderSearchRequest request) {
    return orderService.deliveryReadyList(request);
  }

  @PutMapping("/{id}/delivery-request")
  public void deliveryRequest(@PathVariable Long id) {
    orderService.deliveryRequest(id);
  }

}
