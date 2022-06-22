package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserRoleDTO;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("admin/users")
public class AdminController {

    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Collection<User> showAllUser() {
        return userService.users();
    }

    @PostMapping("")
    public User addUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody UserRoleDTO user, @PathVariable("id") int id) {
        userService.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public User showById(@PathVariable("id") long id) {
        return userService.showUser(id);
    }

    @DeleteMapping( "{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
