package com.barogo.api.order.entity;

import com.barogo.api.delivery.code.DeliveryStatus;
import com.barogo.api.delivery.entity.Delivery;
import com.barogo.api.order.code.OrderStatus;
import com.barogo.api.order.dto.OrderResponse;
import com.barogo.api.order.dto.OrderSaveRequest;
import com.barogo.api.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "t_order")
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  private OrderStatus status = OrderStatus.ORDERED;

  private String address;

  private String subAddress;

  private String zipCode;

  private LocalDateTime deliveryRequestedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk01_t_order"))
  private User user;

  @OneToMany
  @Builder.Default
  private List<Delivery> deliveries = new ArrayList<>();


  public boolean isUpdatableAddress() {
    return OrderStatus.updatableStatus().contains(this.status);
  }

  public boolean canPaid() {
    return this.status.equals(OrderStatus.ORDERED);
  }

  public void paid() {
    this.status = OrderStatus.PAID;
  }

  public boolean canDeliveryReady() {
    return this.status.equals(OrderStatus.PAID);
  }

  public void deliveryReady() {
    this.status = OrderStatus.DELIVERY_READY;
    this.deliveryRequestedAt = LocalDateTime.now();
  }

  public boolean canDeliveryRequest() {
    return this.status.equals(OrderStatus.DELIVERY_READY)
        && this.deliveries.stream()
        .filter(delivery -> !delivery.getStatus().equals(DeliveryStatus.CANCEL))
        .findAny().isEmpty();
  }

  public void deliveryRequestWithDeliver(User delivery) {
    this.status = OrderStatus.DELIVERY_REQUESTED;
    this.deliveries.add(Delivery.builder()
        .deliver(delivery)
        .order(this)
        .build());
  }

  public void update(OrderSaveRequest request) {
    this.address = request.getAddress();
  }


  public OrderResponse toResponse() {
    return OrderResponse.builder()
        .id(id)
        .status(status)
        .address(address)
        .subAddress(subAddress)
        .zipCode(zipCode)
        .build();
  }
}
