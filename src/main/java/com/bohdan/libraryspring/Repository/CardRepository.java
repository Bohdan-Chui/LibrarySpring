package com.bohdan.libraryspring.Repository;

import com.bohdan.libraryspring.model.Card;
import com.bohdan.libraryspring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Card getById(Integer id);

    Page<Card> findAllByUser(User user, Pageable pageable);

    Page<Card> findAllByStatus(Pageable pageable, String status);

}