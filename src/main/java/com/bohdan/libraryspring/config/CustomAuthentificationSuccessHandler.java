package com.bohdan.libraryspring.config;


import com.bohdan.libraryspring.model.User;
import com.bohdan.libraryspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Configuration
class CustomAuthentificationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        HttpSession session = request.getSession();
        String currentUserEmail = authentication.getName();
        Optional<User> user = userService.findUserByEmail(currentUserEmail);
        user.ifPresent(value -> session.setAttribute("user", value));

        response.sendRedirect("/mainPage");
    }
}