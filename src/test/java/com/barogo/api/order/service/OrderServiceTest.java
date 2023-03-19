package com.barogo.api.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.barogo.api.order.OrderDataInterface;
import com.barogo.api.order.code.OrderStatus;
import com.barogo.api.order.dto.OrderSearchRequest;
import com.barogo.api.order.entity.Order;
import com.barogo.api.order.repository.OrderRepository;
import com.barogo.api.user.entity.User;
import com.barogo.api.user.service.UserService;
import com.barogo.common.constant.ErrorMessage;
import com.barogo.common.exception.APIException;
import com.barogo.common.exception.NotExistDataException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayName("인가 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest implements OrderDataInterface {

  @InjectMocks
  OrderService orderService;

  @Mock
  OrderRepository orderRepository;
  @Mock
  UserService userService;


  @DisplayName("PK id 로 조회")
  @Test
  void getById() {
    // given
    var id = 1L;
    given(orderRepository.findById(any(Long.class))).willReturn(Optional.of(Order.builder().id(id).build()));
    // when
    var result = orderService.get(id);
    // then
    then(orderRepository).should(times(1)).findById(id);
    assertEquals(result.id(), id);
  }

  @DisplayName("id 조회 오류")
  @Test
  void notFoundId() {
    // given
    var id = 2L;
    given(orderRepository.findById((any(Long.class)))).willThrow(new NotExistDataException());
    // when
    var exception = assertThrows(NotExistDataException.class, () -> orderService.get(id));
    // then
    assertEquals(ErrorMessage.NOT_EXIST_DATA, exception.getMessage());
  }

  @DisplayName("주문 저장")
  @Test
  void saveOrder() {
    // given
    var request = saveOrderRequest();
    var user = User.builder().id(1L).build();
    given(orderRepository.save(any(Order.class))).willReturn(Order.builder().id(1L).build());
    // when
    var newId = orderService.save(request);
    // the
    assertEquals(1L, newId);
  }

  @DisplayName("주문 조회")
  @Test
  void getOrderList() {
    // given
    var request = OrderSearchRequest.builder().build();
    given(orderRepository.findAllByDeliveryRequestedAtBetween(any(), any())).willReturn(
        List.of(Order.builder().build()));
    // when
    var result = orderService.list(request);
    // the
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @DisplayName("주문 수정 실패")
  @Test
  void failToUpdate() {
    // given
    var id = 1L;
    var request = saveOrderRequest();
    given(orderRepository.findById(id)).willReturn(
        Optional.of(Order.builder().id(1L).status(OrderStatus.DELIVERY_REQUESTED).build()));
    // when
    var result = assertThrows(APIException.class, () -> orderService.update(id, request));
    // then
    assertEquals(ErrorMessage.INVALID_STATUS_FOR_UPDATE_ADDRESS, result.getMessage());
  }

  @DisplayName("결제완료상태변경실패")
  @Test
  void failToPaid() {
    // given
    var id = 1L;
    given(orderRepository.findById(id)).willReturn(
        Optional.of(Order.builder().id(1L).status(OrderStatus.DELIVERY_REQUESTED).build()));
    // when
    var result = assertThrows(APIException.class, () -> orderService.paid(id));
    // then
    assertEquals(ErrorMessage.INVALID_STATUS_FOR_PAID, result.getMessage());
  }

  @DisplayName("배달준비 상태 변경 실패")
  @Test
  void failToDeliveryReady() {
    // given
    var id = 1L;
    given(orderRepository.findById(id)).willReturn(
        Optional.of(Order.builder().id(1L).status(OrderStatus.ORDERED).build()));
    // when
    var result = assertThrows(APIException.class, () -> orderService.deliveryReady(id));
    // then
    assertEquals(ErrorMessage.INVALID_STATUS_FOR_DELIVERY_READY, result.getMessage());
  }

  @DisplayName("배달요청 상태 변경 실패")
  @Test
  void failToDeliveryRequest() {
    // given
    var id = 1L;
    given(orderRepository.findById(id)).willReturn(
        Optional.of(Order.builder().id(1L).status(OrderStatus.ORDERED).build()));
    // when
    var result = assertThrows(APIException.class, () -> orderService.deliveryRequest(id));
    // then
    assertEquals(ErrorMessage.INVALID_STATUS_FOR_DELIVERY_REQUEST, result.getMessage());
  }

  @DisplayName("배달준비상태 조회")
  @Test
  void deliveryReadyList() {
    // given
//    given(orderRepository.findAllByDeliveryRequestedAtGreaterThanEqualAndStatus(any(LocalDateTime.class),
//        OrderStatus.DELIVERY_REQUESTED))
//        .willReturn(List.of(Order.builder().id(1L).build()));
//    // when
//    var result = orderService.deliveryReadyList();
//    // then
//    assertNotNull(result);
//    assertFalse(result.isEmpty());
  }

}