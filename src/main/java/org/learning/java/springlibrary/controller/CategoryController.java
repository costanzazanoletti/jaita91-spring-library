package org.learning.java.springlibrary.controller;

import org.learning.java.springlibrary.model.Category;
import org.learning.java.springlibrary.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

  @Autowired
  private CategoryRepository categoryRepository;

  // metodo index che mostra la lista delle categorie e anche il form per aggiungere una categoria
  @GetMapping
  public String index(Model model) {
    // devo passare al model un attributo categoryList con la lista di categorie
    model.addAttribute("categoryList", categoryRepository.findAll());
    // devo passare al model un attributo categoryObj da mappare sul form
    model.addAttribute("categoryObj", new Category());
    return "categories/index";
  }

  // metodo doCreate che salva la nuova categoria
  @PostMapping("/create")
  public String doCreate(@ModelAttribute("categoryObj") Category categoryForm,
      RedirectAttributes redirectAttributes) {
    // prende l'oggetto Category dalla request
    // lo salva su database
    categoryRepository.save(categoryForm);
    // aggiungo ai redirectAttributes un flasAttribute con un messaggio di conferma
    redirectAttributes.addFlashAttribute("message", "Category successfully added!");
    // fa la redirect alla pagina index
    return "redirect:/categories";
  }

  // metodo delete che elimina la categoria presa per id
  @PostMapping("/delete/{catId}")
  public String delete(@PathVariable("catId") Integer id) {
    // prendo l'id della categoria da cancellare dalla request
    // elimino da database la categoria
    categoryRepository.deleteById(id);
    // faccio la redirect alla index
    return "redirect:/categories";
  }
}
