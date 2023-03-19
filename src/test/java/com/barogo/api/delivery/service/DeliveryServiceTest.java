package com.barogo.api.delivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.barogo.api.delivery.entity.Delivery;
import com.barogo.api.delivery.repository.DeliveryRepository;
import com.barogo.api.user.service.UserService;
import com.barogo.common.security.SecurityUtil;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("배송 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

  @InjectMocks
  DeliveryService deliveryService;

  @Mock
  DeliveryRepository deliveryRepository;
  @Mock
  UserService userService;

  @DisplayName("PK Id 로 조회")
  @Test
  void getById() {
    // given
    var id = 1L;
    given(deliveryRepository.findById(id)).willReturn(Optional.of(Delivery.builder().id(id).build()));
    // when
    var delivery = deliveryService.get(1L);
    // then
    assertEquals(1L, delivery.id());

  }
}