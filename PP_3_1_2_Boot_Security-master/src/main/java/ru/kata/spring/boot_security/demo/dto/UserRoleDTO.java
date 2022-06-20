package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;
import ru.kata.spring.boot_security.demo.models.Role;
import java.util.Set;

@Data
public class UserRoleDTO {
    private Long userId;
    private String username;
    private String lastname;
//    private Set<Role> roles;
    private String role;
    private Long age;
    private String email;
    private String password;
    private String passwordConfirm;
}
