package com.bohdan.libraryspring.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @NotEmpty(message = "*Provide a count")
    private Integer count;

    @Column(name = "publishedTime")
    @NotEmpty(message = "*Provide a publishedTime")
    private Calendar publishedTime;
}
