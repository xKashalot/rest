package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.Id;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class UserRoleDTO {
    private Long userId;
    private String username;
    private String lastname;
    private Long age;
    private String email;
    private List<Long> roleId;
}
