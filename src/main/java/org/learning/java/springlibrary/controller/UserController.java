package org.learning.java.springlibrary.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.learning.java.springlibrary.model.User;
import org.learning.java.springlibrary.repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserRepository userRepository;

  // metodo index per mostrare tutti gli User
  @GetMapping
  public String index(@RequestParam(name = "keyword") Optional<String> searchKeyword,
      Model model) {
    // preparo la List di utenti da passare come attributo
    List<User> userList;
    // preparo la variabile con il valore con cui precaricare l'input di ricerca
    String keyword = "";
    // verifico se ho la stringa di ricerca
    if (searchKeyword.isPresent()) {
      keyword = searchKeyword.get();
      // devo usare il metodo del repository che fa la ricerca filtrata
      userList = userRepository.findByFirstNameContainingOrLastNameContaining(keyword, keyword);
    } else {
      // recupero tutti gli User da database
      userList = userRepository.findAll();
    }
    // passo la lista di utenti alla view tramite model attribute
    model.addAttribute("userList", userList);
    // passo anche l'attributo keyword con la chiave di ricerca
    model.addAttribute("keyword", keyword);
    // ritorno il nome del template
    return "users/list";
  }

  // metodo show per mostrare il dettaglio del singolo User

  // metodo per mostrare la pagina di creazione di uno User
  @GetMapping("/create")
  public String create(Model model) {
    // aggiungo un attributo User vuoto
    model.addAttribute("user", new User());
    // model.addAttribute("isEdit", false);
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
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable("id") Integer userId, Model model) {
    // legge l'id dello User dal path
    // recupero i dati di quello User da database
    Optional<User> result = userRepository.findById(userId);
    // verifico se lo User con quell'id è presente
    if (result.isPresent()) {
      // aggiungo al model l'attributo user con l'oggetto User
      model.addAttribute("user", result.get());
      // model.addAttribute("isEdit", true);
      // ritorno il template con il form
      return "users/form";
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "User with id " + userId + " not found");
    }
  }

  @PostMapping("/edit/{id}")
  public String doEdit(@PathVariable("id") Integer userId,
      @Valid @ModelAttribute("user") User formUser, BindingResult bindingResult) {
    // associo l'id dal path variable allo user che arriva dal form
    formUser.setId(userId);
    // verifico la validazione
    if (bindingResult.hasErrors()) {
      // restituisco la view del form con gli errori
      return "/users/form";
    }
    // se non è scattata la validazione procedo a salvare
    // salvo lo User su database
    userRepository.save(formUser);
    // rimando alla lista di User
    return "redirect:/users";

  }

  // metodo delete per cancellare uno User esistente
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable("id") Integer userId) {
    // legge l'id dello User dal path
    // cancella lo User da database
    userRepository.deleteById(userId);
    // fa una redirect alla lista di User
    return "redirect:/users";
  }

}
