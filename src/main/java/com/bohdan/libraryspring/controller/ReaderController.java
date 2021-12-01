package com.bohdan.libraryspring.controller;

import com.bohdan.libraryspring.model.Book;
import com.bohdan.libraryspring.model.Card;
import com.bohdan.libraryspring.model.User;
import com.bohdan.libraryspring.service.BookService;
import com.bohdan.libraryspring.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/reader")
public class ReaderController {

    @Autowired
    BookService bookService;

    @Autowired
    CardService cardService;

    @GetMapping(value = "/catalog")
    public ModelAndView catalogView(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("readerCatalog");
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Book> catalogPage = bookService.getAllBooksForReader(pageable);
        System.out.println(catalogPage.getTotalElements());
        model.addAttribute("page", catalogPage);
        return modelAndView;
    }

    @GetMapping(value = "/cabinet")
    public ModelAndView cabinetView(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "size", required = false, defaultValue = "10") int size, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("readerCabinet");
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Card> catalogPage = cardService.getAllReaderCards(user, pageable);
        System.out.println(catalogPage.getTotalElements());
        model.addAttribute(user);
        model.addAttribute("page", catalogPage);
        return modelAndView;
    }
    @PostMapping(value ="/book/inLibrary/{bookId}" )
    public String takeBookInLibrary(@PathVariable("bookId") long bookId , HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        cardService.saveCard("inLibrary", bookId, user.getId());
        return "redirect:/reader/catalog";
    }
    @PostMapping(value ="/book/toHome/{bookId}" )
    public String takeBooktoHome(@PathVariable("bookId") long bookId , HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        cardService.saveCard("toHome",bookId, user.getId());

        return "redirect:/reader/catalog";

    }
    @PostMapping(value ="/book/return/{cardId}" )
    public String returnBook(@PathVariable("cardId") long cardId , HttpServletRequest request){

        cardService.deleteCard(cardId);

        return "redirect:/reader/cabinet";

    }

}