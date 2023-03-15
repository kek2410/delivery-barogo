package com.barogo.api.delivery.entity;

import com.barogo.api.delivery.code.DeliveryStatus;
import com.barogo.api.delivery.dto.DeliveryResponse;
import com.barogo.api.order.entity.Order;
import com.barogo.api.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  @OneToOne
  @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk01_t_deliver"))
  private Order order;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(foreignKey = @ForeignKey(name = "fk02_t_deliver"))
  private User deliver;

  public boolean canChangeAddress() {
    return this.status.equals(DeliveryStatus.READY);
  }

  public void setDeliver(User user) {
    this.deliver = user;
    this.status = DeliveryStatus.PICKUP;
  }

  public void delivering() {
    this.status = DeliveryStatus.DELIVERING;
  }

  public void delivered() {
    this.status = DeliveryStatus.DELIVERED;
  }

  public DeliveryResponse toResponse() {
    return DeliveryResponse.builder()
        .id(id)
        .status(status)
        .build();
  }
}
