package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.UserRoleDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.io.Console;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserRoleDTO convertUserToDto(User user) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(user.getUserId());
        userRoleDTO.setUsername(user.getUsername());
        userRoleDTO.setLastname(user.getLastname());
        userRoleDTO.setAge(user.getAge());
        userRoleDTO.setEmail(user.getEmail());
        userRoleDTO.setRoleIds(user.getRoles().stream().map(Role::getRoleId).collect(Collectors.toSet()));
        return userRoleDTO;
    }


    @Transactional
    @Override
    public void update(long id, UserRoleDTO user) {
        System.out.println(user.getRoleIds().toString() + "user из формы с массивом ролей");
        User updatedUser = showUser(id);
        updatedUser.setRoles(roleRepository.findRolesByRoleIdIn(user.getRoleIds()));
        System.out.println(updatedUser.getRoles().toString() + "user после установки новой роли");
        updatedUser.setUsername(user.getUsername());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setAge(user.getAge());
    }


    @Override
    public User showUser(long id) {
        Optional<User> dbUser = userRepository.findById(id);
        return dbUser.orElse(new User());
    }

    @Override
    public Collection<User> users() {
        return userRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(user.getRoles());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null) {
            user.setRoles(roleRepository.findByRole("ROLE_USER"));
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public void delete(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found");
        }
        return user;
    }

}
