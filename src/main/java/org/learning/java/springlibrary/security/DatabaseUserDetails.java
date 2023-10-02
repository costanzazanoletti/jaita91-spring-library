package org.learning.java.springlibrary.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.learning.java.springlibrary.model.Role;
import org.learning.java.springlibrary.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DatabaseUserDetails implements UserDetails {

  private Integer id;
  private String username;
  private String password;
  private Set<GrantedAuthority> authorities;

  // il costruttore di DatabaseUserDetails copia i dati di una istanza di User
  // in modo che siano compatibili con l'interfaccia UserDetails
  public DatabaseUserDetails(User user) {
    this.id = user.getId();
    this.username = user.getEmail(); // i miei utenti della library si registrano con l'email
    this.password = user.getPassword();
    this.authorities = new HashSet<>();
    for (Role role : user.getRoles()) {
      // per ogni ruolo dello user aggiungo una authority con lo stesso nome
      this.authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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

  public Integer getId() {
    return id;
  }

}
