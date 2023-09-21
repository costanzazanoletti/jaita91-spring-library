package org.learning.java.springlibrary.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.learning.java.springlibrary.model.User;
import org.learning.java.springlibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserRepository userRepository;

  // metodo index per mostrare tutti gli User
  @GetMapping
  public String index(Model model) {
    // recupero tutti gli User da database
    List<User> userList = userRepository.findAll();
    // passo la lista di utenti alla view tramite model attribute
    model.addAttribute("userList", userList);
    // ritorno il nome del template
    return "users/list";
  }

  // metodo show per mostrare il dettaglio del singolo User

  // metodo per mostrare la pagina di creazione di uno User
  @GetMapping("/create")
  public String create(Model model) {
    // aggiungo un attributo User vuoto
    model.addAttribute("user", new User());
    // ritorno il template con il form
    return "users/form";
  }

  // metodo create per creare un nuovo User
  @PostMapping("/create")
  public String doCreate(@Valid @ModelAttribute("user") User userForm,
      BindingResult bindingResult) {
    // userForm contiene i dati dello user presi dalla request
    // verifico se ci sono errori
    if (bindingResult.hasErrors()) {
      return "users/form";
    }
    // lo salvo su database
    userRepository.save(userForm);
    return "redirect:/users";
  }

  // metodo edit per modificare uno User esistente

  // metodo delete per cancellare uno User esistente

}
