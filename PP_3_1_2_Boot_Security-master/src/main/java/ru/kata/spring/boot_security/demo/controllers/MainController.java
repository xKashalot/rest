package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Collection;
import java.util.List;

@Controller
@EnableWebMvc
public class MainController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public MainController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String index(Model model, @AuthenticationPrincipal User user) {
        List<User> users = userService.users();
        Collection<Role> listRoles = userService.getRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", listRoles);
        model.addAttribute("userRepo", userRepository.findByEmail(user.getEmail()));
        model.addAttribute("newUser", new User());
        return "/index";
    }


    @GetMapping("admin/users/{id}")
    public String showById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "/user";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userForm", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("userForm", new User());
        return "login";
    }

}
