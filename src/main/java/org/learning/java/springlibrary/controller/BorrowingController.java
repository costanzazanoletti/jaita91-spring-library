package org.learning.java.springlibrary.controller;

import java.time.LocalDate;
import java.util.Optional;
import org.learning.java.springlibrary.model.Book;
import org.learning.java.springlibrary.model.Borrowing;
import org.learning.java.springlibrary.repository.BookRepository;
import org.learning.java.springlibrary.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

  @Autowired
  private BorrowingRepository borrowingRepository;

  @Autowired
  private BookRepository bookRepository;

  // metodi per aggiungere un borrowing
  @GetMapping("/create")
  public String create(@RequestParam("bookId") Integer bookId, Model model) {
    // dal bookId voglio recuperare il Book
    Optional<Book> bookResult = bookRepository.findById(bookId);
    if (bookResult.isPresent()) {
      // se esiste il book con quell'id proseguo
      Book book = bookResult.get();
      // creo il borrowing da passare alla view
      Borrowing borrowing = new Borrowing();
      // al borrowing associo il book preso da db
      borrowing.setBook(book);
      // precarico i campi date con valori di default
      borrowing.setStartDate(LocalDate.now());
      borrowing.setExpireDate(LocalDate.now().plusDays(30));
      // passo il borrowing configurato alla view tramite model
      model.addAttribute("borrowing", borrowing);
      return "borrowings/form";

    } else {
      // se non esiste sollevo un'eccezione
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "book with id " + bookId + " not found");
    }

  }

  @PostMapping("/create")
  public String doCreate(@ModelAttribute("borrowing") Borrowing borrowingForm) {
    // salvo il borrowing su database
    borrowingRepository.save(borrowingForm);
    // faccio la redirect alla show del book prestato
    return "redirect:/books/show/" + borrowingForm.getBook().getId();
  }

  // metodi per modificare un borrowing
  @GetMapping("/edit/{borrowingId}")
  public String edit(@PathVariable("borrowingId") Integer id, Model model) {
    // recupero da database il Borrowing da modificare prendendolo per id
    Optional<Borrowing> borrowingResult = borrowingRepository.findById(id);
    if (borrowingResult.isPresent()) {
      // passo come attributo del model il Borrowing da modificare
      model.addAttribute("borrowing", borrowingResult.get());
      // ritorno il nome del template
      return "/borrowings/edit";
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/edit/{borrowingId}")
  public String doEdit(@PathVariable("borrowingId") Integer borrowingId,
      @ModelAttribute("borrowing") Borrowing borrowingForm) {
    // setto l'id del borrowing preso dal path variable
    borrowingForm.setId(borrowingId);
    // salvo il borrowing su database
    borrowingRepository.save(borrowingForm);
    // faccio la redirect alla show del book
    return "redirect:/books/show/" + borrowingForm.getBook().getId();
  }

  // metodo per la delete
  @PostMapping("/delete/{borrowingId}")
  public String delete(@PathVariable("borrowingId") Integer id) {
    // prima di cancellare il borrowing devo recuperare l'id del book
    Optional<Borrowing> borrowingResult = borrowingRepository.findById(id);
    if (borrowingResult.isPresent()) {
      Integer bookId = borrowingResult.get().getBook().getId();
      borrowingRepository.deleteById(id);
      return "redirect:/books/show/" + bookId;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

  }


}
