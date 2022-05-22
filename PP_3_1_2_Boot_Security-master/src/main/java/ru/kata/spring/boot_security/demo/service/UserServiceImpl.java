package ru.kata.spring.boot_security.demo.service;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

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

    @Override
    public User showUser(long id) {
        Optional<User> dbUser = userRepository.findById(id);
        return dbUser.orElse(new User());
    }

    @Override
    public void save(User user) {
        user.setRoles(Collections.singleton(new Role(1l, "ROLE_USER")));
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public List<User> users() {
        return userRepository.findAll();
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
        updatedUser.setName(user.getName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setCity(user.getCity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found");
        }
        return user;
    }
}
