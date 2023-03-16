package com.barogo.api.user.dto;

import com.barogo.common.constant.UserRole;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements UserDetails {

  private String userId;
  private UserRole roles;
  private String name;
  private boolean isDeleted;
  private boolean isLocked;

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
