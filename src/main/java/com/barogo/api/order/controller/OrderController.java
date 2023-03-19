package com.barogo.api.order.controller;

import com.barogo.api.order.dto.OrderResponse;
import com.barogo.api.order.dto.OrderSaveRequest;
import com.barogo.api.order.dto.OrderSearchRequest;
import com.barogo.api.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "주문 관리 API")
@PreAuthorize("hasAuthority('CUSTOMER')")
public class OrderController {

  private final OrderService orderService;

  @Operation(description = "ID 로 조회")
  @GetMapping("/{id}")
  public OrderResponse get(@PathVariable Long id) {
    return orderService.get(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "신규 등록")
  public Long save(@Valid @RequestBody OrderSaveRequest request) {
    return orderService.save(request);
  }

  @GetMapping
  @Operation(description = "기간별 주문 조회")
  public List<OrderResponse> list(@Valid OrderSearchRequest request) {
    return orderService.list(request);
  }

  @PutMapping("/{id}")
  @Operation(description = "배송 주소 변경")
  public void update(@PathVariable Long id, @Valid @RequestBody OrderSaveRequest request) {
    orderService.update(id, request);
  }

  @PutMapping("/{id}/pay")
  @Operation(description = "결제 완료로 상태 변경")
  public void paid(@PathVariable Long id) {
    orderService.paid(id);
  }

  @PutMapping("/{id}/delivery-ready")
  @Operation(description = "배송 준비로 상태 변경")
  public void deliveryReady(@PathVariable Long id) {
    orderService.deliveryReady(id);
  }

  @PreAuthorize("hasAuthority('DELIVER')")
  @GetMapping("/delivery-ready")
  @Operation(description = "배송 준비 주문 조회")
  public List<OrderResponse> deliveryReadyList() {
    return orderService.deliveryReadyList();
  }

  @PutMapping("/{id}/delivery-request")
  @Operation(description = "배송 요청으로 상태 변경")
  public void deliveryRequest(@PathVariable Long id) {
    orderService.deliveryRequest(id);
  }

}
