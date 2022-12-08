package com.vietsoft.security;

import com.vietsoft.model.Role;
import com.vietsoft.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("serial")
public class UserPrincipal extends User implements UserDetails {

  Collection<? extends GrantedAuthority> authorities;

  public static UserPrincipal create(User user, List<Role> roles) {
    // add member roles
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role r: roles) {
      authorities.add(new SimpleGrantedAuthority(r.getValue()));
    }

    UserPrincipal principal = new UserPrincipal(authorities);
    principal.setId(user.getId());
    principal.setUsername(user.getUsername());
    principal.setEmail(user.getEmail());
    principal.setEmailVerified(user.getEmailVerified());
    principal.setFirstName(user.getFirstName());
    principal.setLastName(user.getLastName());
    principal.setFullName(user.getFullName());
    principal.setShortName(user.getShortName());
    principal.setBirthday(user.getBirthday());
    principal.setUpdatedTime(user.getUpdatedTime());
    principal.setCreatedTime(user.getCreatedTime());
    principal.setPhone(user.getPhone());
    principal.setAvatarUrl(user.getAvatarUrl());
    principal.setUpdatedTime(user.getUpdatedTime());

    return principal;
  }

  public UserPrincipal() {
    super();
  }

  public UserPrincipal(Collection<? extends GrantedAuthority> authorities) {
    super();
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserPrincipal that = (UserPrincipal) o;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

}
