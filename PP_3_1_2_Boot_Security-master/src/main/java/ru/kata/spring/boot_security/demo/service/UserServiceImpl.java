package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User showUser(long id) {
        Optional<User> dbUser = userRepository.findById(id);
        return dbUser.orElse(new User());
    }

    @Override
    public List<User> users() {
        return userRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());

        if (userFromDB != null) {
            return false;
        }

        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(roleRepository.getById(1L)));
        }

        user.setRoles(user.getRoles());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
    @Transactional
    public void update(long id, User user) {
        User updatedUser = showUser(id);
        updatedUser.setUsername(user.getUsername());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setAge(user.getAge());
        updatedUser.setRoles(user.getRoles());
    }

    @Override
    public Set<Role> getRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found");
        }
        //return new User(user.getEmail(), user.getPassword(), rolesToAuthorities(user.getRoles()));
        return user;
    }


//    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles){
//        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
//    }

    //    @Override
//    public User showUser(int id) {
//       return (User) entityManager.createQuery("from User where id = :id").setParameter("id", id).getSingleResult();
//    }
//
//    @Override
//    @Transactional
//    public void save(User user) {
//        entityManager.persist(user);
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public List<User> users() {
//        return entityManager.createQuery("from User").getResultList();
//    }
//
//    @Transactional
//    public void delete(int id) {
//        entityManager.createQuery("delete from User where id = :id").setParameter("id", id).executeUpdate();
//    }
}
