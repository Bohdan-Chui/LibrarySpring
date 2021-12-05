package com.bohdan.libraryspring.service;


import com.bohdan.libraryspring.Repository.RoleRepository;
import com.bohdan.libraryspring.Repository.UserRepository;
import com.bohdan.libraryspring.model.Role;
import com.bohdan.libraryspring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<User> findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }


    public Page<User> getAllReaders(Pageable pageable) {
        return userRepository.getUserByRole("reader", pageable);
    }
    public Page<User> getAllWorkers(Pageable pageable) {
        return userRepository.getUserByRole("librarian", pageable);
    }

    public void setActiveById(long id, boolean active){
        User user = userRepository.findById((int)id);
        user.setActive(active);
        userRepository.save(user);
    }
    public User saveLibrarian(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("librarian");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        System.out.println(user);
        return userRepository.save(user);
    }
    public void deleteUserById(long userId){
        User user = userRepository.findById((int) userId);
        userRepository.delete(user);
    }


    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("reader");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        System.out.println(user);
        return userRepository.save(user);
    }

}