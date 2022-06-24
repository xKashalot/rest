package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;
import java.util.List;


@Data
public class UserRoleDTO {
    private Long userId;
    private String username;
    private String lastname;
    private Long age;
    private String email;
    private List<Long> roleIds;
}
