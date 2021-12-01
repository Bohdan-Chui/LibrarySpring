package com.bohdan.libraryspring.service;

import com.bohdan.libraryspring.Repository.BookRepository;
import com.bohdan.libraryspring.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    final
    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getAllBooksForReader(Pageable pageable){
        return bookRepository.findBooksByCountGreaterThan(pageable, 0);
    }
    public void changeCount(long bookId, int cout){
        Book book = bookRepository.findBookById((int)bookId);
        book.setCount(book.getCount() + cout);
        bookRepository.save(book);

    }
}
