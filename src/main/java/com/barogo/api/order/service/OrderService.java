package com.barogo.api.order.service;

import com.barogo.api.order.code.OrderStatus;
import com.barogo.api.order.dto.OrderResponse;
import com.barogo.api.order.dto.OrderSaveRequest;
import com.barogo.api.order.dto.OrderSearchRequest;
import com.barogo.api.order.entity.Order;
import com.barogo.api.order.repository.OrderRepository;
import com.barogo.api.user.service.UserService;
import com.barogo.common.constant.ErrorCode;
import com.barogo.common.exception.APIException;
import com.barogo.common.exception.NotExistDataException;
import com.barogo.common.security.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final UserService userService;

  @Transactional(readOnly = true)
  public OrderResponse get(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new APIException(ErrorCode.NOT_EXIST_USER))
        .toResponse();
  }

  @Transactional
  public Long save(OrderSaveRequest request) {
    var user = userService.getUserByUserId(SecurityUtil.getUserId());
    return orderRepository.save(request.toEntityWith(user))
        .getId();
  }

  @Transactional(readOnly = true)
  public List<OrderResponse> list(OrderSearchRequest request) {
    return orderRepository.findAllByDeliveryRequestedAtBetween(request.getFromDateTime(), request.getToDateTime())
        .stream()
        .map(Order::toResponse)
        .toList();
  }

  @Transactional
  public void update(Long id, OrderSaveRequest request) {
    var order = orderRepository.findById(id).orElseThrow(NotExistDataException::new);
    if (!order.isUpdatableAddress()) {
      throw new APIException();
    }
    order.update(request);
  }

  @Transactional
  public void paid(Long id) {
    var order = orderRepository.findById(id).orElseThrow(NotExistDataException::new);
    if (!order.canPaid()) {
      throw new APIException(ErrorCode.INVALID_STATUS_FOR_PAID);
    }
    order.paid();
  }

  @Transactional
  public void deliveryReady(Long id) {
    var order = orderRepository.findById(id).orElseThrow(NotExistDataException::new);
    if (!order.canDeliveryReady()) {
      throw new APIException(ErrorCode.INVALID_STATUS_FOR_DELIVERY_READY);
    }
    order.deliveryReady();
  }

  @Transactional(readOnly = true)
  public List<OrderResponse> deliveryReadyList(OrderSearchRequest request) {
    return orderRepository.findAllByDeliveryRequestedAtBetweenAndStatus(
            request.getFromDateTime(), request.getToDateTime(), OrderStatus.DELIVERY_REQUESTED)
        .stream()
        .map(Order::toResponse)
        .toList();
  }

  @Transactional
  public void deliveryRequest(Long id) {
    var order = orderRepository.findById(id).orElseThrow(NotExistDataException::new);
    if (!order.canDeliveryRequest()) {
      throw new APIException(ErrorCode.INVALID_STATUS_FOR_DELIVERY_REQUEST);
    }
    var deliver = userService.getUserByUserId(SecurityUtil.getUserId());
    order.deliveryRequestWithDeliver(deliver);
  }
}
