package com.barogo.common.database;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Audit {

  @Comment("등록자")
  @CreatedBy
  protected String createdBy;

  @Comment("등록일시")
  @CreatedDate
  @Builder.Default
  protected LocalDateTime createdAt = LocalDateTime.now();

  @Comment("수정자")
  @LastModifiedBy
  protected String updatedBy;

  @Comment("수정일시")
  @LastModifiedDate
  @Builder.Default
  protected LocalDateTime updatedAt = LocalDateTime.now();

}

