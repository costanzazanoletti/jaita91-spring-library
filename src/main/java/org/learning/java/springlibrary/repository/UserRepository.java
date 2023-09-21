package org.learning.java.springlibrary.repository;

import org.learning.java.springlibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
