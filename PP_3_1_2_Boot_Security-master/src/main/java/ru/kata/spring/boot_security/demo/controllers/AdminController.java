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

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@EnableWebMvc
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String index(Model model, Principal user) {
        List<User> users = userService.users();
        Collection<Role> listRoles = userService.getRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", listRoles);
        model.addAttribute("userRepo", userRepository.findByEmail(user.getName()));
        return "/index";
    }


    @PostMapping("/view")
    public String showById(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "/";
    }

    @PostMapping("/admin/update")
    public String update(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(id, user);
        return "redirect:/";
    }

    @PostMapping("/admin/")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/";
    }

//    @PostMapping("/admin/edit")
//    public String edit(@RequestParam("id") int id, Model model){
//    model.addAttribute("user", userService.showUser(id));
//    return "/edit";
//    }


//        @GetMapping("/{id}")
//    public String showById(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.showUser(id));
//        return "users/user";
//    } rest

//        @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id){
//        model.addAttribute("user", userService.showUser(id));
//        return "users/edit";
//    } rest


//        @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
//        userService.update(id, user);
//        return "redirect:/users";
//    } rest

//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        userService.delete(id);
//        return "redirect:/users";
//    } rest

}
