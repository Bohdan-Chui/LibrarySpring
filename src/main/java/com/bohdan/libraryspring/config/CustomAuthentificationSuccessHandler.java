package com.bohdan.libraryspring.config;


import com.bohdan.libraryspring.model.User;
import com.bohdan.libraryspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Configuration
class CustomAuthentificationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String currentUserEmail = authentication.getName();
        Optional<User> user = userService.findUserByEmail(currentUserEmail);
        if(user.isPresent()){
            session.setAttribute("user", user.get());
        }

        if (authorities.contains("reader")) {
            response.sendRedirect("/mainPage");
        }
        else {
            response.sendRedirect("/user/catalog");
        }
    }
}