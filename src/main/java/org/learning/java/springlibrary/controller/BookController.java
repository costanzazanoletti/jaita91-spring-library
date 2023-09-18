package org.learning.java.springlibrary.controller;

import java.util.List;
import java.util.Optional;
import org.learning.java.springlibrary.model.Book;
import org.learning.java.springlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/books")
public class BookController {

  @Autowired
  // dependency injection: quando si crea un oggetto di tipo BookController ha bisogno di un BookRepository
  private BookRepository bookRepository;

  // metodo index che mostra la lista di tutti i Book
  @GetMapping
  public String index(Model model) {
    List<Book> bookList = bookRepository.findAll(); // questa è la lista di libri presa da database
    model.addAttribute("books", bookList); // passo la lista di libri al model
    return "books/list";
  }

  // metodo show che mostra il dettaglio di un libro preso per id

  @GetMapping("/show/{bookId}")
  public String show(@PathVariable("bookId") Integer id, Model model) {
    System.out.println("Id: " + id); // solo per debug
    // uso l'id preso dal path della request per cercare quel book su database
    // chiedo al bookRepository di fare un findById che può oppure no ritornare un Book
    Optional<Book> bookOptional = bookRepository.findById(id);
    // se nell'optional c'è il book proseguo e lo passo alla view
    if (bookOptional.isPresent()) {
      Book bookFromDB = bookOptional.get(); // chiedo all'optional di darmi il suo contenuto
      model.addAttribute("book", bookFromDB);
      return "books/detail";
    } else {
      // se l'opional è vuoto sollevo un'eccezione
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


  }
}
