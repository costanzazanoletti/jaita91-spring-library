package org.learning.java.springlibrary.repository;

import org.learning.java.springlibrary.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {

}
