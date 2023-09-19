package org.learning.java.springlibrary.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.learning.java.springlibrary.model.Book;
import org.learning.java.springlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  // controller che mostra la pagina di creazione di un Book
  @GetMapping("/create") // url
  public String create(Model model) {
    // aggiungiamo al model un attributo di tipo Book
    model.addAttribute("book", new Book());

    return "books/form"; // template
  }

  // metodo che gestisce la POST di creazione di un Book
  /*
   * l'annnotation @Valid davanti al parametro formBook fa scattare la validazione degli attributi
   * di Book che hanno delle annotation di validazione (es. @Notblank)
   * Gli errori di validazione vengono raccolti nella mappa BindingResult bindingResult
   * */
  @PostMapping("/create")
  public String doCreate(@Valid @ModelAttribute("book") Book formBook,
      BindingResult bindingResult) {
    // formBook è un oggetto Book costruito con i dati che arrivano dalla request, quindi dal form

    // prima di salvare il book verifico che non ci siano errori di validazione
    if (bindingResult.hasErrors()) {
      return "books/form"; // template
    }

    // posso manipolare l'oggetto formBook prima di salvarlo
    formBook.setTitle(formBook.getTitle().toUpperCase());

    // per salvare il book su database chiama in aiuto il bookRepository
    bookRepository.save(formBook);
    // se il book è stato salvato con successo faccio una redirect alla pagina della lista
    return "redirect:/books";
  }

}
