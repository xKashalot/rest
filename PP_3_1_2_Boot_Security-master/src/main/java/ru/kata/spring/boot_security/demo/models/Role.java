package ru.kata.spring.boot_security.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String role;

    public Role(Long roleId) {
        this.roleId = roleId;
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
