package com.bohdan.libraryspring.service;

import com.bohdan.libraryspring.Repository.BookRepository;
import com.bohdan.libraryspring.Repository.CardRepository;
import com.bohdan.libraryspring.Repository.UserRepository;
import com.bohdan.libraryspring.model.Book;
import com.bohdan.libraryspring.model.Card;
import com.bohdan.libraryspring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardService {
    CardRepository cardRepository;
    UserRepository userRepository;
    BookRepository bookRepository;
    BookService bookService;

    @Autowired
    public CardService(CardRepository cardRepository, UserRepository userRepository, BookRepository bookRepository, BookService bookService) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    public Page<Card> getAllUnconfirmedOrders(Pageable pageable){
        return cardRepository.findAllByStatus(pageable, "notConfirmed");
    }
    public Page<Card> getAllReaderCards(User user, Pageable pageable){
        return cardRepository.findAllByUser(user, pageable);
    }
    public void deleteCard(long cardId){
        System.out.println("card id:: " + cardId);
        bookService.changeCount(cardRepository.getById((int) cardId).getBook().getId(), 1);
        cardRepository.deleteById((int) cardId);
    }
    public  void confirmCard(long cardId){
        Card card = cardRepository.getById((int) cardId);
        card.setStatus("confirmed");
        cardRepository.save(card);
    }

    public void saveCard(String place, long bookId, long userId){
        System.out.println("bookId "+ bookId);
        System.out.println("userId "+ userId);
       // System.out.println(bookId.);
         User user = userRepository.findById((int) userId);

         System.out.println(user);
        Book book = bookRepository.findBookById((int)bookId);
        System.out.println(book);

        if(user != null && book !=null){
            Date returnDate = new Date();
            returnDate.getTime();
            if(place != null && place.equals("toHome")){
                returnDate.setMonth(returnDate.getMonth()+1);
            } else if(place != null && place.equals("inLibrary")){
                returnDate.setDate(returnDate.getDate()+1);
            }
            cardRepository.save(new Card(0,place,"notConfirmed",returnDate , 0, book, user));
            bookService.changeCount(bookId, -1);
        }
    }
}
