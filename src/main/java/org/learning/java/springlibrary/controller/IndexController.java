package org.learning.java.springlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  @GetMapping
  public String index() {
    return "redirect:/books"; // non è nome di un template ma la nuova rotta del browser
  }
}
