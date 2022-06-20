package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String role;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Collection<User> users;

    public Role(Long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public Role(Long roleId) {
        this.roleId = roleId;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        return role.replace("ROLE_", "");
    }
}
