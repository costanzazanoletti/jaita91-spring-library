package org.learning.java.springlibrary.repository;

import org.learning.java.springlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
