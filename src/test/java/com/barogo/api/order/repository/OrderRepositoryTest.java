package com.barogo.api.order.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.barogo.RepositoryTest;
import com.barogo.api.order.code.OrderStatus;
import com.barogo.api.order.entity.Order;
import com.barogo.api.user.entity.User;
import com.barogo.api.user.repository.UserRepository;
import com.barogo.common.constant.UserRole;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("주문 리포지토리 테스트")
@RepositoryTest
class OrderRepositoryTest {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void setUp() {
    var user = userRepository.save(User.builder()
        .name("test")
        .role(UserRole.CUSTOMER)
        .build());
    orderRepository.saveAll(
        List.of(
            Order.builder()
                .deliveryRequestedAt(LocalDateTime.now())
                .zipCode("1234")
                .subAddress("subadress")
                .address("address")
                .user(user)
                .status(OrderStatus.DELIVERY_REQUESTED)
                .build(),
            Order.builder()
                .deliveryRequestedAt(LocalDateTime.now().minusDays(1L))
                .zipCode("1234")
                .subAddress("subadress")
                .address("address")
                .user(user)
                .status(OrderStatus.DELIVERY_REQUESTED)
                .build(),
            Order.builder()
                .deliveryRequestedAt(LocalDateTime.now().minusDays(2L))
                .zipCode("1234")
                .subAddress("subadress")
                .address("address")
                .user(user)
                .build(),
            Order.builder()
                .deliveryRequestedAt(LocalDateTime.now().minusDays(3L))
                .zipCode("1234")
                .subAddress("subadress")
                .address("address")
                .user(user)
                .build()
        )
    );
  }

  @DisplayName("전체 조회")
  @Test
  void listAll() {
    // given
    var from = LocalDateTime.now().minusWeeks(1L);
    var to = LocalDateTime.now().plusDays(1L);
    // when
    var list = orderRepository.findAllByCreatedAtBetween(from, to);
    // then
    assertFalse(list.isEmpty());
  }

  @DisplayName("배송요청된 주문 조회")
  @Test
  void deliveryRequestedOrder() {
    // given
    var from = LocalDateTime.now().minusDays(3L);
    // when
    var list = orderRepository.findAllByDeliveryRequestedAtGreaterThanEqualAndStatus(from,
        OrderStatus.DELIVERY_REQUESTED);
    // the
    assertFalse(list.isEmpty());
    assertEquals(2, list.size());
  }

}