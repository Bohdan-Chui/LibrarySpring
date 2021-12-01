package com.bohdan.libraryspring.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer id;

    @Column(name = "place")
    @NotEmpty(message = "*Provide a place")
    private String place;

    @Column(name = "status")
    @NotEmpty(message = "*Provide a status")
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "returnDate")
    private Date returnDate;

    @Column(name = "fine")
    private Integer fine;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
