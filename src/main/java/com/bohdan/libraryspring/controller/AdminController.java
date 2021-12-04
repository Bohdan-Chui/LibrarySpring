package com.bohdan.libraryspring.controller;

import com.bohdan.libraryspring.model.User;
import com.bohdan.libraryspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public String usersView(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<User> catalogPage = userService.getAllReaders(pageable);
        model.addAttribute("page", catalogPage);
        return "adminUsers";
    }
    @PostMapping(value ="/user/block/{userId}" )
    public String blockUser(@PathVariable("userId") long userId ){
        userService.setActiveById(userId, false);
        return "redirect:/admin/users";
    }
    @PostMapping(value ="/user/unblock/{userId}" )
    public String unblock(@PathVariable("userId") long userId ){
        userService.setActiveById(userId, true);
        return "redirect:/admin/users";

    }
}
