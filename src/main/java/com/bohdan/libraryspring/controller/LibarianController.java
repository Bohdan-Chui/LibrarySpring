package com.bohdan.libraryspring.controller;

import com.bohdan.libraryspring.model.Card;
import com.bohdan.libraryspring.model.User;
import com.bohdan.libraryspring.service.CardService;
import com.bohdan.libraryspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/librarian")
public class LibarianController {

    @Autowired
    CardService cardService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/orders")
    public ModelAndView catalogView(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orders");
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Card> catalogPage = cardService.getAllUnconfirmedOrders(pageable);
        System.out.println(catalogPage.getTotalElements());
        model.addAttribute("page", catalogPage);
        return modelAndView;
    }
    @PostMapping(value ="/card/delete/{cardId}" )
    public String deleteCard(@PathVariable("cardId") long cardId , HttpServletRequest request){
        cardService.deleteCard(cardId);
        return "redirect:/librarian/orders";
    }
    @PostMapping(value ="/card/confirm/{cardId}" )
    public String confirmCard(@PathVariable("cardId") long cardId , HttpServletRequest request){
        cardService.confirmCard(cardId);
        return "redirect:/librarian/orders";

    }
    @GetMapping(value = "/users")
    public String usersView(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<User> catalogPage = userService.getAllReaders(pageable);
        model.addAttribute("page", catalogPage);

        return "librarianUsers";
    }

}
