package org.learning.java.springlibrary.repository;

import java.util.List;
import java.util.Optional;
import org.learning.java.springlibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  // metodo che seleziona tutti gli User il cui nome contiene una stringa di ricerca
  // o il cui cognome contiene un'altra stringa di ricerca
  // select ... from users u where u.firstName = ? and u.lastName = ?
  List<User> findByFirstNameContainingOrLastNameContaining(String firstNameSearch,
      String lastNameSearch);

  // metodo che recupera uno User per email
  Optional<User> findByEmail(String email);
}
