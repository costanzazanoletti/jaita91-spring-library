package org.learning.java.springlibrary.security;

import java.util.Optional;
import org.learning.java.springlibrary.model.User;
import org.learning.java.springlibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // nella library lo username degli utenti corrisponde alla email
    // cerco nel repository degli User uno User con email = username
    Optional<User> userResult = userRepository.findByEmail(username);
    // se lo trovo
    if (userResult.isPresent()) {
      // costruisco un oggetto DatabaseUserDetails a partire da quello User
      User user = userResult.get();
      return new DatabaseUserDetails(user);
    } else {
      // se non lo trovo sollevo una eccezione
      throw new UsernameNotFoundException("Username with email " + username + " not found");
    }

  }
}
