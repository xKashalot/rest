package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@EnableWebMvc
@RequestMapping()
public class UsersController {

    private final UserServiceImpl userService;

    @Autowired
    public UsersController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.users());
        return "/index";
    }

    @PostMapping("/view")
    public String showById(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "/user";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(id, user);
        return "redirect:/";
    }

    @PostMapping("/")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model){
    model.addAttribute("user", userService.showUser(id));
    return "/edit";
    }


//    @GetMapping("/new")
//    public String newUser(@ModelAttribute("user") User user) {
//        return "registration";
//    }

//    @PostMapping()
//    public String create(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:/";
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
