package com.bohdan.libraryspring.service;


import com.bohdan.libraryspring.Repository.RoleRepository;
import com.bohdan.libraryspring.Repository.UserRepository;
import com.bohdan.libraryspring.model.Role;
import com.bohdan.libraryspring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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


    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("reader");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        System.out.println(user);
        return userRepository.save(user);
    }

}