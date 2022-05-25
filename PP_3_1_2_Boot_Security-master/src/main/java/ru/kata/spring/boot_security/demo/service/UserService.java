package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;


public interface UserService {
    User showUser(long id);
    boolean save(User user);
    List<User> users();
    void delete(long id);
    void update(long id, User user);
}
