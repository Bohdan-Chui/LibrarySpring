package com.bohdan.libraryspring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer id;

    @Column(name = "title")
    @NotEmpty(message = "*Provide a title")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "*Provide a author")
    private String author;

    @Column(name = "publisher")
    @NotEmpty(message = "*Provide a publisher")
    private String publisher;

    @Column(name = "count")
    private Integer count;

    @Column(name = "publishedTime")
    private Date publishedTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Card> cards;
}
