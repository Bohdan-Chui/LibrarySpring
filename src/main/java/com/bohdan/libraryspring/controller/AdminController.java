package com.bohdan.libraryspring.controller;

import com.bohdan.libraryspring.model.Book;
import com.bohdan.libraryspring.model.User;
import com.bohdan.libraryspring.service.BookService;
import com.bohdan.libraryspring.service.UserService;
import com.bohdan.libraryspring.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

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
    @GetMapping(value = "/workers")
    public String workersView(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<User> catalogPage = userService.getAllWorkers(pageable);
        model.addAttribute("page", catalogPage);
        User user = new User();
        model.addAttribute("user", user);
        return "adminLibrarians";
    }
    @PostMapping(value = "/workers")
    public String createNewUser(@Valid User user, BindingResult bindingResult) {
        Optional<User> userExist = userService.findUserByEmail(user.getEmail());
        if (userExist.isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "*There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "adminLibrarians";
        }

        String preCryptPassword = user.getPassword();
        user = userService.saveLibrarian(user);
        System.out.println("email = " + user.getEmail() + ", pass = " + preCryptPassword);
        return "redirect: /admin/workers";
    }
    @PostMapping(value ="/librarian/delete/{userId}" )
    public String deleteLibrarian(@PathVariable("userId") long userId ){
        userService.deleteUserById(userId);
        return "redirect: /admin/workers";
    }
    @GetMapping(value = "/catalog")
    public ModelAndView catalogView(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminCatalog");
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Book> catalogPage = bookService.getALlBooksForAdmin(pageable);
        model.addAttribute("page", catalogPage);
        PaginationUtil.setPaginationAttributes(model,catalogPage,page, size);
        Book book = new Book();
        model.addAttribute("book", book);
        return modelAndView;
    }
    @PostMapping(value = "/book/create")
    public String addBook(@Valid Book book, BindingResult bindingResult){
        Book bookExist = bookService.getBook(book);
        if(bookExist != null){
            bindingResult.rejectValue("title", "error.book", "*There is already that book");
        }
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/admin/catalog";
        }
        System.out.println(book);
        System.out.println(bookService.saveBook(book));
        return "redirect:/admin/catalog";
    }

    @PostMapping(value ="/book/delete/{bookId}" )
    public String deleteCard(@PathVariable("bookId") long bookId){
        bookService.deleteBook(bookId);
        return "redirect:/admin/catalog";
    }
    @GetMapping(value ="/book/editBook/{bookId}" )
    public String editBook(@PathVariable("bookId") long bookId, Model model){
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "editBook";
    }
    @PostMapping(value = "/book/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult){
        System.out.println(book);
        bookService.deleteBook(book.getId());
        System.out.println(book);
        bookService.saveBook(book);
        return "redirect:/admin/catalog";
    }
}
