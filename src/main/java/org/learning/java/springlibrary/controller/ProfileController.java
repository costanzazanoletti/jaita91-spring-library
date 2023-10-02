package org.learning.java.springlibrary.controller;

import org.learning.java.springlibrary.model.User;
import org.learning.java.springlibrary.repository.UserRepository;
import org.learning.java.springlibrary.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public String profile(Model model, Authentication authentication) {
    DatabaseUserDetails loggedUserDetails = (DatabaseUserDetails) authentication.getPrincipal();
    Integer userId = loggedUserDetails.getId();
    User loggedUser = userRepository.findById(userId).get();
    model.addAttribute("user", loggedUser);
    return "users/profile";
  }
}
