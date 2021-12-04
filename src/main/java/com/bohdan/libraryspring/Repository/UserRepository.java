package com.bohdan.libraryspring.Repository;

import com.bohdan.libraryspring.model.Role;
import com.bohdan.libraryspring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findById(Integer user_id);
    @Query(value = "SELECT u FROM User u LEFT JOIN u.roles role WHERE role.role = ?1")
    Page<User> getUserByRole(String name, Pageable pageable);


}