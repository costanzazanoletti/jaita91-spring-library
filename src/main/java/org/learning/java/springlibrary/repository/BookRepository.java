package org.learning.java.springlibrary.repository;

import java.util.List;
import org.learning.java.springlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

  // qui posso aggiungere metodi per query custom
  // metodo che restituisce tutti i libri il cui titolo contiene una stringa di ricerca
  List<Book> findByTitleContaining(String search);
}
