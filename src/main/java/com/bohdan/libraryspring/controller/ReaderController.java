package com.bohdan.libraryspring.controller;

import com.bohdan.libraryspring.model.Book;
import com.bohdan.libraryspring.model.Card;
import com.bohdan.libraryspring.model.User;
import com.bohdan.libraryspring.service.BookService;
import com.bohdan.libraryspring.service.CardService;

import com.bohdan.libraryspring.util.PaginationUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping(value = "/reader")
public class ReaderController {

    @Autowired
    BookService bookService;

    @Autowired
    CardService cardService;

    @GetMapping(value = "/catalog")
    public ModelAndView catalogView(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(name = "name", required = false, defaultValue = "") String name,
                                    @RequestParam(name = "publisher", required = false, defaultValue = "") String publisher,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("readerCatalog");
        Sort sort = bookService.getSort((String)request.getSession().getAttribute("sort"));
        Pageable pageable = PageRequest.of(page-1, size, sort);
        Page<Book> catalogPage = bookService.getAllBooksForReader(pageable, name, publisher);
        PaginationUtil.setPaginationAttributes(model,catalogPage,page, size);
        model.addAttribute("page", catalogPage);
        request.setAttribute("name","");
        log.info("catalog page set up");
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
        model.addAttribute(user);
        model.addAttribute("page", catalogPage);
        log.info("cabinet page set up");
        return modelAndView;
    }
    @PostMapping(value ="/book/inLibrary/{bookId}" )
    public String takeBookInLibrary(@PathVariable("bookId") long bookId , HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        cardService.saveCard("inLibrary", bookId, user.getId());
        log.info("card saved to library with bookid "+ bookId);
        return "redirect:/reader/catalog";
    }
    @PostMapping(value ="/book/toHome/{bookId}" )
    public String takeBooktoHome(@PathVariable("bookId") long bookId , HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        cardService.saveCard("toHome",bookId, user.getId());
        log.info("card saved to home with bookid "+ bookId);
        return "redirect:/reader/catalog";

    }
    @PostMapping(value ="/book/return/{cardId}" )
    public String returnBook(@PathVariable("cardId") long cardId){
        cardService.deleteCard(cardId);
        log.info("deleted card with id: " + cardId);
        return "redirect:/reader/cabinet";
    }
    @GetMapping("/catalog/sort")
    public String changeSortOrder(@RequestParam(name = "sort", required = false, defaultValue = "byName") String sort,
                                  HttpServletRequest request){
        log.info("sort" + sort);
        request.getSession().setAttribute("sort", sort);
        return "redirect:/reader/catalog";
    }

}