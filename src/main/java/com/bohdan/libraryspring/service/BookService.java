package com.bohdan.libraryspring.service;

import com.bohdan.libraryspring.Repository.BookRepository;
import com.bohdan.libraryspring.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookService {

    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getAllBooksForReader(Pageable pageable, String name, String publisher ){
        if(!publisher.isEmpty()){
            return bookRepository.findBooksByPublisherContaining(pageable, publisher);
        }
        if(!name.isEmpty()){
            return bookRepository.findBooksByTitleContaining(pageable,name);
        }

        return bookRepository.findBooksByCountGreaterThan(pageable, 0);
    }

    public Page<Book> getALlBooksForAdmin(Pageable pageable){
        return bookRepository.findAll(pageable);
    }
    public void changeCount(long bookId, int cout){
        Book book = bookRepository.findBookById((int)bookId);
        book.setCount(book.getCount() + cout);
        bookRepository.save(book);

    }
    public Book getBook(Book book){
        return bookRepository.findBookByAuthorAndTitle(book.getAuthor(), book.getTitle());
    }
    public Book getBookById(long bookId){
        return  bookRepository.findBookById((int)bookId);
    }
    public  Book saveBook(Book book){
        return bookRepository.save(book);
    }
    public void deleteBook(long bookId){
        bookRepository.deleteById((int) bookId);
    }
    public Sort getSort(String sortParam){
        if(sortParam == null){
            return Sort.by(Sort.Direction.ASC, "title");
        }

        Sort sort = null;
        switch(sortParam){
            case "byName":
                sort = Sort.by(Sort.Direction.ASC, "title");
                break;
            case "byDate":
                sort = Sort.by(Sort.Direction.ASC, "publishedTime");
                break;
            case "byPublisher":
                sort = Sort.by(Sort.Direction.ASC, "publisher");
                break;
            case "byAuthor":
                sort = Sort.by(Sort.Direction.ASC, "author");
                break;
            default:
                sort = Sort.by(Sort.Direction.ASC, "title");
                break;
        }
        return sort;
    }
}
