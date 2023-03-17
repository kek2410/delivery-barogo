package com.barogo.api.user.dto;

import com.barogo.common.constant.UserRole;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
public record UserDTO(
    String userId,
    UserRole roles,
    String name,
    boolean isDeleted,
    boolean isLocked) implements UserDetails {

  public String getAuditor() {
    return this.userId;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles == null ? Collections.emptyList() : List.of(roles);
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.isLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return !this.isDeleted;
  }

  public static UserDTO empty() {
    return UserDTO.builder().build();
  }

}
