package com.barogo.api.order.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.barogo.AbstractControllerTest;
import com.barogo.api.order.OrderDataInterface;
import com.barogo.api.order.dto.OrderSaveRequest;
import com.barogo.api.order.dto.OrderSearchRequest;
import com.barogo.api.order.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("주문 컨트롤러 테스트")
@WebMvcTest(OrderController.class)
class OrderControllerTest extends AbstractControllerTest implements OrderDataInterface {

  private static final String BASE_URL = "/orders";

  @MockBean
  OrderService orderService;

  @DisplayName("restful id 조회")
  @Test
  void getById() throws Exception {
    // given
    var request = 1L;
    given(orderService.get(any(Long.class))).willReturn(getOrderResponse());
    // when
    var perform = mvc.perform(get(BASE_URL + "/" + request)
        .contentType(CONTENT_TYPE));
    // then
    perform.andExpect(STATUS_IS_OK)
        .andExpect(resultJson(getOrderResponse()));
  }

  @DisplayName("주문 저장")
  @Test
  void saveOrders() throws Exception {
    // given
    given(orderService.save(any(OrderSaveRequest.class))).willReturn(1L);
    // when
    var perform = mvc.perform(post(BASE_URL)
        .contentType(CONTENT_TYPE)
        .content(toJson(saveOrderRequest())));
    // then
    perform.andExpect(STATUS_IS_CREATED)
        .andExpect(resultJson(1L));
  }


  @DisplayName("주문 저장 실패")
  @Test
  void failToSaveOrders() throws Exception {
    // given
    given(orderService.save(any(OrderSaveRequest.class))).willReturn(1L);
    // when
    var perform = mvc.perform(post(BASE_URL)
        .contentType(CONTENT_TYPE)
        .content(toJson(OrderSaveRequest.builder().build())));
    // then
    perform.andExpect(STATUS_IS_BAD_REQUEST);
  }

  @DisplayName("주문조회")
  @Test
  void getListAll() throws Exception {
    // given
    var request = OrderSearchRequest.builder()
        .fromDateTime(LocalDateTime.now().minusDays(3L)).toDateTime(LocalDateTime.now())
        .build();
    given(orderService.list(any(OrderSearchRequest.class))).willReturn(List.of(getOrderResponse()));
    // when
    var perform = mvc.perform(get(BASE_URL)
        .contentType(CONTENT_TYPE)
        .queryParams(toMap(request)));
    // then
    perform.andExpect(STATUS_IS_OK);
  }

  @DisplayName("주문 수정")
  @Test
  void updateOrder() throws Exception {
    // given
    var request = saveOrderRequest();
    // when
    var perform = mvc.perform(put(BASE_URL + "/" + 1L)
        .contentType(CONTENT_TYPE)
        .content(toJson(request)));
    // the
    perform.andExpect(STATUS_IS_OK);
  }

  @DisplayName("배송준비 목록 조회")
  @Test
  void deliveryReadyList() throws Exception {
    // given
    var request = OrderSearchRequest.builder()
        .fromDateTime(LocalDateTime.now().minusDays(3L)).toDateTime(LocalDateTime.now())
        .build();
    given(orderService.deliveryReadyList(any(OrderSearchRequest.class))).willReturn(List.of(getOrderResponse()));
    // when
    var perform = mvc.perform(get(BASE_URL + "/delivery-ready")
        .contentType(CONTENT_TYPE)
        .queryParams(toMap(request)));
    // then
    perform.andExpect(STATUS_IS_OK);
  }

}