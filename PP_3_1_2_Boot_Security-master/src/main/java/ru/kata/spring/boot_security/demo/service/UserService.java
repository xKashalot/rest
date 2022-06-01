package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;
import java.util.Set;


public interface UserService {
    User showUser(long id);
    List<User> users();
    boolean save(User user);
    void delete(long id);
    void update(long id, User user);
    Set<Role> getRoles();
}
