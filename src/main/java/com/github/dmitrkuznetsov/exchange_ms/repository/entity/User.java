package com.github.dmitrkuznetsov.exchange_ms.repository.entity;

import com.github.dmitrkuznetsov.exchange_ms.dto.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private List<Wallet> wallets;

  public void addWallet(Wallet wallet) {
    if (wallets == null) {
      wallets = new ArrayList<>();
    }

    wallets.add(wallet);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
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
}
