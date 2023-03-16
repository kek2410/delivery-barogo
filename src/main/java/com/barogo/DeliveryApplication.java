package com.barogo;

import com.barogo.api.order.code.OrderStatus;
import com.barogo.api.order.entity.Order;
import com.barogo.api.order.repository.OrderRepository;
import com.barogo.api.user.entity.User;
import com.barogo.api.user.repository.UserRepository;
import com.barogo.common.constant.UserRole;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DeliveryApplication {

  public static void main(String[] args) {
    var context = SpringApplication.run(DeliveryApplication.class, args);

    BCryptPasswordEncoder bCryptPasswordEncoder = (BCryptPasswordEncoder) context.getBean("bCryptPasswordEncoder");
    UserRepository userRepository = (UserRepository) context.getBean("userRepository");
    OrderRepository orderRepository = (OrderRepository) context.getBean("orderRepository");
    for (var user : getDefaultSetUsers(bCryptPasswordEncoder)) {
      var saved = userRepository.save(user);
      if (user.getRole().equals(UserRole.CUSTOMER)) {
        var orders = getDefaultSetOrders(saved);
        orderRepository.saveAll(orders);
      }
    }
  }

  static List<User> getDefaultSetUsers(BCryptPasswordEncoder bCryptPasswordEncoder) {
    return List.of(
        User.builder()
            .role(UserRole.ADMIN)
            .userId("admin")
            .password(bCryptPasswordEncoder.encode("admin1234!@#$"))
            .email("admin@barogo.com")
            .name("admin")
            .phone("010-1234-1234")
            .build(),
        User.builder()
            .role(UserRole.CUSTOMER)
            .userId("customer")
            .password(bCryptPasswordEncoder.encode("customer1234!@#$"))
            .email("customer@barogo.com")
            .name("customer")
            .phone("010-1234-1235")
            .build(),
        User.builder()
            .role(UserRole.DELIVER)
            .userId("delivery")
            .password(bCryptPasswordEncoder.encode("delivery1234!@#$"))
            .email("delivery@barogo.com")
            .name("delivery")
            .phone("010-1234-1236")
            .build());
  }

  static List<Order> getDefaultSetOrders(User user) {
    return List.of(
        Order.builder()
            .user(user)
            .address("서울시 송파구 잠실동 어딘가123로")
            .subAddress("가로등옆")
            .zipCode("51231")
            .status(OrderStatus.ORDERED)
            .build(),
        Order.builder()
            .user(user)
            .address("서울시 강남구 청담동 비싼동네123로")
            .subAddress("아파트옆 두번째 골목길")
            .zipCode("12345")
            .status(OrderStatus.PAID)
            .build(),
        Order.builder()
            .user(user)
            .address("서울시 강남구 청담동 적당히비싼동네1")
            .subAddress("아파트옆 세번째 골목길")
            .zipCode("12345")
            .status(OrderStatus.DELIVERY_REQUESTED)
            .deliveryRequestedAt(LocalDateTime.now().minusDays(2L))
            .build(),
        Order.builder()
            .user(user)
            .address("서울시 강남구 청담동 적당히매우비싼동네2")
            .subAddress("아파트옆 다섯번째 골목길")
            .zipCode("12345")
            .status(OrderStatus.DELIVERY_REQUESTED)
            .deliveryRequestedAt(LocalDateTime.now())
            .build(),
        Order.builder()
            .user(user)
            .address("서울시 강남구 청담동 아주많이비싼동네")
            .subAddress("아파트옆 첫번째 골목길")
            .zipCode("12345")
            .status(OrderStatus.DELIVERY_REQUESTED)
            .deliveryRequestedAt(LocalDateTime.now().minusWeeks(1L))
            .build()
    );
  }


}
