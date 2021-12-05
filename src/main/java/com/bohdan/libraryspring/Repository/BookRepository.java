package com.bohdan.libraryspring.Repository;


import com.bohdan.libraryspring.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
   // Optional<User> findBookByAuthor(String author);
    Book findBookById(Integer book_id);
    Page<Book> findBooksByCountGreaterThan(Pageable pageable, Integer count);
    Book findBookByAuthorAndTitle(String author, String title);
    Page<Book> findBooksByTitleContaining(Pageable pageable, String title);
    Page<Book> findBooksByPublisherContaining(Pageable pageable, String publisher);


}
