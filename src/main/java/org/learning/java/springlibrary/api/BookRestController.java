package org.learning.java.springlibrary.api;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.learning.java.springlibrary.model.Book;
import org.learning.java.springlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/books")
public class BookRestController {

  @Autowired
  private BookRepository bookRepository;

  // ciascun metodo della classe rappresenta un endpoint

  // metodo che restituisce la lista di tutti i Book
  @GetMapping
  public List<Book> list() {
    List<Book> bookList = bookRepository.findAll();
    return bookList;
  }

  // metodo che restituisce il dettaglio del singolo libro
  @GetMapping("/{id}")
  public Book detail(@PathVariable("id") Integer id) {
    Optional<Book> result = bookRepository.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + " not found");
    }
  }

  // metodo per creare un nuovo Book
  @PostMapping
  public Book create(@Valid @RequestBody Book book) {
    Book createdBook = bookRepository.save(book);
    return createdBook;
  }

  // metodo per cancellare un Book
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    Optional<Book> result = bookRepository.findById(id);
    if (result.isPresent()) {
      try {
        bookRepository.deleteById(id);
      } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Book with id " + id + " cannot be deleted");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + " not found");
    }
  }

  // metodo per modificare un Book esistente
  @PutMapping("/{id}")
  public Book update(@Valid @RequestBody Book book, @PathVariable Integer id) {
    Optional<Book> result = bookRepository.findById(id);
    if (result.isPresent()) {
      book.setId(id);
      return bookRepository.save(book);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + " not found");
    }
  }
}
