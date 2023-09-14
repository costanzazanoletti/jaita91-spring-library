package org.learning.java.springlibrary.controller;

import java.util.List;
import org.learning.java.springlibrary.model.Book;
import org.learning.java.springlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
